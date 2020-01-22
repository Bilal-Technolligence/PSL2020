package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hotchemi.android.rate.AppRate;

public class LiveScoreActivity extends BaseActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    int progress = 0;
    ProgressBar simpleProgressBar;
    ImageView btnOut,btnOne,btnTwo,btnThree,btnWide,btnSix,btnFour,btnDot,btnNoball,btnRunout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
       // setContentView( R.layout.activity_live_score );
        btnOut = findViewById( R.id.imgOut );
        btnOne = findViewById( R.id.imgOne );
        btnTwo = findViewById( R.id.imgTwo );
        btnThree = findViewById( R.id.imgThree );
        btnWide = findViewById( R.id.imgWide );
        btnSix = findViewById( R.id.imgSix );
        btnFour = findViewById( R.id.imgFour );
        btnDot = findViewById( R.id.imgDot );
        btnNoball = findViewById( R.id.imgNoball );
        btnRunout = findViewById( R.id.imgRunout );

        simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar5);


        btnOne.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               reference.child( "Test" ).child( "ok" ).setValue( 100 ).toString();
//                Toast.makeText( LiveScoreActivity.this, "Done", Toast.LENGTH_SHORT ).show();
                setProgressValue(progress);

            }
        } );

    }

    private void setProgressValue(final int progress) {

        // set the progress
        simpleProgressBar.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
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
