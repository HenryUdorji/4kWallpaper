package com.codemountain.a4kwallpaper.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.codemountain.a4kwallpaper.view.fragments.CategoryFragment;
import com.codemountain.a4kwallpaper.view.fragments.LatestFragment;
import com.codemountain.a4kwallpaper.view.fragments.PopularFragment;

public class WallpaperPagerAdapter extends FragmentStateAdapter {

    public WallpaperPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PopularFragment();
            case 1:
                return new LatestFragment();
            default:
                return new CategoryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
