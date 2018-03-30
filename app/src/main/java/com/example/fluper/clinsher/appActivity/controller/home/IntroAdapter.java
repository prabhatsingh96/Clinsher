package com.example.fluper.clinsher.appActivity.controller.home;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by fluper on 19/2/18.
 */

public class IntroAdapter extends FragmentPagerAdapter{

        public IntroAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return IntroFragment.newInstance(Color.parseColor("#41ABF8"), position); // blue
                case 1:
                    return IntroFragment.newInstance(Color.parseColor("#41ABF8"), position); // green
                case 2:
                return IntroFragment.newInstance(Color.parseColor("#41ABF8"), position);
                case 3:
                return IntroFragment.newInstance(Color.parseColor("#41ABF8"), position);
                default:
                    return IntroFragment.newInstance(Color.parseColor("#41ABF8"), position);
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

    }

