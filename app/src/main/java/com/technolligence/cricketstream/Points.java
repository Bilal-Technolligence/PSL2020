package com.technolligence.cricketstream;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class Points extends BaseActivity {
    TextView name, point;
    CardView card;
    ImageView userProfileImage;
    ArrayList<PointsAttr> scheduleAttrs;
    RecyclerView recyclerView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private InterstitialAd interstitialAd;
    private AdView bannerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_points);
        name = findViewById(R.id.txtNamee);
        point = findViewById(R.id.txtPoints);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        loadAds();
        userProfileImage = (ImageView) findViewById(R.id.imgProfileOne);
        card = findViewById(R.id.card);
        recyclerView = findViewById(R.id.pointList);
        scheduleAttrs = new ArrayList<PointsAttr>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.child("UsersPoints").orderByChild("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scheduleAttrs.clear();
                //profiledata.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    PointsAttr p = dataSnapshot1.getValue(PointsAttr.class);
                    scheduleAttrs.add(p);
                }
                Collections.reverse(scheduleAttrs);
                recyclerView.setAdapter(new PointsAdapter(scheduleAttrs, Points.this));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            String userId = prefs.getString("id", "");
            if (!userId.equals("")) {
                databaseReference.child("UsersPoints").child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            try {
                                name.setText(dataSnapshot.child("name").getValue().toString());
                            } catch (Exception e) {
                            }
                            try {
                                point.setText(dataSnapshot.child("points").getValue().toString());
                            } catch (Exception e) {
                            }
                            try {
                                Picasso.get().load(dataSnapshot.child("image_url").getValue().toString()).into(userProfileImage);
                            } catch (Exception e) {
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        } else {
            card.setVisibility(View.GONE);
        }

    }

    private void loadAds() {
        bannerAd = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);

        AdRequest adRequest1 = new AdRequest.Builder().build();
        InterstitialAd.load(this,getResources().getString(R.string.interstitial_full_screen), adRequest1, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd i) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                interstitialAd = i;
                Log.i(TAG, "onAdLoaded");
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        //Log.d("TAG", "The ad was dismissed.");
                        //finish();

                        finish();
                        //interstitialAd.loadAd();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        interstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i(TAG, loadAdError.getMessage());
                interstitialAd = null;
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (bannerAd != null) {
            bannerAd.destroy();
        }
        if (interstitialAd != null) {
            //interstitialAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            finish();
        }
    }


    @Override
    int getContentViewId() {
        return R.layout.activity_points;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
