package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;

public class Points extends BaseActivity {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    TextView name, point;
    CardView card;
    ImageView userProfileImage;
    ArrayList<PointsAttr> scheduleAttrs;
    RecyclerView recyclerView;
    private InterstitialAd interstitialAd;
    private AdView bannerAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_points);
        name = findViewById(R.id.txtNamee);
        point = findViewById(R.id.txtPoints);
        AudienceNetworkAds.initialize(this);
        loadAds();
       userProfileImage = (ImageView)findViewById(R.id.imgProfileOne);
        card = findViewById(R.id.card);
        recyclerView=findViewById(R.id.pointList);
        scheduleAttrs = new ArrayList<PointsAttr>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.child("UsersPoints").orderByChild("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scheduleAttrs.clear();
                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    PointsAttr p = dataSnapshot1.getValue(PointsAttr.class);
                    scheduleAttrs.add(p);
                }
                Collections.reverse(scheduleAttrs);
                recyclerView.setAdapter(new PointsAdapter(scheduleAttrs ,Points.this));


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
                            }
                            catch (Exception e){}
                            try {
                                point.setText(dataSnapshot.child("points").getValue().toString());
                            }
                            catch (Exception e){}
                            try {
                                Picasso.get().load(dataSnapshot.child("image_url").getValue().toString()).into(userProfileImage);
                            }
                            catch (Exception e){}
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
        refreshAds();
    }

    private void loadAds(){
        String bannerId="IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";
        String interstitialId="YOUR_PLACEMENT_ID";
        bannerAd = new AdView(this, bannerId, AdSize.BANNER_HEIGHT_50);
        interstitialAd = new InterstitialAd(this,interstitialId);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(bannerAd);
        bannerAd.loadAd();

        AdSettings.addTestDevice("5b0b5bb8-d58d-4d97-9978-b973bec26663");
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
            finish();
        }
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                //Toast.makeText(MainActivity.this, "displayed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Toast.makeText(MainActivity.this, "dismissed", Toast.LENGTH_LONG).show();
            finish();
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
    public void refreshAds(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bannerAd.loadAd();
                refreshAds();
            }
        }, 10000);
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
