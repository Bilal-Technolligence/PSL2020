package com.technolligence.cricketstream;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    int countTab;

    public PageAdapter(@NonNull FragmentManager fm, int countTab) {
        super(fm);
        this.countTab = countTab;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fixture fixture = new Fixture();
                return fixture;
            case 1:
                Scoreboard scoreboard = new Scoreboard();
                return scoreboard;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
