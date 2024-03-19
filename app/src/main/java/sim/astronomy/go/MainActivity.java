package sim.astronomy.go;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    // Create object of ViewPager2
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView navView = findViewById(R.id.nav_view);
        viewPager2 = findViewById(R.id.viewpager);

        for (int i = 0; i < navView.getMenu().size(); ++i) {
            MenuItem item = navView.getMenu().getItem(i);
            item.setCheckable(true);
            item.setOnMenuItemClickListener(new MyMenuItemOnMenuItemClickListener(i, viewPager2));
        }

        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);
        viewPager2.registerOnPageChangeCallback(new MyViewPager2OnPageChangeCallback(navView));
    }

    class MyMenuItemOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener
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

    class MyViewPager2OnPageChangeCallback extends ViewPager2.OnPageChangeCallback {

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