package com.technolligence.cricketstream;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class BatBowlAdapter extends FragmentStatePagerAdapter {
    int countTab;

    public BatBowlAdapter(@NonNull FragmentManager fm, int countTab) {
        super(fm);
        this.countTab = countTab;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BatsmanFragment batsmanFragment = new BatsmanFragment();
                return batsmanFragment;
            case 1:
                BowlerFragment bowlerFragment = new BowlerFragment();
                return bowlerFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
