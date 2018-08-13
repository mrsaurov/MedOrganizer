package com.saurov.android.helpers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.saurov.android.activities.SkippedMedicineTabFragment;
import com.saurov.android.activities.TakenMedicineTabFragment;


public class MedicineHistoryPager extends FragmentStatePagerAdapter {
    private int tabCount;
    private Bundle arguments;

    private String tabTitles[] = {"Taken", "Skipped"};

    public MedicineHistoryPager(FragmentManager fm, Bundle arguments, int tabCount) {
        super(fm);
        this.arguments = arguments;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TakenMedicineTabFragment tab1 = new TakenMedicineTabFragment();
                tab1.setArguments(arguments);
                return tab1;
            case 1:
                SkippedMedicineTabFragment tab2 = new SkippedMedicineTabFragment();
                tab2.setArguments(arguments);
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
