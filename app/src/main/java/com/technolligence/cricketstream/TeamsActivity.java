package com.technolligence.cricketstream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.technolligence.cricketstream.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeamsActivity extends BaseActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    CardView peshawerZalmi,islamabadUnited,quettaGladiator,karachiKings,lahoreQalanders,multanSultan;
    private InterstitialAd interstitialAd;
    private AdView bannerAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
      //  setContentView( R.layout.activity_teams );
        AudienceNetworkAds.initialize(this);
        loadAds();
        peshawerZalmi=findViewById( R.id.cardPeshawerZalmi );
        islamabadUnited=findViewById( R.id.cardIslamabad );

        quettaGladiator=findViewById( R.id.cardQuetta );

        karachiKings=findViewById( R.id.cardKarachi );
        lahoreQalanders=findViewById( R.id.cardLahoreQalandar );

        multanSultan=findViewById( R.id.cardMultanSultan );

        peshawerZalmi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Peshawar Zalmi" );
                startActivity(intent);
            }
        } );
        lahoreQalanders.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Lahore Qalandars" );
                startActivity(intent);
            }
        } );
        karachiKings.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Karachi Kings" );
                startActivity(intent);
            }
        } );
        islamabadUnited.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Islamabad United" );
                startActivity(intent);
            }
        } );
        multanSultan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Multan Sultan" );
                startActivity(intent);
            }
        } );
        quettaGladiator.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Quetta Gladiators" );
                startActivity(intent);
            }
        } );


    }
    private void loadAds(){
        String bannerId="188011879101516_197866138116090";
        String interstitialId="188011879101516_197826204786750";
        bannerAd = new AdView(this, bannerId, AdSize.BANNER_HEIGHT_50);
        interstitialAd = new InterstitialAd(this,interstitialId);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(bannerAd);
        bannerAd.loadAd();

        //AdSettings.addTestDevice("5b0b5bb8-d58d-4d97-9978-b973bec26663");
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
            interstitialAd.show();
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
        LayoutInflater layoutInflater = LayoutInflater.from(TeamsActivity.this);
        View promptView = layoutInflater.inflate(R.layout.rateapp, null);

        final AlertDialog alertD = new AlertDialog.Builder(TeamsActivity.this).create();

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
                                try{startActivity(viewIntent);}
                                catch (Exception e){}
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


    @Override
    int getContentViewId() {
        return R.layout.activity_teams;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_team;
    }
}
