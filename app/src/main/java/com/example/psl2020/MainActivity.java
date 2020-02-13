package com.example.psl2020;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.Context;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    ArrayList<ScheduleAttr> scheduleAttrs;
    RecyclerView recyclerView;
    ArrayList<NewsDataClass> newsDataClassArray = new ArrayList<>();
    RecyclerView webView1;
    ArrayList<String> str=new ArrayList<>();
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();
    private final String CHANNEL_ID ="personal" ;
    public final int NOTIFICATION_ID = 001;
    //ads instances
    private InterstitialAd interstitialAd;
    private AdView bannerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel( CHANNEL_ID,CHANNEL_ID,importance );
            NotificationManager notificationManager = (NotificationManager)getSystemService( NOTIFICATION_SERVICE );
            notificationManager.createNotificationChannel( notificationChannel );
        }
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "i got";
                        if (!task.isSuccessful()) {
                            msg = "fail";
                        }
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });




        Context context;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        AudienceNetworkAds.initialize(this);
        loadAds();
        recyclerView = findViewById(R.id.matchesRecyclerView);
        scheduleAttrs = new ArrayList<ScheduleAttr>();
        recyclerView.setLayoutManager(layoutManager);

        final int duration = 3000;
        final int pixelsToMove = 250;
        final Handler mHandler = new Handler( Looper.getMainLooper());
        final Runnable SCROLLING_RUNNABLE = new Runnable() {

            @Override
            public void run() {
                recyclerView.smoothScrollBy(pixelsToMove, 0);
                mHandler.postDelayed(this, duration);
            }
        };

        reference.child("Schedule").orderByChild("winner").equalTo("Upcoming").limitToFirst(3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scheduleAttrs.clear();
                //profiledata.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ScheduleAttr p = dataSnapshot1.getValue(ScheduleAttr.class);
                    scheduleAttrs.add(p);
                }

                recyclerView.setAdapter(new MatchesRecylerView(scheduleAttrs, getApplicationContext()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItem = layoutManager.findLastCompletelyVisibleItemPosition();
                if(lastItem == layoutManager.getItemCount()-1){
                   mHandler.removeCallbacks(SCROLLING_RUNNABLE);
                    Handler postHandler = new Handler();
                    postHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(null);
                            recyclerView.setAdapter(new MatchesRecylerView(scheduleAttrs, getApplicationContext()));
                            mHandler.postDelayed(SCROLLING_RUNNABLE, 6000);
                        }
                    }, 7000);
                }
            }
        });
        mHandler.postDelayed(SCROLLING_RUNNABLE, 8000);


        webView1 = (RecyclerView) findViewById(R.id.web1);
        webView1.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        webView1.setLayoutManager(layoutManager1);

        reference.child("RecentVideos").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProgressBar progressBar1=(ProgressBar) findViewById(R.id.progress_barVideos);
                progressBar1.setVisibility(View.VISIBLE);

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        // Toast.makeText(getApplicationContext() ,dataSnapshot1.child("url").getValue().toString(), Toast.LENGTH_LONG).show();
                        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + dataSnapshot1.child("url").getValue().toString() + "\" frameborder=\"0\" allowfullscreen=\"true\"></iframe>"));
                    }
                    VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
                    webView1.setAdapter(videoAdapter);
                    progressBar1.setVisibility(View.GONE);
                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        new ScrapeNews().execute();
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

        AdSettings.addTestDevice("24e32ca0-8624-4848-a75c-5eace9d8af87");
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
    public void refreshAds(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bannerAd.loadAd();
                refreshAds();
            }
        }, 10000);
    }
    private void fetchNews(){
        RecyclerView newsRecyclerView;
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setItemViewCacheSize(20);
        newsRecyclerView.setDrawingCacheEnabled(true);
        newsRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        str.add("2");
//        str.add("as");
        newsRecyclerView.setAdapter(new NewsAdapter(newsDataClassArray, MainActivity.this));

    }


    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }

    public  class ScrapeNews extends AsyncTask<Void,Void,Void>{
        ProgressBar progressBar=(ProgressBar) findViewById(R.id.progress_bar);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
           // Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url="https://www.geosuper.tv/featured-news";
                Document document=Jsoup.connect(url).get();
                Elements element=document.select("li.col-xs-12");
                int size=element.size();
                for(int i=0;i<size;i++){
                    String imgUrl=element.select("li.col-xs-12").select("img").eq(i).attr("src");
                    String title=element.select("li.col-xs-12").select("h4").select("a").eq(i).attr("title");
                    String detail=element.select("li.col-xs-12").select("h4").select("a").eq(i).attr("href");
                    String datetime=element.select("li.col-xs-12").select("div.meta-tag").select("li").eq(i).text();
                    newsDataClassArray.add(new NewsDataClass(imgUrl,title,detail,datetime));
                }
            } catch (IOException e) {
                e.printStackTrace();
              //  Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            fetchNews();
           // Toast.makeText(getApplicationContext(), "success "+aVoid, Toast.LENGTH_LONG).show();
        }
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
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.rateapp, null);

        final AlertDialog alertD = new AlertDialog.Builder(MainActivity.this).create();

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



}
