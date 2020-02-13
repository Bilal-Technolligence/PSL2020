package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import org.jsoup.Connection;

public class GuideLine extends BaseActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    private InterstitialAd interstitialAd;
    private AdView bannerAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_guide_line);
        AudienceNetworkAds.initialize(this);
        loadAds();
        refreshAds();
    }
    private void loadAds(){
        String bannerId="188011879101516_197866138116090";
        String interstitialId="188011879101516_197826204786750";
        bannerAd = new AdView(this, bannerId, AdSize.BANNER_HEIGHT_50);
        interstitialAd = new InterstitialAd(this,interstitialId);
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.addView(bannerAd);
        bannerAd.loadAd();

      //  AdSettings.addTestDevice("5b0b5bb8-d58d-4d97-9978-b973bec26663");
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
        return R.layout.activity_guide_line;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.guideline;
    }
}
