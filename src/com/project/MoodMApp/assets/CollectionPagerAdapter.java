package com.project.MoodMApp.assets;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import com.project.MoodMApp.MyMapFragment;
import com.project.MoodMApp.NewsFragment;

/**
 * Created by PsichO on 05.04.2014.
 */
public class CollectionPagerAdapter extends FragmentPagerAdapter {

    public CollectionPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return new MyMapFragment();
            case 1:
                return new NewsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}