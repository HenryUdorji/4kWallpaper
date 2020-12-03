package com.codemountain.a4kwallpaper.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.codemountain.a4kwallpaper.R;
import com.codemountain.a4kwallpaper.adapters.WallpaperPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager2 wallpaperPager;
    TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        fetchLatestImages();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //viewpager
        wallpaperPager = findViewById(R.id.viewPager);
        wallpaperPager.setAdapter(new WallpaperPagerAdapter(this));
        tabLayout = findViewById(R.id.tabLayout);
        tabLayoutMediator = new TabLayoutMediator(
                tabLayout, wallpaperPager, (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Popular");
                            tab.setIcon(R.drawable.ic_popular);
                            break;
                        case 1:
                            tab.setText("Latest");
                            tab.setIcon(R.drawable.ic_latest);
                            break;
                        case 2:
                            tab.setText("Category");
                            tab.setIcon(R.drawable.ic_category);
                            break;

                    }
                }
        );
        tabLayoutMediator.attach();
    }

    private void fetchLatestImages() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tabLayoutMediator.detach();
        Log.i(TAG, "onDestroy: TabLayoutMediator detached");
    }
}