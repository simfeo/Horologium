package sim.astronomy.go.ui.moon;


import static sim.astronomy.go.Utils.AstroMath.JD;
import static sim.astronomy.go.Utils.AstroMath.getFullNullMoonDates;
import static sim.astronomy.go.Utils.AstroMath.getLunarEclipticOrbitInDegreesAndDistance;
import static sim.astronomy.go.Utils.AstroMath.getMoonAge;
import static sim.astronomy.go.Utils.AstroMath.getMoonVisibilityPercent;
import static sim.astronomy.go.Utils.AstroMath.normalize;
import static sim.astronomy.go.Utils.AstroMath.JDtoDay;
import static sim.astronomy.go.Utils.AstroMath.JDtoMon;
import static sim.astronomy.go.Utils.AstroMath.JDtoYear;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sim.astronomy.go.R;
import sim.astronomy.go.databinding.MoonBinding;


public class MoonFragment extends Fragment {

    View view;
    private TextView percent, phase, distance, zodiac, age;
    Resources res;

    private MoonBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MoonViewModel MoonViewModel =
                new ViewModelProvider(this).get(MoonViewModel.class);

        binding = MoonBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        res = getResources();


        TextView moonFulllTime = view.findViewById(R.id.moonFulllTime);
        TextView moonNullMoonTime = view.findViewById(R.id.moonNullMoonTime);
        percent = view.findViewById(R.id.moonVisibilityPercent);
        phase = view.findViewById(R.id.moonMoonPhase);
        zodiac = view.findViewById(R.id.moonZodiacMoonPositionTitle);
        age = view.findViewById(R.id.moonAge);
        distance = view.findViewById(R.id.moonDistanceFromEarth);
        TextView moonZodiacMoonPositionName = view.findViewById(R.id.moonZodiacMoonPositionName);

        String date = new SimpleDateFormat("dd MM yyyy").format(Calendar.getInstance().getTime());
        String[] days = date.split(" ");

        int day = Integer.parseInt(days[0]);
        int mon = Integer.parseInt(days[1]);
        int year = Integer.parseInt(days[2]);

//            double Jd = 367 * year - 7 * (year + (mon + 9) / 12) / 4 + 275 * mon / 9 + day + 1721013.5 + 0.5;
        double Jd = JD(year, mon, day) + 0.5;
        double ageInDays = getMoonAge(year, mon, day, Jd);

        String Phase = getMoonPhaseName(ageInDays);
        phase.setText(res.getString(R.string.Moon_Fase) + " " + Phase);
        String dayPostFix = getAgeDaysPostfix(ageInDays);

        age.setText(res.getString(R.string.Moon_AGE) + " " + Math.round(ageInDays) + dayPostFix);


        double[] resultOrbitDistance = getLunarEclipticOrbitInDegreesAndDistance(Jd);
        double LO = resultOrbitDistance[0];
        double DI = resultOrbitDistance[1];
        String Zodiac = getConstellationName(LO);

        zodiac.setText(res.getString(R.string.Moon_Constellation));
        moonZodiacMoonPositionName.setText(Zodiac);
        distance.setText(res.getString(R.string.Moon_Distance) + " " + (int) (DI * 6342) + " km");

        double[] resutlFullNullMoonDates = getFullNullMoonDates(Jd, ageInDays);
        double JDFull = resutlFullNullMoonDates[0];
        double JDNew = resutlFullNullMoonDates[1];


        moonFulllTime.setText("🌝" + JDtoYear(JDFull) + "-" + JDtoMon(JDFull) + "-" + JDtoDay(JDFull) + "  " + (int) ((JDFull % 1 * 1440) / 60) + ":" + ((Math.round((JDFull % 1 * 1440) % 60)) < 10 ? ("0" + (Math.round((JDFull % 1 * 1440) % 60))) : (Math.round((JDFull % 1 * 1440) % 60))));
        moonNullMoonTime.setText("🌚" + JDtoYear(JDNew) + "-" + JDtoMon(JDNew) + "-" + JDtoDay(JDNew) + "  " + (int) ((JDNew % 1 * 1440) / 60) + ":" + ((Math.round((JDNew % 1 * 1440) % 60)) < 10 ? ("0" + (Math.round((JDNew % 1 * 1440) % 60))) : (Math.round((JDNew % 1 * 1440) % 60))));


        String hours = new java.text.SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
        int hoursInt = Integer.parseInt(hours);
        double jde = Jd - 0.5 + hoursInt / 24.0;
        int visibilityPercent = getMoonVisibilityPercent(jde);
        percent.setText(res.getString(R.string.Moon_Visibility) + " " + visibilityPercent + "%");


        return view;
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
    private static String getMoonPhaseName(double ageInDays) {
        String Phase;
        if (ageInDays < 1.84566) Phase = "NEW";
        else if (ageInDays < 5.53699) Phase = "Evening crescent";
        else if (ageInDays < 9.22831) Phase = "First quarter";
        else if (ageInDays < 12.91963) Phase = "Waxing gibbous";
        else if (ageInDays < 16.61096) Phase = "FULL";
        else if (ageInDays < 20.30228) Phase = "Waning gibbous";
        else if (ageInDays < 23.99361) Phase = "Last quarter";
        else if (ageInDays < 27.68493) Phase = "Morning crescent";
        else Phase = "NEW";
        return Phase;
    }

    @NonNull
    private String getConstellationName(double LO) {
        String Zodiac;
        if (LO < 33.18) Zodiac = res.getString(R.string.z12);
        else if (LO < 51.16) Zodiac = res.getString(R.string.z1);
        else if (LO < 93.44) Zodiac = res.getString(R.string.z2);
        else if (LO < 119.48) Zodiac = res.getString(R.string.z3);
        else if (LO < 135.30) Zodiac = res.getString(R.string.z4);
        else if (LO < 173.34) Zodiac = res.getString(R.string.z5);
        else if (LO < 224.17) Zodiac = res.getString(R.string.z6);
        else if (LO < 242.57) Zodiac = res.getString(R.string.z7);
        else if (LO < 271.26) Zodiac = res.getString(R.string.z8);
        else if (LO < 302.49) Zodiac = res.getString(R.string.z9);
        else if (LO < 311.72) Zodiac = res.getString(R.string.z10);
        else if (LO < 348.58) Zodiac = res.getString(R.string.z11);
        else Zodiac = res.getString(R.string.z12);
        return Zodiac;
    }
}
