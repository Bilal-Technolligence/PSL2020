package com.example.psl2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LiveStreamingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
       // setContentView( R.layout.activity_live_streaming );
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_live_streaming;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_liveStream;
    }
}
