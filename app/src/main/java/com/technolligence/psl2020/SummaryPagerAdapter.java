package com.technolligence.psl2020;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SummaryPagerAdapter extends FragmentStatePagerAdapter {
    int countTab;
    public SummaryPagerAdapter(@NonNull FragmentManager fm, int countTab) {
        super(fm);
        this.countTab=countTab;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Team1Fragment team1Fragment=new Team1Fragment();
                return team1Fragment;
            case 1:
                Team2Fragment team2Fragment=new Team2Fragment();
                return team2Fragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
