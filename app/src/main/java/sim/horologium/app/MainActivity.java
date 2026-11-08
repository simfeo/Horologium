package sim.horologium.app;

import static sim.horologium.app.Utils.Utils.initializeCityDataContainer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;

import sim.horologium.app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // targetSdk 35+ forces edge-to-edge on Android 15+, so keep content clear of the system
        // bars here. On older versions the insets arrive as zero and nothing changes.
        // The status bar itself is transparent there, so a scrim view restores the brand colour.
        View statusBarScrim = findViewById(R.id.status_bar_scrim);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(bars.left, 0, bars.right, bars.bottom);
            statusBarScrim.getLayoutParams().height = bars.top;
            statusBarScrim.requestLayout();
            return WindowInsetsCompat.CONSUMED;
        });

        NavigationBarView navView = findViewById(R.id.nav_view);
        // Create object of ViewPager2
        ViewPager2 viewPager2 = findViewById(R.id.viewpager);

        for (int i = 0; i < navView.getMenu().size(); ++i) {
            MenuItem item = navView.getMenu().getItem(i);
            item.setCheckable(true);
            item.setOnMenuItemClickListener(new MyMenuItemOnMenuItemClickListener(i, viewPager2));
        }

        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);
        viewPager2.registerOnPageChangeCallback(new MyViewPager2OnPageChangeCallback(navView));
        initializeCityDataContainer(this);
    }

    static class MyMenuItemOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener
    {
        private final int order;
        private final ViewPager2 viewPager;
        public MyMenuItemOnMenuItemClickListener(int ord, ViewPager2 pager)
        {
            order = ord;
            viewPager = pager;
        }

        @Override
        public boolean onMenuItemClick(@NonNull MenuItem item) {
            viewPager.setCurrentItem(order);
            return true;
        }
    }

    static class MyViewPager2OnPageChangeCallback extends ViewPager2.OnPageChangeCallback {

        NavigationBarView navView;
        public MyViewPager2OnPageChangeCallback(NavigationBarView v)
        {
            navView = v;
        }
        @Override
        // This method is triggered when there is any scrolling activity for the current page
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        // triggered when you select a new page
        @Override
        public void onPageSelected(int position) {

            MenuItem item = navView.getMenu().getItem(position);
            item.setChecked(true);

            super.onPageSelected(position);
        }

        // triggered when there is
        // scroll state will be changed
        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    }
}