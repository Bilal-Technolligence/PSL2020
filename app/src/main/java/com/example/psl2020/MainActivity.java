package com.example.psl2020;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.Context;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.rateapp);
        dialog.setTitle("Cricket Express PSL2020...");

        Context context;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView = findViewById(R.id.matchesRecyclerView);
        scheduleAttrs = new ArrayList<ScheduleAttr>();
        recyclerView.setLayoutManager(layoutManager);

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
        webView1 = (RecyclerView) findViewById(R.id.web1);
        webView1.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        webView1.setLayoutManager(layoutManager1);

        reference.child("RecentVideos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        // Toast.makeText(getApplicationContext() ,dataSnapshot1.child("url").getValue().toString(), Toast.LENGTH_LONG).show();
                        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + dataSnapshot1.child("url").getValue().toString() + "\" frameborder=\"0\" allowfullscreen=\"true\"></iframe>"));
                    }
                    VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
                    webView1.setAdapter(videoAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/lsNcUkAFxuk\" frameborder=\"0\" allowfullscreen=\"true\"></iframe>"));


        // set the custom dialog components - text, image and button
//        Button remind = (Button) dialog.findViewById(R.id.remindLater);
//        remind.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button rateNow = (Button) dialog.findViewById(R.id.dialogButtonOK);
//        // if button is clicked, close the custom dialog
//        rateNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                redirect();
//
//                dialog.dismiss();
//            }
//
//
//        });
//
//        dialog.show();
        new ScrapeNews().execute();

    }

    private void fetchNews(){
        RecyclerView newsRecyclerView;
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        str.add("2");
//        str.add("as");
        newsRecyclerView.setAdapter(new NewsAdapter(newsDataClassArray, MainActivity.this));

    }

    private void redirect() {
        String link1 = "<a href=\"https://play.google.com/store/apps\">https://play.google.com/store/apps</a>";
        String message = "Some links: " + link1 + "link1, link2, link3";
        Spanned myMessage = Html.fromHtml(message);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("This is a title");
        builder.setMessage(myMessage);
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView msgTxt = (TextView) alertDialog.findViewById(android.R.id.message);
        msgTxt.setMovementMethod(LinkMovementMethod.getInstance());
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogTheme);
        builder.setTitle("Rate us and get a chance to win");
        //  builder.setMessage("Rate us and get a chance to win");
        // add the buttons
        builder.setPositiveButton("Rate Now",  new DialogInterface.OnClickListener() {
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
        builder.setNeutralButton("Remind me later", null);
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
}
