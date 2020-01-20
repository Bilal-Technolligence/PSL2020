package com.example.psl2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.facebook.FacebookSdk;

import hotchemi.android.rate.AppRate;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
      //  setContentView( R.layout.activity_main );
        AppRate.with(this)
                .setInstallDays( 0 )
                .setLaunchTimes( 3 )
                .setRemindInterval( 5 )
                .monitor();

        AppRate.showRateDialogIfMeetsConditions( this );
        AppRate.with( this ).clearAgreeShowDialog();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
