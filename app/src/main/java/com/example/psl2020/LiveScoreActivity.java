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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    int progress = 0;
    ProgressBar simpleProgressBar;
    ImageView btnOut, btnOne, btnTwo, btnThree, btnWide, btnSix, btnFour, btnDot, btnNoball, btnRunout;
    ImageView team1, team2;
    TextView score1, score2, over1, over2, wicket1, wicket2, rr1, rr2, match, tosswon, bowler, recent;
    String one, two, elected,scheduleId;
    Integer id, toss;
    TextView bat1, bat2, run1, run2, ball1, ball2, four1, four2, six1, six2, sr1, sr2;
    String selection="", userId="";
    LinearLayout linearLayout1,linearLayout2;
    ImageView live;
    ArrayList<BatsmanAttr> batsmanAttrs;
    ArrayList<BowlerAttr> bowlerAttrs;
    RecyclerView batsmanRecycler,bowlingRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView( R.layout.activity_live_score );
        btnOut = findViewById(R.id.imgOut);
        btnOne = findViewById(R.id.imgOne);
        btnTwo = findViewById(R.id.imgTwo);
        btnThree = findViewById(R.id.imgThree);
        btnWide = findViewById(R.id.imgWide);
        btnSix = findViewById(R.id.imgSix);
        btnFour = findViewById(R.id.imgFour);
        btnDot = findViewById(R.id.imgDot);
        btnNoball = findViewById(R.id.imgNoball);
        btnRunout = findViewById(R.id.imgRunout);

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


        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

//        batsmanRecycler = findViewById(R.id.recyclerbating);
//        batsmanAttrs = new ArrayList<BatsmanAttr>();
//        batsmanRecycler.setLayoutManager(new LinearLayoutManager(this));
//
//
//        bowlingRecycler = findViewById(R.id.recyclerbowling);
//        bowlerAttrs = new ArrayList<BowlerAttr>();
//        bowlingRecycler.setLayoutManager(new LinearLayoutManager(this));



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


                                            if (toss == 51) {
                                                tosswon.setText("Islamabad won the toss and elected to " + elected + " first.");
                                            } else if (toss == 12) {
                                                tosswon.setText("Karachi won the toss and elected to " + elected + " first.");
                                            } else if (toss == 13) {
                                                tosswon.setText("Lahore won the toss and elected to " + elected + " first.");
                                            } else if (toss == 14) {
                                                tosswon.setText("Multan won the toss and elected to " + elected + " first.");
                                            } else if (toss == 15) {
                                                tosswon.setText("Peshawar won the toss and elected to " + elected + " first.");
                                            } else if (toss == 54) {
                                                tosswon.setText("Quetta won the toss and elected to " + elected + " first.");
                                            }
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
                                            linearLayout1.setVisibility(View.GONE);
                                            linearLayout2.setVisibility(View.GONE);
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
                                            linearLayout1.setVisibility(View.GONE);
                                            linearLayout2.setVisibility(View.GONE);
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
                                            linearLayout1.setVisibility(View.GONE);
                                            linearLayout2.setVisibility(View.GONE);
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
                                            linearLayout1.setVisibility(View.GONE);
                                            linearLayout2.setVisibility(View.GONE);
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
                                            linearLayout1.setVisibility(View.GONE);
                                            linearLayout2.setVisibility(View.GONE);
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
                                            linearLayout1.setVisibility(View.GONE);
                                            linearLayout2.setVisibility(View.GONE);
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
                                            linearLayout1.setVisibility(View.GONE);
                                            linearLayout2.setVisibility(View.GONE);
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            btnWide.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        String userId = prefs.getString("id", "");
                                        if (!userId.equals("")) {
                                            selection = "wd";
                                            linearLayout1.setVisibility(View.GONE);
                                            linearLayout2.setVisibility(View.GONE);
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            btnNoball.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                                    boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                                    if (isLoggedIn) {
                                        String userId = prefs.getString("id", "");
                                        if (!userId.equals("")) {
                                            selection = "nb";
                                            linearLayout1.setVisibility(View.GONE);
                                            linearLayout2.setVisibility(View.GONE);
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
                                            selection = "W ";
                                            linearLayout1.setVisibility(View.GONE);
                                            linearLayout2.setVisibility(View.GONE);
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext() , "Log in first" , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            reference.child("LiveScore").child("recent").child("6").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        String lastBall = dataSnapshot.getValue().toString();
                                        linearLayout1.setVisibility(View.VISIBLE);
                                        linearLayout2.setVisibility(View.VISIBLE);
                                        if(selection.contains(lastBall)&& !selection.equals("")){
                                            if(!userId.equals(null)){
                                                reference.child("UserPoints").child(userId).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if(dataSnapshot.exists()){
                                                            int oldPoints = Integer.valueOf(dataSnapshot.child("points").getValue().toString());
                                                            int newPoints = oldPoints+10;
                                                            reference.child("UserPoints").child(userId).child("points").setValue(newPoints);
                                                            selection="";
                                                            congratulations();

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }

                                        }
                                        else
                                        {
                                            selection="";
//                                            AlertDialog.Builder alertadd = new AlertDialog.Builder(LiveScoreActivity.this);
//                                            LayoutInflater factory = LayoutInflater.from(LiveScoreActivity.this);
//                                            final View view = factory.inflate(R.layout.congratulationdialogbox, null);
//                                            alertadd.setTitle("Opps Batter Luck for Next");
//                                            alertadd.setView(view);
//                                            final AlertDialog alert = alertadd.create();
//                                            alert.show();
////// Hide after some seconds
//                                            final Handler handler  = new Handler();
//                                            final Runnable runnable = new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    if (alert.isShowing()) {
//                                                        alert.dismiss();
//                                                    }
//                                                }
//                                            };
//
//                                            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                                                @Override
//                                                public void onDismiss(DialogInterface dialog) {
//                                                    handler.removeCallbacks(runnable);
//                                                }
//                                            });
//
//                                            handler.postDelayed(runnable, 5000);
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
        reference.child("Schedule").addValueEventListener(new ValueEventListener() {
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogTheme);
        builder.setTitle("Rate us and get a chance to win");
        //  builder.setMessage("Rate us and get a chance to win");
        // add the buttons
        builder.setPositiveButton("Rate us",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
        builder.setNeutralButton("Cancel", null);
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

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
