package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import hotchemi.android.rate.AppRate;

public class LiveScoreActivity extends BaseActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    int progress = 0;
    ProgressBar simpleProgressBar;
    ImageView btnOut, btnOne, btnTwo, btnThree, btnWide, btnSix, btnFour, btnDot, btnNoball, btnRunout;
    ImageView team1, team2;
    TextView score1, score2, over1, over2, wicket1, wicket2, rr1, rr2, match, tosswon;
    String one, two ,elected;
    Integer id,toss;

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

        reference.child("Schedule").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.child("status").getValue().toString().equals("Live")) {
                            one = dataSnapshot1.child("teamOne").getValue().toString();
                            two = dataSnapshot1.child("teamTwo").getValue().toString();
                            id = Integer.parseInt(dataSnapshot1.child("sid").getValue().toString());
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

                            reference.child("LiveScore").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        toss = Integer.parseInt(dataSnapshot.child("tosswin").getValue().toString());
                                        elected = dataSnapshot.child("elected").getValue().toString();
                                        if(toss == 51){
                                            tosswon.setText("Islamabad won the toss and elected to "+elected+ " first.");
                                        }
                                        else if(toss == 12){
                                            tosswon.setText("Karachi won the toss and elected to "+elected+ " first.");
                                        }
                                        else if(toss == 13){
                                            tosswon.setText("Lahore won the toss and elected to "+elected+ " first.");
                                        }
                                        else if(toss == 14){
                                            tosswon.setText("Multan won the toss and elected to "+elected+ " first.");
                                        }
                                        else if(toss == 15){
                                            tosswon.setText("Peshawar won the toss and elected to "+elected+ " first.");
                                        }
                                        else if(toss == 16){
                                            tosswon.setText("Quetta won the toss and elected to "+elected+ " first.");
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            reference.child("Teams").child(one).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        Picasso.get().load(dataSnapshot.child("ImgUrl").getValue().toString()).into(team1);
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
                                        Picasso.get().load(dataSnapshot.child("ImgUrl").getValue().toString()).into(team2);
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
                                        score1.setText(dataSnapshot.child("score").getValue().toString());
                                        wicket1.setText(dataSnapshot.child("wicket").getValue().toString());
                                        over1.setText(dataSnapshot.child("over").getValue().toString());
                                        rr1.setText(dataSnapshot.child("rr").getValue().toString());
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
                                        score2.setText(dataSnapshot.child("score").getValue().toString());
                                        wicket2.setText(dataSnapshot.child("wicket").getValue().toString());
                                        over2.setText(dataSnapshot.child("over").getValue().toString());
                                        rr2.setText(dataSnapshot.child("rr").getValue().toString());
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


        simpleProgressBar = (ProgressBar) findViewById(R.id.progressBar5);


        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               reference.child( "Test" ).child( "ok" ).setValue( 100 ).toString();
//                Toast.makeText( LiveScoreActivity.this, "Done", Toast.LENGTH_SHORT ).show();
                setProgressValue(progress);

            }
        });

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
