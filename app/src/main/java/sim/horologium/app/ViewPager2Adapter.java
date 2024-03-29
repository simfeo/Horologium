package sim.horologium.app;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import sim.horologium.app.ui.calendar.CalendarFragment;
import sim.horologium.app.ui.moon.MoonFragment;
import sim.horologium.app.ui.sun.SunFragment;
import sim.horologium.app.ui.zodiac.ZodiacFragment;


class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position)
        {
            case 1:
                return new SunFragment();
            case 2:
                return new MoonFragment();
            case 3:
                return new ZodiacFragment();
            default:
                return new CalendarFragment();
        }
    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return 4;
    }
}
