package com.saurov.android.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.saurov.android.activities.SkippedMedicineTabFragment;
import com.saurov.android.activities.TakenMedicineTabFragment;


public class Pager extends FragmentStatePagerAdapter {

    int tabCount;

    String tabTitles[] = {"Taken", "Skipped"};

    public Pager(FragmentManager fm, int tabCount) {
        super(fm);

        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                TakenMedicineTabFragment tab1 = new TakenMedicineTabFragment();
                return tab1;
            case 1:
                SkippedMedicineTabFragment tab2 = new SkippedMedicineTabFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
