package com.example.psl2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import hotchemi.android.rate.AppRate;

public class LiveScoreActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
       // setContentView( R.layout.activity_live_score );


    }

    @Override
    int getContentViewId() {
        return R.layout.activity_live_score;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_matches;
    }
}
