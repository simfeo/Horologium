package sim.astronomy.go;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import sim.astronomy.go.ui.calendar.CalendarFragment;
import sim.astronomy.go.ui.moon.MoonFragment;
import sim.astronomy.go.ui.sun.SunFragment;
import sim.astronomy.go.ui.zodiac.ZodiacFragment;


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
