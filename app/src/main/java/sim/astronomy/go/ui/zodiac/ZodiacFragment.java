package sim.astronomy.go.ui.zodiac;

import static sim.astronomy.go.Utils.Utils.shouldUpdateUI;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
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
    private TextView Oven, Oven1, Telez, Telez1, Bliznez, Bliznez1, Rak, Rak1, Lev, Lev1, Deva, Deva1, Vesi, Vesi1, Scorpi, Scorpi1, Strelez, Strelez1, Kozer, Kozer1, Vodol, Vodol1, Ribi, Ribi1;
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

        Oven = view.findViewById(R.id.oven1);
        Oven1 = view.findViewById(R.id.oven2);
        Telez = view.findViewById(R.id.telez1);
        Telez1 = view.findViewById(R.id.telez2);
        Bliznez = view.findViewById(R.id.bliznez1);
        Bliznez1 = view.findViewById(R.id.bliznez2);
        Rak = view.findViewById(R.id.rak1);
        Rak1 = view.findViewById(R.id.rak2);
        Lev = view.findViewById(R.id.lev1);
        Lev1 = view.findViewById(R.id.lev2);
        Deva = view.findViewById(R.id.deva1);
        Deva1 = view.findViewById(R.id.deva2);
        Vesi = view.findViewById(R.id.vesi1);
        Vesi1 = view.findViewById(R.id.vesi2);
        Scorpi = view.findViewById(R.id.scorpi1);
        Scorpi1 = view.findViewById(R.id.scorpi2);
        Strelez = view.findViewById(R.id.strelez1);
        Strelez1 = view.findViewById(R.id.strelez2);
        Kozer = view.findViewById(R.id.kozer1);
        Kozer1 = view.findViewById(R.id.kozer2);
        Vodol = view.findViewById(R.id.vodol1);
        Vodol1 = view.findViewById(R.id.vodol2);
        Ribi = view.findViewById(R.id.ribi1);
        Ribi1 = view.findViewById(R.id.ribi2);
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
            makeActualConstellationFormatting(Oven, Oven1);
        if ((mon == 4 && day >= 21) || (mon == 5 && day <= 20))
            makeActualConstellationFormatting(Telez, Telez1);
        if ((mon == 5 && day >= 21) || (mon == 6 && day <= 21))
            makeActualConstellationFormatting(Bliznez, Bliznez1);
        if ((mon == 6 && day >= 22) || (mon == 7 && day <= 22))
            makeActualConstellationFormatting(Rak, Rak1);
        if ((mon == 7 && day >= 23) || (mon == 8 && day <= 23))
            makeActualConstellationFormatting(Lev, Lev1);
        if ((mon == 8 && day >= 24) || (mon == 9 && day <= 23))
            makeActualConstellationFormatting(Deva, Deva1);
        if ((mon == 9 && day >= 24) || (mon == 10 && day <= 22))
            makeActualConstellationFormatting(Vesi, Vesi1);
        if ((mon == 10 && day >= 23) || (mon == 11 && day <= 22))
            makeActualConstellationFormatting(Scorpi, Scorpi1);
        if ((mon == 11 && day >= 23) || (mon == 12 && day <= 21))
            makeActualConstellationFormatting(Strelez, Strelez1);
        if ((mon == 12 && day >= 22) || (mon == 1 && day <= 20))
            makeActualConstellationFormatting(Kozer, Kozer1);
        if ((mon == 1 && day >= 21) || (mon == 2 && day <= 19))
            makeActualConstellationFormatting(Vodol, Vodol1);
        if ((mon == 2 && day >= 20) || (mon == 3 && day <= 20))
            makeActualConstellationFormatting(Ribi, Ribi1);
    }


    public void setZodiacAnimalNameToTextView(int a, TextView la) {

        String[] zodiacAnimals = res.getStringArray(R.array.ZodiacAnimal);
        a = (a - 1900) % 12;
        la.setText(zodiacAnimals[a]);


    }

    public void makeActualConstellationFormatting(TextView nameTextView, TextView dateTextView) {
        nameTextView.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
        nameTextView.setTypeface(nameTextView.getTypeface(), Typeface.BOLD);
        nameTextView.setTextColor(res.getColor(R.color.day_background,null));
        dateTextView.setTextAppearance(android.R.style.TextAppearance_Medium);
        dateTextView.setTypeface(nameTextView.getTypeface(), Typeface.BOLD);
        dateTextView.setTextColor(res.getColor(R.color.day_background,null));
    }
}
