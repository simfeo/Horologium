package unhnar.idimus.atro;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import unhnar.idimus.atro.ui.calendar.CalendarFragment;
import unhnar.idimus.atro.ui.moon.MoonFragment;
import unhnar.idimus.atro.ui.sun.SunFragment;
import unhnar.idimus.atro.ui.zodiac.ZodiacFragment;

class ViewPager2Adapter extends FragmentStateAdapter {

    public ViewPager2Adapter(FragmentActivity fa) {
        super(fa);
    }

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
