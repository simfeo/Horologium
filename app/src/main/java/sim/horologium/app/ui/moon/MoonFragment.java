package sim.horologium.app.ui.moon;


import static sim.horologium.app.Utils.AstroMath.IsSummerTimeEu;
import static sim.horologium.app.Utils.AstroMath.IsSummerTimeUsaCanada;
import static sim.horologium.app.Utils.AstroMath.JD;
import static sim.horologium.app.Utils.AstroMath.getFullNullMoonDates;
import static sim.horologium.app.Utils.AstroMath.getLunarEclipticOrbitInDegreesAndDistance;
import static sim.horologium.app.Utils.AstroMath.getMoonAge;
import static sim.horologium.app.Utils.AstroMath.getMoonVisibilityPercent;
import static sim.horologium.app.Utils.AstroMath.JDtoDay;
import static sim.horologium.app.Utils.AstroMath.JDtoMon;
import static sim.horologium.app.Utils.AstroMath.JDtoYear;
import static sim.horologium.app.Utils.Utils.initializeCityDataContainer;
import static sim.horologium.app.Utils.Utils.numberToStringAddZeroIfNeeded;
import static sim.horologium.app.Utils.Utils.shouldUpdateUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import sim.horologium.app.Utils.LocationData;
import sim.horologium.app.R;
import sim.horologium.app.databinding.MoonBinding;


public class MoonFragment extends Fragment {
    final long toleranceInMinutes = 30;
    Resources res;
    View view;
    private MoonBinding binding;
    private TextView percent, phase, distance, zodiac, age, nextFullMoonDate, nextNewMoonDate, zodiacPositionName;
    Calendar m_lastUpdateTime = null;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MoonViewModel MoonViewModel =
                new ViewModelProvider(this).get(MoonViewModel.class);

        binding = MoonBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        res = getResources();

        nextFullMoonDate = view.findViewById(R.id.moonFullTime);
        nextNewMoonDate = view.findViewById(R.id.moonNewMoonTime);
        percent = view.findViewById(R.id.moonVisibilityPercent);
        phase = view.findViewById(R.id.moonMoonPhase);
        zodiac = view.findViewById(R.id.moonZodiacMoonPositionTitle);
        age = view.findViewById(R.id.moonAge);
        distance = view.findViewById(R.id.moonDistanceFromEarth);

        SetupDataToUi();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SetupDataToUi();
    }

    private void SetupDataToUi() {
        if (!shouldUpdateUI(m_lastUpdateTime, toleranceInMinutes))
        {
            return;
        }
        m_lastUpdateTime = java.util.Calendar.getInstance();

        String date = new SimpleDateFormat("dd MM yyyy", Locale.US).format(Calendar.getInstance().getTime());
        String[] days = date.split(" ");

        int day = Integer.parseInt(days[0]);
        int mon = Integer.parseInt(days[1]);
        int year = Integer.parseInt(days[2]);

//            double Jd = 367 * year - 7 * (year + (mon + 9) / 12) / 4 + 275 * mon / 9 + day + 1721013.5 + 0.5;
        double Jd = JD(year, mon, day)+0.5;
        double ageInDays = getMoonAge(Jd);

        String Phase = getMoonPhaseName(ageInDays);
        phase.setText(res.getString(R.string.Moon_Phase) + " " + Phase);
        String dayPostFix = getAgeDaysPostfix(ageInDays);

        age.setText(res.getString(R.string.Moon_AGE) + " " + Math.round(ageInDays) + dayPostFix);


        double[] resultOrbitDistance = getLunarEclipticOrbitInDegreesAndDistance(Jd);
        double LO = resultOrbitDistance[0];
        double DI = resultOrbitDistance[1];
        String Zodiac = getConstellationName(LO);

        zodiac.setText(res.getString(R.string.Moon_Constellation) + " "+ Zodiac);
        distance.setText(res.getString(R.string.Moon_Distance) + " " + (int) (DI * 6342) + " km");

        double[] resultFullNullMoonDates = getFullNullMoonDates(Jd, ageInDays);
        double JDFull = resultFullNullMoonDates[0];
        double JDNew = resultFullNullMoonDates[1];

        LocationData locationData = initializeCityDataContainer(requireContext());

        JDFull = addUtcCorrection(JDFull, locationData);
        JDNew = addUtcCorrection(JDNew, locationData);


        nextFullMoonDate.setText(res.getString(R.string.Moon_FullMoon_Beginning) + " " + getMoonFullNullTimeString(JDFull));
        nextNewMoonDate.setText(res.getString(R.string.Moon_NUllMoon_Beginning) + " " + getMoonFullNullTimeString(JDNew));

        String hours = new SimpleDateFormat("HH", Locale.US).format(Calendar.getInstance().getTime());
        int hoursInt = Integer.parseInt(hours);
        double jde = Jd - 0.5 + hoursInt / 24.0;
        int visibilityPercent = getMoonVisibilityPercent(jde);
        percent.setText(res.getString(R.string.Moon_Visibility) + " " + visibilityPercent + "%");
    }

    private double addUtcCorrection(double jd, LocationData locationData) {
        int year = JDtoYear(jd);
        int mon = JDtoMon(jd);
        int day = JDtoDay(jd);
        double UTC = locationData.getGmtAsDouble();
        if (locationData.daylightSavingEnabled == LocationData.DayLightState.Eu
                && IsSummerTimeEu(year, mon, day)) {
            UTC += 1.0;
        } else if (locationData.daylightSavingEnabled == LocationData.DayLightState.UsCanada
                && IsSummerTimeUsaCanada(year, mon, day)) {
            UTC += 1.0;
        }
        return jd + UTC/24.0;
    }

    private String getMoonFullNullTimeString (double jd)
    {
        double temp=jd + 0.5;
        int Z=(int)(temp);
        double F = temp - Z;

        int hours = (int)(F*24.0);
        F -= hours/24.0;
        int minutes = (int)(F*1440.0);
        if (minutes == 60)
        {
            minutes = 59;
        }
        String[] sMonths = res.getStringArray(R.array.Months);

        String result = ""+JDtoDay(jd) + " " + sMonths[JDtoMon(jd)] + " " + JDtoYear(jd) + "  " + numberToStringAddZeroIfNeeded(hours) + ":" + numberToStringAddZeroIfNeeded(minutes);
        return result;
    }

    private String getAgeDaysPostfix(double ageInDays) {
        String postfix = null;
        String[] postfixArray = res.getStringArray(R.array.MoonDays);
        if (Math.round(ageInDays) == 1 || Math.round(ageInDays) == 21)
            postfix = " " + postfixArray[0];
        else if (Math.round(ageInDays) > 4 && Math.round(ageInDays) < 21)
            postfix = " " + postfixArray[1];
        else if (Math.round(ageInDays) > 24) postfix = " " + postfixArray[1];
        else if (Math.round(ageInDays) > 1 && Math.round(ageInDays) < 5)
            postfix = " " + postfixArray[2];
        else if (Math.round(ageInDays) > 21 && Math.round(ageInDays) < 25)
            postfix = " " + postfixArray[2];
        return postfix;
    }

    @NonNull
    private String getMoonPhaseName(double ageInDays) {
        String Phase;
        if (ageInDays < 1.84566) Phase = res.getString(R.string.Moon_Phase_new);
        else if (ageInDays < 5.53699) Phase = res.getString(R.string.Moon_Phase_evening_crescent);
        else if (ageInDays < 9.22831) Phase = res.getString(R.string.Moon_Phase_first_quarter);
        else if (ageInDays < 12.91963) Phase = res.getString(R.string.Moon_Phase_waxing_gibbous);
        else if (ageInDays < 16.61096) Phase = res.getString(R.string.Moon_Phase_full);
        else if (ageInDays < 20.30228) Phase = res.getString(R.string.Moon_Phase_waning_gibbous);
        else if (ageInDays < 23.99361) Phase = res.getString(R.string.Moon_Phase_last_quarter);
        else if (ageInDays < 27.68493) Phase = res.getString(R.string.Moon_Phase_morning_crescent);
        else Phase = res.getString(R.string.Moon_Phase_new);;
        return Phase;
    }

    @NonNull
    private String getConstellationName(double LO) {
        String Zodiac;
        if (LO < 33.18) Zodiac = res.getString(R.string.Pisces_Title);
        else if (LO < 51.16) Zodiac = res.getString(R.string.Aries_Title);
        else if (LO < 93.44) Zodiac = res.getString(R.string.Taurus_Title);
        else if (LO < 119.48) Zodiac = res.getString(R.string.Gemini_Title);
        else if (LO < 135.30) Zodiac = res.getString(R.string.Cancer_Title);
        else if (LO < 173.34) Zodiac = res.getString(R.string.Leo_Title);
        else if (LO < 224.17) Zodiac = res.getString(R.string.Virgo_Title);
        else if (LO < 242.57) Zodiac = res.getString(R.string.Libra_Title);
        else if (LO < 271.26) Zodiac = res.getString(R.string.Scorpio_Title);
        else if (LO < 302.49) Zodiac = res.getString(R.string.Sagittarius_Title);
        else if (LO < 311.72) Zodiac = res.getString(R.string.Capricorn_Title);
        else if (LO < 348.58) Zodiac = res.getString(R.string.Aquarius_Title);
        else Zodiac = res.getString(R.string.Pisces_Title);
        return Zodiac;
    }
}
