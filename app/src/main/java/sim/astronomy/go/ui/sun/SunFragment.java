package sim.astronomy.go.ui.sun;


import static sim.astronomy.go.Utils.AstroMath.IsSummerTime;
import static sim.astronomy.go.Utils.AstroMath.SunRise;
import static sim.astronomy.go.Utils.AstroMath.SunSet;
import static sim.astronomy.go.Utils.AstroMath.getDayNum;
import static sim.astronomy.go.Utils.AstroMath.getFiForLocation;
import static sim.astronomy.go.Utils.AstroMath.getLwForLocation;
import static sim.astronomy.go.Utils.AstroMath.zoneString;
import static sim.astronomy.go.Utils.Utils.numberToStringAddZeroIfNeeded;
import static sim.astronomy.go.Utils.Utils.readingProcedure;
import static sim.astronomy.go.Utils.Utils.writingProcedure;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import java.io.IOException;
import java.util.Locale;


import sim.astronomy.go.R;
import sim.astronomy.go.Utils.Utils;
import sim.astronomy.go.databinding.SunBinding;


public class SunFragment extends Fragment {

    private Resources res;
    private TextView cityName, gmtt, latitude, longitude, sunrise, sunset, daylon;

    private static String[] cityDataContainer = new String[11];
    int lat1, lat2, lat3, lon1, lon2, lon3;

    private View view;

    private SunBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SunViewModel SunViewModel =
                new ViewModelProvider(this).get(SunViewModel.class);

        binding = SunBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        res = getResources();

        cityName = view.findViewById(R.id.sunCityName);
        gmtt = view.findViewById(R.id.sunGmtTimeZone);
        latitude = view.findViewById(R.id.sunLatitude);
        longitude = view.findViewById(R.id.sunLongitude);
        sunrise = view.findViewById(R.id.sunSunriseTime);
        sunset = view.findViewById(R.id.sunSunsetTime);
        daylon = view.findViewById(R.id.sunDayLength);

        initializeCityDataArrayContainer();
        cityName.setText(cityDataContainer[0]);
        double zone = Double.parseDouble(cityDataContainer[1]);

        gmtt.setText("GMT " + zoneString(zone));
        latitude.setText(res.getString(R.string.Sun_LATITUDE) + " " +
                cityDataContainer[4] + "° " + cityDataContainer[5] + "' " + cityDataContainer[6] + "''  " +
                (cityDataContainer[3].equals("north") ?
                        res.getString(R.string.Sun_North) : res.getString(R.string.Sun_South)));

        longitude.setText(res.getString(R.string.Sun_LONGITUDE) + " " + cityDataContainer[8] + "° " +
                cityDataContainer[9] + "' " + cityDataContainer[10] + "''  " +
                (cityDataContainer[7].equals("east") ?
                        res.getString(R.string.Sun_East) : res.getString(R.string.Sun_West)));


        lat1 = InitizalizeLongitudeLatitudeFromArray(cityDataContainer, 4);
        lat2 = InitizalizeLongitudeLatitudeFromArray(cityDataContainer, 5);
        lat3 = InitizalizeLongitudeLatitudeFromArray(cityDataContainer, 6);

        lon1 = InitizalizeLongitudeLatitudeFromArray(cityDataContainer, 8);
        lon2 = InitizalizeLongitudeLatitudeFromArray(cityDataContainer, 9);
        lon3 = InitizalizeLongitudeLatitudeFromArray(cityDataContainer, 10);


        setupSunsetSunrise();

        return view;
    }

    private void setupSunsetSunrise() {
        String dt = new java.text.SimpleDateFormat("dd-MM-yyyy", Locale.US).format(java.util.Calendar.getInstance().getTime());

        String[] days = dt.split("-");

        int day = Integer.parseInt(days[0]);
        int mon = Integer.parseInt(days[1]);
        int year = Integer.parseInt(days[2]);

        double fi = getFiForLocation(year,mon,day,lat1,lat2,lat3);
        double lw = getLwForLocation(lon1, lon2, lon3, cityDataContainer[7].equals("east"));

        //        int daynum=Math.round(mon*275/9)-Math.round((2-leap)*(mon+9)/12)+day-29;
        int daynum = getDayNum(year, mon, day);

        double UTC = Double.parseDouble(cityDataContainer[1]);
        if (cityDataContainer[2].equals("on") && IsSummerTime(year, mon, daynum)) {
            ++UTC;
        }

        double riseTime = SunRise(year, mon, day, fi, lw);
        double sunsetTime = SunSet(year, mon, day, fi, lw);

        setupSunriseSunsetViews(UTC, riseTime, sunsetTime);
    }

    private void setupSunriseSunsetViews(double UTC, double riseTime, double sunsetTime) {
        if (sunsetTime != 1000 && riseTime != 1000) {

            double DayLong = sunsetTime - riseTime;

            riseTime += UTC;
            sunsetTime += UTC;

            String addRise = "", addSet = "";

            if (riseTime > 24) {
                riseTime -= 24;
                addRise = " nx.";
            } else if (riseTime < 0) {
                riseTime += 24;
                addRise = " prv.";
            }
            if (sunsetTime > 24) {
                sunsetTime -= 24;
                addSet = " nx.";
            } else if (sunsetTime < 0) {
                sunsetTime += 24;
                addSet = " prv.";
            }


            int iDayLong = (int) DayLong;
            int iDayLongM = (int) Math.round((DayLong % 1) * 60);
            if (iDayLongM == 60) {
                iDayLongM = 0;
                ++iDayLong;
            }
//            String sDayLong = res.getString(R.string.Sun_DAYLONG) + " " + iDayLong + ":" + ((iDayLongM < 10) ? ("0" + iDayLongM) : (iDayLongM));
            String sDayLong = res.getString(R.string.Sun_DAYLONG) + " " + iDayLong + ":" + numberToStringAddZeroIfNeeded(iDayLongM);


            int riseTimeInt = (int) riseTime;
            int sunsetTimeInt = (int) sunsetTime;

//            String riseTimeMin = String.valueOf((Math.round(riseTime % 1.0 * 60) < 10) ? "0" + Math.round(riseTime % 1.0 * 60) : Math.round(riseTime % 1.0 * 60));
            String riseTimeMin = numberToStringAddZeroIfNeeded(Math.round(riseTime % 1.0 * 60));
            if (riseTimeMin.equalsIgnoreCase("60")) {
                riseTimeMin = "00";
                ++riseTimeInt;
            }

//            String sunsetTimeMin = String.valueOf((Math.round(sunsetTime % 1.0 * 60) < 10) ? "0" + Math.round(sunsetTime % 1.0 * 60) : Math.round(sunsetTime % 1.0 * 60));
            String sunsetTimeMin = numberToStringAddZeroIfNeeded(Math.round(sunsetTime % 1.0 * 60));
            if (sunsetTimeMin.equals("60")) {
                sunsetTimeMin = "00";
                ++sunsetTimeInt;
            }

            sunrise.setText(res.getString(R.string.Sun_SUNRISE) + " " + riseTimeInt + ":" + riseTimeMin + addRise);
            sunset.setText(res.getString(R.string.Sun_SUNSET) + " " + sunsetTimeInt + ":" + sunsetTimeMin + addSet);
            daylon.setText(sDayLong);
        } else {
            sunrise.setText("N/D");
            sunset.setText("N/D");
            daylon.setText("N/D");
        }
    }

    private static int InitizalizeLongitudeLatitudeFromArray(String[] arrayContainer, int i) {
        int value = 0;
        try {
            value = Integer.parseInt(arrayContainer[i]);

        } catch (Exception e) {
        }
        return value;
    }

    private void initializeCityDataArrayContainer() {
        cityDataContainer = new String[11];
        try {
            cityDataContainer = readingProcedure(getContext());
        } catch (IOException e) {
            try {
                writingProcedure(getContext(), "Kyiv +2 on north 50 27 00 east 30 30 00");
            } catch (IOException e1) {

            }
            try {
                cityDataContainer = readingProcedure(getContext());
            } catch (IOException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }
    }
}
