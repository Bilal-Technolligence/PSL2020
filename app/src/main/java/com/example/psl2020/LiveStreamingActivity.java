package com.example.psl2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LiveStreamingActivity extends BaseActivity {
    CardView cardPtvSports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
       // setContentView( R.layout.activity_live_streaming );
        cardPtvSports = findViewById( R.id.cardChannel1 );
        cardPtvSports.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getApplication().getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                if (i != null)
                    getApplicationContext().startActivity(i);
            }
        } );
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
