package com.modakdev.bootstream.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.modakdev.bootstream.MainActivity;
import com.modakdev.bootstream.Movies;
import com.modakdev.bootstream.R;
import com.modakdev.bootstream.TvShows;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position)
        {
            case 0 :
                Bundle bundle = new Bundle();
                bundle.putString("username", MainActivity.username);
                bundle.putString("ip", MainActivity.ip);
                fragment = new Movies();
                fragment.setArguments(bundle);
                break;
            case 1 :
                fragment = new TvShows();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}