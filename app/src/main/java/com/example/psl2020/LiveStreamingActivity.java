package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Vector;

public class LiveStreamingActivity extends BaseActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    RecyclerView webView1;
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        webView1 = (RecyclerView) findViewById(R.id.web1);
        webView1.setHasFixedSize(true);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        webView1.setLayoutManager(layoutManager1);

        reference.child("LiveMatch").addValueEventListener(new ValueEventListener() {
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


    }

    @Override
    int getContentViewId() {
        return R.layout.activity_live_streaming;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_liveStream;
    }
}
