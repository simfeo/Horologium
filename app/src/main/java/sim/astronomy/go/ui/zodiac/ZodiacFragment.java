package sim.astronomy.go.ui.zodiac;

import static sim.astronomy.go.Utils.Utils.shouldUpdateUI;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Locale;

import sim.astronomy.go.R;
import sim.astronomy.go.databinding.ZodiacBinding;

public class ZodiacFragment extends Fragment {
    final long toleranceInMinutes = 60*24;
    Resources res;
    View view;
    private ZodiacBinding binding;
    private TextView Aries, Aries1, Taurus, Taurus1, Gemini, Gemini1, Cancer, Cancer1, Leo, Leo1,
            Virgo, Virgo1, Libra, Libra1, Scorpio, Scorpio1, Sagittarius, Sagittarius1, Capricorn, Capricorn1,
            Aquarius, Aquarius1, Pisces, Pisces1;
    private TextView act_m2, act_m1, act, act_1, act_2;
    Calendar m_lastUpdateTime = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ZodiacViewModel zodiacViewModel =
                new ViewModelProvider(this).get(ZodiacViewModel.class);

        binding = ZodiacBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        res = getResources();

        Aries = view.findViewById(R.id.zodiac_AriesTitle);
        Aries1 = view.findViewById(R.id.zodiac_AriesDate);
        Taurus = view.findViewById(R.id.zodiac_TaurusTitle);
        Taurus1 = view.findViewById(R.id.zodiac_TaurusDate);
        Gemini = view.findViewById(R.id.zodiac_GeminiTitle);
        Gemini1 = view.findViewById(R.id.zodiac_GeminiDate);
        Cancer = view.findViewById(R.id.zodiac_CancerTitle);
        Cancer1 = view.findViewById(R.id.zodiac_CancerDate);
        Leo = view.findViewById(R.id.zodiac_LeoTitle);
        Leo1 = view.findViewById(R.id.zodiac_LeoDate);
        Virgo = view.findViewById(R.id.zodiac_VirgoTitle);
        Virgo1 = view.findViewById(R.id.zodiac_VirgoDate);
        Libra = view.findViewById(R.id.zodiac_LibraTitle);
        Libra1 = view.findViewById(R.id.zodiac_LibraDate);
        Scorpio = view.findViewById(R.id.zodiac_ScorpioTitle);
        Scorpio1 = view.findViewById(R.id.zodiac_ScorpioDate);
        Sagittarius = view.findViewById(R.id.zodiac_SagittariusTitle);
        Sagittarius1 = view.findViewById(R.id.zodiac_SagittariusDate);
        Capricorn = view.findViewById(R.id.zodiac_CapricornTitle);
        Capricorn1 = view.findViewById(R.id.zodiac_CapricornDate);
        Aquarius = view.findViewById(R.id.zodiac_AquariusTitle);
        Aquarius1 = view.findViewById(R.id.zodiac_AquariusDate);
        Pisces = view.findViewById(R.id.zodiac_PiscesTitle);
        Pisces1 = view.findViewById(R.id.zodiac_PiscesDate);
        act_m2 = view.findViewById(R.id.zodiacTwoYearsAgo);
        act_m1 = view.findViewById(R.id.zodiacPreviousYear);
        act = view.findViewById(R.id.zodiacCurrentYear);
        act_1 = view.findViewById(R.id.zodiacNextYear);
        act_2 = view.findViewById(R.id.zodiacTwoYearAhead);

        SetupUI();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        SetupUI();
    }

    private void SetupUI() {
        if (!shouldUpdateUI(m_lastUpdateTime, toleranceInMinutes))
        {
            return;
        }
        m_lastUpdateTime = java.util.Calendar.getInstance();

        String dt = new java.text.SimpleDateFormat("dd-MM-yyyy", Locale.US).format(java.util.Calendar.getInstance().getTime());

        String[] days = dt.split("-");

        int day = Integer.parseInt(days[0]);
        int mon = Integer.parseInt(days[1]);
        int year = Integer.parseInt(days[2]);
        setZodiacAnimalNameToTextView(year - 2, act_m2);
        setZodiacAnimalNameToTextView(year - 1, act_m1);
        setZodiacAnimalNameToTextView(year, act);
        setZodiacAnimalNameToTextView(year + 1, act_1);
        setZodiacAnimalNameToTextView(year + 2, act_2);

        setZodiacConstellationFormatting(day, mon);
    }

    private void setZodiacConstellationFormatting(int day, int mon) {
        if ((mon == 3 && day >= 21) || (mon == 4 && day <= 20))
            makeActualConstellationFormatting(Aries, Aries1);
        if ((mon == 4 && day >= 21) || (mon == 5 && day <= 20))
            makeActualConstellationFormatting(Taurus, Taurus1);
        if ((mon == 5 && day >= 21) || (mon == 6 && day <= 21))
            makeActualConstellationFormatting(Gemini, Gemini1);
        if ((mon == 6 && day >= 22) || (mon == 7 && day <= 22))
            makeActualConstellationFormatting(Cancer, Cancer1);
        if ((mon == 7 && day >= 23) || (mon == 8 && day <= 23))
            makeActualConstellationFormatting(Leo, Leo1);
        if ((mon == 8 && day >= 24) || (mon == 9 && day <= 23))
            makeActualConstellationFormatting(Virgo, Virgo1);
        if ((mon == 9 && day >= 24) || (mon == 10 && day <= 22))
            makeActualConstellationFormatting(Libra, Libra1);
        if ((mon == 10 && day >= 23) || (mon == 11 && day <= 22))
            makeActualConstellationFormatting(Scorpio, Scorpio1);
        if ((mon == 11 && day >= 23) || (mon == 12 && day <= 21))
            makeActualConstellationFormatting(Sagittarius, Sagittarius1);
        if ((mon == 12 && day >= 22) || (mon == 1 && day <= 20))
            makeActualConstellationFormatting(Capricorn, Capricorn1);
        if ((mon == 1 && day >= 21) || (mon == 2 && day <= 19))
            makeActualConstellationFormatting(Aquarius, Aquarius1);
        if ((mon == 2 && day >= 20) || (mon == 3 && day <= 20))
            makeActualConstellationFormatting(Pisces, Pisces1);
    }


    public void setZodiacAnimalNameToTextView(int a, TextView la) {
        String[] zodiacAnimals = res.getStringArray(R.array.ZodiacAnimal);
        a = (a - 1900) % 12;
        la.setText(zodiacAnimals[a]);
    }

    public void makeActualConstellationFormatting(TextView nameTextView, TextView dateTextView) {
        float currentSize = nameTextView.getTextSize()*1.2f;
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentSize);
        nameTextView.setTypeface(nameTextView.getTypeface(), Typeface.BOLD);
        nameTextView.setTextColor(res.getColor(R.color.day_background,null));
        dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentSize);
        dateTextView.setTypeface(nameTextView.getTypeface(), Typeface.BOLD);
        dateTextView.setTextColor(res.getColor(R.color.day_background,null));
    }
}
