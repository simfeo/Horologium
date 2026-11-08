package sim.horologium.app.ui.sun;

import static sim.horologium.app.Utils.AstroMath.IsSummerTimeEu;
import static sim.horologium.app.Utils.AstroMath.IsSummerTimeUsaCanada;
import static sim.horologium.app.Utils.AstroMath.SunRise;
import static sim.horologium.app.Utils.AstroMath.SunSet;
import static sim.horologium.app.Utils.AstroMath.getFiForLocation;
import static sim.horologium.app.Utils.AstroMath.getLwForLocation;
import static sim.horologium.app.Utils.Utils.initializeCityDataContainer;
import static sim.horologium.app.Utils.Utils.numberToStringAddZeroIfNeeded;
import static sim.horologium.app.Utils.Utils.shouldUpdateUI;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Locale;

import sim.horologium.app.EditCords.EditLocationActivity;
import sim.horologium.app.Utils.LocationData;
import sim.horologium.app.R;
import sim.horologium.app.databinding.SunBinding;


public class SunFragment extends Fragment {
    final long toleranceInMinutes = 60 * 24;
    private Resources res;
    private TextView cityName, gmtt, latitude, longitude, sunrise, sunset, daylon;
    private int latDeg, latMin, latSec, lonDeg, lonMin, lonSec;
    Calendar m_lastUpdateTime = null;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    LocationData locationData = initializeCityDataContainer(requireContext());
                    setupUiElements(locationData);
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SunViewModel SunViewModel =
                new ViewModelProvider(this).get(SunViewModel.class);

        SunBinding binding = SunBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        res = getResources();

        cityName = view.findViewById(R.id.sunCityName);
        gmtt = view.findViewById(R.id.sunGmtTimeZone);
        latitude = view.findViewById(R.id.sunLatitude);
        longitude = view.findViewById(R.id.sunLongitude);
        sunrise = view.findViewById(R.id.sunSunriseTime);
        sunset = view.findViewById(R.id.sunSunsetTime);
        daylon = view.findViewById(R.id.sunDayLength);

        binding.sunOpenEditCoordsButton.setOnClickListener(v -> mStartForResult.launch(new Intent(v.getContext(), EditLocationActivity.class)));

        LocationData locationData = initializeCityDataContainer(requireContext());
        setupUiElements(locationData);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocationData locationData = initializeCityDataContainer(requireContext());
        m_lastUpdateTime = null;
        setupUiElements(locationData);
    }

    private void setupUiElements(LocationData locationData) {
        if (!shouldUpdateUI(m_lastUpdateTime, toleranceInMinutes)) {
            return;
        }
        m_lastUpdateTime = java.util.Calendar.getInstance();

        cityName.setText(locationData.cityName);

        gmtt.setText(res.getString(R.string.sun_GMT_prefix, locationData.gmt));
        String latitudeValue = String.format(Locale.ROOT, "%d%s %d%s %s%s %s",
                locationData.latitude.degrees, getString(R.string.degree_post),
                locationData.latitude.minutes, getString(R.string.minute_post),
                numberToStringAddZeroIfNeeded(locationData.latitude.seconds), getString(R.string.second_post),
                res.getString(locationData.isNorth() ? R.string.Sun_North : R.string.Sun_South));
        latitude.setText(res.getString(R.string.sun_latitude_value, latitudeValue));

        String longitudeValue = String.format(Locale.ROOT, "%d%s %d%s %s%s %s",
                locationData.longitude.degrees, getString(R.string.degree_post),
                locationData.longitude.minutes, getString(R.string.minute_post),
                numberToStringAddZeroIfNeeded(locationData.longitude.seconds), getString(R.string.second_post),
                res.getString(locationData.isEast() ? R.string.Sun_East : R.string.Sun_West));
        longitude.setText(res.getString(R.string.sun_longitude_value, longitudeValue));

        latDeg = locationData.latitude.degrees;
        latMin = locationData.latitude.minutes;
        latSec = locationData.latitude.seconds;

        lonDeg = locationData.longitude.degrees;
        lonMin = locationData.longitude.minutes;
        lonSec = locationData.longitude.seconds;


        setupSunsetSunrise(locationData);
    }

    private void setupSunsetSunrise(LocationData cityDataContainer) {
        String dt = new java.text.SimpleDateFormat("dd-MM-yyyy", Locale.US).format(java.util.Calendar.getInstance().getTime());

        String[] days = dt.split("-");

        int day = Integer.parseInt(days[0]);
        int mon = Integer.parseInt(days[1]);
        int year = Integer.parseInt(days[2]);

        double fi = getFiForLocation(year, mon, day, latDeg, latMin, latSec);
        double lw = getLwForLocation(lonDeg, lonMin, lonSec, cityDataContainer.isEast());

        double UTC = cityDataContainer.getGmtAsDouble();
        if (cityDataContainer.daylightSavingEnabled == LocationData.DayLightState.Eu
                && IsSummerTimeEu(year, mon, day)) {
            UTC += 1.0;
        } else if (cityDataContainer.daylightSavingEnabled == LocationData.DayLightState.UsCanada
                && IsSummerTimeUsaCanada(year, mon, day)) {
            UTC += 1.0;
        }

        double riseTime = SunRise(year, mon, day, fi, lw);
        double sunsetTime = SunSet(year, mon, day, fi, lw);

        setupSunriseSunsetViews(UTC, riseTime, sunsetTime);
    }

    private void setupSunriseSunsetViews(double UTC, double riseTime, double sunsetTime) {
        if (!Double.isNaN(sunsetTime) && !Double.isNaN(riseTime)) {
            riseTime += UTC;
            if (riseTime > 24) {
                riseTime -= 24;
            } else if (riseTime < 0) {
                riseTime += 24;
            }

            int riseTimeInt = (int) riseTime;
            int riseTimeMin = (int)Math.round(riseTime % 1.0 * 60);
            if (riseTimeMin == 60) {
                riseTimeMin = 0;
                ++riseTimeInt;
            }
            String riseTimStr = String.format(Locale.ROOT,"%d:%s", riseTimeInt, numberToStringAddZeroIfNeeded(riseTimeMin));

            sunsetTime += UTC;
            if (sunsetTime > 24) {
                sunsetTime -= 24;
            } else if (sunsetTime < 0) {
                sunsetTime += 24;
            }

            int sunsetTimeInt = (int) sunsetTime;
            int sunsetTimeMin = (int)Math.round(sunsetTime % 1.0 * 60);
            if (sunsetTimeMin == 60) {
                sunsetTimeMin = 0;
                ++sunsetTimeInt;
            }
            String sunsetTimeStr = String.format(Locale.ROOT,"%d:%s", sunsetTimeInt, numberToStringAddZeroIfNeeded(sunsetTimeMin));

            double DayLong = sunsetTime - riseTime;
            if (DayLong > 0.0) {
                int iDayLong = (int) DayLong;
                int iDayLongM = (int) Math.round((DayLong % 1) * 60);
                if (iDayLongM == 60) {
                    iDayLongM = 0;
                    ++iDayLong;
                }
                daylon.setText(res.getString(R.string.Sun_DAYLONG, iDayLong + ":" + numberToStringAddZeroIfNeeded(iDayLongM)));

            }
            sunrise.setText(res.getString(R.string.Sun_SUNRISE, riseTimStr));
            sunset.setText(res.getString(R.string.Sun_SUNSET, sunsetTimeStr));
        } else {
            if (!Double.isNaN(riseTime))
            {
                riseTime += UTC;
                if (riseTime > 24) {
                    riseTime -= 24;
                } else if (riseTime < 0) {
                    riseTime += 24;
                }

                int riseTimeInt = (int) riseTime;
                int riseTimeMin = (int)Math.round(riseTime % 1.0 * 60);
                if (riseTimeMin == 60) {
                    riseTimeMin = 0;
                    ++riseTimeInt;
                }
                String riseTimStr = String.format(Locale.ROOT,"%d:%s", riseTimeInt, numberToStringAddZeroIfNeeded(riseTimeMin));
                sunrise.setText(res.getString(R.string.Sun_SUNRISE, riseTimStr));
            }
            else {
                sunrise.setText(res.getString(R.string.Sun_SUNRISE, res.getString(R.string.sun_time_unavailable)));
            }

            if(!Double.isNaN(sunsetTime))
            {
                sunsetTime += UTC;
                if (sunsetTime > 24) {
                    sunsetTime -= 24;
                } else if (sunsetTime < 0) {
                    sunsetTime += 24;
                }

                int sunsetTimeInt = (int) sunsetTime;
                int sunsetTimeMin = (int)Math.round(sunsetTime % 1.0 * 60);
                if (sunsetTimeMin == 60) {
                    sunsetTimeMin = 0;
                    ++sunsetTimeInt;
                }
                String sunsetTimeStr = String.format(Locale.ROOT,"%d:%s", sunsetTimeInt, numberToStringAddZeroIfNeeded(sunsetTimeMin));
                sunset.setText(res.getString(R.string.Sun_SUNSET, sunsetTimeStr));
            }
            else {
                sunset.setText(res.getString(R.string.Sun_SUNSET, res.getString(R.string.sun_time_unavailable)));
            }
            daylon.setText(res.getString(R.string.Sun_DAYLONG, res.getString(R.string.sun_time_unavailable)));
        }
    }
}
