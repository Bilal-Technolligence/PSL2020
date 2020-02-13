package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;
import hotchemi.android.rate.AppRate;

public class LiveScoreActivity extends BaseActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    //ads instances
    private InterstitialAd interstitialAd;
    private AdView bannerAd;
    int progress = 0;
    ProgressBar simpleProgressBar;
    ImageView btnOut, btnOne, btnTwo, btnThree, btnSix, btnFour, btnDot, btnRunout;
    ImageView team1, team2;
    TextView score1, score2, over1, over2, wicket1, wicket2, rr1, rr2, match, tosswon, bowler, recent,predictionHeading,resultHeading;
    String one, two, elected,scheduleId;
    Integer id, toss;
    TextView bat1, bat2, run1, run2, ball1, ball2, four1, four2, six1, six2, sr1, sr2;
    String selection="", userId="";
    LinearLayout linearLayout1,linearLayout2;
    ImageView live;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AudienceNetworkAds.initialize(this);
        loadAds();
        // setContentView( R.layout.activity_live_score );
        btnOut = findViewById(R.id.imgOut);
        btnOne = findViewById(R.id.imgOne);
        btnTwo = findViewById(R.id.imgTwo);
        btnThree = findViewById(R.id.imgThree);
        btnSix = findViewById(R.id.imgSix);
        btnFour = findViewById(R.id.imgFour);
        btnDot = findViewById(R.id.imgDot);
        btnRunout = findViewById(R.id.imgRunout);
        predictionHeading = findViewById(R.id.txtballbyballprediction);
        resultHeading = findViewById(R.id.txtwaitforresult);

        team1 = findViewById(R.id.imgTeam1);
        team2 = findViewById(R.id.imgTeam2);
        score1 = findViewById(R.id.txtScore1);
        score2 = findViewById(R.id.txtScore2);
        wicket1 = findViewById(R.id.txtWicket1);
        wicket2 = findViewById(R.id.txtWicket2);
        over1 = findViewById(R.id.txtOver1);
        over2 = findViewById(R.id.txtOver2);
        rr1 = findViewById(R.id.txtRR1);
        rr2 = findViewById(R.id.txtRR2);
        match = findViewById(R.id.txtmatches);
        tosswon = findViewById(R.id.txtmatches1);

        bowler = findViewById(R.id.txtBowler);
        recent = findViewById(R.id.txtRecent);

        bat1 = findViewById(R.id.txtBatsmanOneName);
        bat2 = findViewById(R.id.txtBatsmanTwoName);
        run1 = findViewById(R.id.txtBatsmanOneRun);
        run2 = findViewById(R.id.txtBatsmanTwoRun);
        ball1 = findViewById(R.id.txtBatsmanOneBalls);
        ball2 = findViewById(R.id.txtBatsmanTwoBalls);
        four1 = findViewById(R.id.txtBatsmanOneFours);
        four2 = findViewById(R.id.txtBatsmanTwoFours);
        six1 = findViewById(R.id.txtBatsmanOneSixes);
        six2 = findViewById(R.id.txtBatsmanTwoSixes);
        sr1 = findViewById(R.id.txtBatsmanOneSr);
        sr2 = findViewById(R.id.txtBatsmanTwoSr);

        live = findViewById(R.id.imgLL);
        linearLayout1=(LinearLayout) findViewById(R.id.points);
        linearLayout2=(LinearLayout) findViewById(R.id.points2);


        SharedPreferences sharedPreferences = getSharedPreferences( "prefs",MODE_PRIVATE );
        boolean firstStart = sharedPreferences.getBoolean( "firstStart",true);
        if (firstStart){
            showDialog(  );
        }


        reference.child("Schedule").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.child("status").getValue().toString().equals("Live")) {
                            one = dataSnapshot1.child("teamOne").getValue().toString();
                            two = dataSnapshot1.child("teamTwo").getValue().toString();
                            id = Integer.parseInt(dataSnapshot1.child("sid").getValue().toString());
                            scheduleId = dataSnapshot1.child("sid").getValue().toString();
                            if (id == 0) {
                                match.setText("1st Match");
                            } else if (id == 1) {
                                match.setText("2nd Match");
                            } else if (id == 2) {
                                match.setText("3rd Match");
                            } else {
                                id++;
                                match.setText(id + "th Match");
                            }
                            try {
                                String note = dataSnapshot1.child("note").getValue().toString();
                                tosswon.setText(note);
                            }
                            catch (Exception e){}
                            reference.child("LiveScore").child("recent").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        String rece = " ";
                                            for (int i = 1; i <= 6; i++) {
                                            try {
                                                rece = rece + dataSnapshot.child(String.valueOf(i)).getValue().toString()+" ";
                                            }
                                        catch (Exception e){}
                                        }
                                        recent.setText(rece);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            reference.child("LiveScore").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        try{
                                            toss = Integer.parseInt(dataSnapshot.child("tosswin").getValue().toString());
                                            elected = dataSnapshot.child("elected").getValue().toString();
                                            bowler.setText(dataSnapshot.child("bowler").getValue().toString());
                                        }
                                        catch (Exception e){}
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            reference.child( "FinishMatches" ).child( String.valueOf( scheduleId ) ).addValueEventListener( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        tosswon.setText( dataSnapshot.child( "note" ).getValue(  ).toString() );
                                        live.setVisibility( View.GONE );
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            } );

                            reference.child("Teams").child(one).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        try{Picasso.get().load(dataSnapshot.child("ImgUrl").getValue().toString()).into(team1);}
                                        catch (Exception e){}
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            reference.child("Teams").child(two).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        try{Picasso.get().load(dataSnapshot.child("ImgUrl").getValue().toString()).into(team2);}
                                        catch (Exception e){}

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            reference.child("LiveScore").child("batsmanScore").child("1").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        try {
                                            run1.setText(dataSnapshot.child("score").getValue().toString());
                                            bat1.setText(dataSnapshot.child("name").getValue().toString());
                                            ball1.setText(dataSnapshot.child("balls").getValue().toString());
                                            four1.setText(dataSnapshot.child("fours").getValue().toString());
                                            six1.setText(dataSnapshot.child("sixs").getValue().toString());
                                            sr1.setText(dataSnapshot.child("rr").getValue().toString());
                                            reference.child("LiveScore").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                                    if (dataSnapshot1.exists()) {
                                                        if (dataSnapshot.child("name").getValue().toString().equals(dataSnapshot1.child("currentBatting").getValue().toString())) {
                                                            bat1.setText(dataSnapshot.child("name").getValue().toString() + " *");
                                                        } else {
                                                            bat1.setText(dataSnapshot.child("name").getValue().toString());
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        } catch (Exception e) {

                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            reference.child("LiveScore").child("batsmanScore").child("2").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        try {
                                            run2.setText(dataSnapshot.child("score").getValue().toString());
                                            bat2.setText(dataSnapshot.child("name").getValue().toString());
                                            ball2.setText(dataSnapshot.child("balls").getValue().toString());
                                            four2.setText(dataSnapshot.child("fours").getValue().toString());
                                            six2.setText(dataSnapshot.child("sixs").getValue().toString());
                                            sr2.setText(dataSnapshot.child("rr").getValue().toString());
                                            reference.child("LiveScore").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                                    if (dataSnapshot1.exists()) {
                                                        if (dataSnapshot.child("name").getValue().toString().equals(dataSnapshot1.child("currentBatting").getValue().toString())) {
                                                            bat2.setText(dataSnapshot.child("name").getValue().toString() + " *");
                                                        } else {
                                                            bat2.setText(dataSnapshot.child("name").getValue().toString());
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        } catch (Exception e) {

                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            reference.child("LiveScore").child(one).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        try {
                                            score1.setText(dataSnapshot.child("score").getValue().toString());
                                            over1.setText(dataSnapshot.child("over").getValue().toString());
                                            rr1.setText(dataSnapshot.child("rr").getValue().toString());
                                            wicket1.setText(dataSnapshot.child("wicket").getValue().toString());
                                        } catch (Exception e) {

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            reference.child("LiveScore").child(two).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        try {
                                            score2.setText(dataSnapshot.child("score").getValue().toString());
                                            over2.setText(dataSnapshot.child("over").getValue().toString());
                                            rr2.setText(dataSnapshot.child("rr").getValue().toString());
                                            wicket2.setText(dataSnapshot.child("wicket").getValue().toString());
                                        } catch (Exception e) {

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            btnOne.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        String userId = prefs.getString("id", "");
                                        if (!userId.equals("")) {
                                            selection = "1 ";
                                        GoneVisibility();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }

                                }


                            });
                            btnTwo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        String userId = prefs.getString("id", "");
                                        if (!userId.equals("")) {
                                            selection = "2 ";
                                            GoneVisibility();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            btnThree.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        String userId = prefs.getString("id", "");
                                        if (!userId.equals("")) {
                                            selection = "3 ";
                                            GoneVisibility();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            btnFour.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        String userId = prefs.getString("id", "");
                                        if (!userId.equals("")) {
                                            selection = "4 ";
                                            GoneVisibility();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            btnSix.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        String userId = prefs.getString("id", "");
                                        if (!userId.equals("")) {
                                            selection = "6 ";
                                            GoneVisibility();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            btnOut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        String userId = prefs.getString("id", "");
                                        if (!userId.equals("")) {
                                            selection = "W ";
                                            GoneVisibility();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            btnDot.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        String userId = prefs.getString("id", "");
                                        if (!userId.equals("")) {
                                            selection = "0 ";
                                            GoneVisibility();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            btnRunout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        userId = prefs.getString("id", "");
                                        if (!userId.equals("")) {
                                            selection = "Runout ";
                                            GoneVisibility();
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            reference.child("LiveScore").child("recent").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        String lastBall = dataSnapshot.child("6").getValue().toString();
                                        linearLayout1.setVisibility(View.VISIBLE);
                                        linearLayout2.setVisibility(View.VISIBLE);
                                        predictionHeading.setVisibility(View.VISIBLE );
                                        resultHeading.setVisibility( View.GONE );

                                        if(selection.equals(lastBall)){
                                            SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                            if (isLoggedIn) {
                                                userId = prefs.getString("id", "");
                                                if (!userId.equals(null)) {
                                                    reference.child("UsersPoints").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            int oldPoints = Integer.valueOf(dataSnapshot.child("points").getValue().toString());
                                                            int newPoints = oldPoints+10;
                                                            reference.child("UsersPoints").child(userId).child("points").setValue(newPoints);
                                                            selection = "";
                                                                congratulations();

                                                            //}
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                            }

                                        }
                                        else if(!selection.equals(""))
                                        {
                                            selection="";
                                            AlertDialog.Builder alertadd = new AlertDialog.Builder(LiveScoreActivity.this);
                                            LayoutInflater factory = LayoutInflater.from(LiveScoreActivity.this);
                                            final View view = factory.inflate(R.layout.badluck, null);
                                            //alertadd.setTitle("Opps Better Luck for Next");
                                            alertadd.setView(view);
                                            final AlertDialog alert = alertadd.create();
                                            alert.show();
//// Hide after some seconds
                                            final Handler handler  = new Handler();
                                            final Runnable runnable = new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (alert.isShowing()) {
                                                        alert.dismiss();
                                                    }
                                                }
                                            };

                                            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                @Override
                                                public void onDismiss(DialogInterface dialog) {
                                                    handler.removeCallbacks(runnable);
                                                }
                                            });

                                            handler.postDelayed(runnable, 5000);
                                        }
                                        else {
                                            selection="";
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                } else
                    Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //summary pager code
        reference.child("Schedule").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.child("status").getValue().toString().equals("Live")) {
                            one = dataSnapshot1.child("teamOne").getValue().toString();
                            two = dataSnapshot1.child("teamTwo").getValue().toString();
                            TabLayout tabLayout=(TabLayout) findViewById(R.id.summaryTabLayout);
                            tabLayout.addTab(tabLayout.newTab().setText(one));
                            tabLayout.addTab(tabLayout.newTab().setText(two));
                            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


                            final ViewPager viewPager= (ViewPager) findViewById(R.id.summaryPager);
                            SummaryPagerAdapter pageAdapter=new SummaryPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
                            viewPager.setAdapter(pageAdapter);
//        viewPager.setPageTransformer(true, new ZoomOutTranformer());
                            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {
                                    viewPager.setCurrentItem(tab.getPosition());
                                }

                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {

                                }

                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {

                                }
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        refreshAds();

    }

    private void showDialog() {
        new AlertDialog.Builder( this, R.style.AlertDialogTheme )
                .setTitle( "Win PSL Final Tickets" )
                .setMessage( "Login First to Guess Every Ball and Increase Points to get final tickts  " )
                .setPositiveButton( "ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                } ).create().show();
        SharedPreferences sharedPreferences =getSharedPreferences( "prefs",MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean( "firstStart",false );
        editor.apply();

    }

    private void GoneVisibility() {
        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
        predictionHeading.setVisibility(View.GONE );
        resultHeading.setVisibility( View.VISIBLE );
    }
    private void loadAds(){
        String bannerId="188011879101516_197866138116090";
        String interstitialId="188011879101516_197826204786750";
        bannerAd = new AdView(this, bannerId, AdSize.BANNER_HEIGHT_50);
        interstitialAd = new InterstitialAd(this,interstitialId);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(bannerAd);
        bannerAd.loadAd();

       // AdSettings.addTestDevice("fd87051b-e697-4b8a-a57f-3e2dfa594453");
        interstitialAd.loadAd();

    }
    @Override
    protected void onDestroy() {
        if (bannerAd != null) {
            bannerAd.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(interstitialAd.isAdLoaded()){
            if(!interstitialAd.isAdInvalidated()) {
                interstitialAd.show();
            }
            else{
                interstitialAd.loadAd();
            }
        }
        else{
            rateUS();
        }
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                //Toast.makeText(MainActivity.this, "displayed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Toast.makeText(MainActivity.this, "dismissed", Toast.LENGTH_LONG).show();
                rateUS();
                interstitialAd.loadAd();

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Toast.makeText(MainActivity.this, "error: "+ad+" --"+adError, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Toast.makeText(MainActivity.this, "loaded", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();
                interstitialAd.loadAd();

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                //  Toast.makeText(MainActivity.this, "logged", Toast.LENGTH_LONG).show();
            }
        });


    }
    public void rateUS(){
        LayoutInflater layoutInflater = LayoutInflater.from(LiveScoreActivity.this);
        View promptView = layoutInflater.inflate(R.layout.rateapp, null);

        final AlertDialog alertD = new AlertDialog.Builder(LiveScoreActivity.this).create();

        Button btnCancel = (Button) promptView.findViewById(R.id.btnCancel);
        Button btnExit = (Button) promptView.findViewById(R.id.btnExit);
        Button btnRate = (Button) promptView.findViewById(R.id.btnrateNow);
        btnRate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    reference.child("Applink").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                final String url = dataSnapshot.getValue().toString();
                                Intent viewIntent =
                                        new Intent("android.intent.action.VIEW",
                                                Uri.parse(url));
                                startActivity(viewIntent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        btnCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertD.dismiss();
            }
        } );
        btnExit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );


        alertD.setView(promptView);

        alertD.show();

    }
    public void refreshAds(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bannerAd.loadAd();
                refreshAds();
            }
        }, 10000);
    }



    private void congratulations() {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(LiveScoreActivity.this);
        LayoutInflater factory = LayoutInflater.from(LiveScoreActivity.this);
        final View view = factory.inflate(R.layout.congratulationdialogbox, null);
        alertadd.setView(view);
        final AlertDialog alert = alertadd.create();
        alert.show();
// Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 3000);


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
