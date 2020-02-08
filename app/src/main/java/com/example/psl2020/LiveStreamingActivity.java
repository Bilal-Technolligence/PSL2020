package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Vector;

public class LiveStreamingActivity extends BaseActivity {
    VideoView videoView;
    ProgressDialog pd;
    String videoUrl ="https://www.sonyliv.com/details/live/6077649606001/Aljazeera-Channel";
 //   RecyclerView webView1;
 //   Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();
 //   private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  webView1 = (RecyclerView) findViewById(R.id.web1);
      //  webView1.setHasFixedSize(true);
      videoView = findViewById( R.id.videoView );
      Context context;
      pd = new ProgressDialog(this);
      pd.setMessage( "Buffering.." );
      pd.setCancelable( true );

        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoView);
        mc.setMediaPlayer(videoView);
        Uri video = Uri.parse(videoUrl);
        videoView.setMediaController(mc);
        videoView.setVideoURI(video);
        videoView.start();

    //  playVideo();


//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//
//        webView1.setLayoutManager(layoutManager1);
//
//        reference.child("LiveMatch").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    try {
//                        if (dataSnapshot.exists()) {
//                            youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + dataSnapshot1.child("url").getValue().toString() + "\" frameborder=\"0\" allowfullscreen=\"true\"></iframe>"));
//                        }
//                        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
//                        webView1.setAdapter(videoAdapter);
//
//                    } catch (Exception e) {
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

//    private void playVideo() {
//        try {
//            getWindow().setFormat( PixelFormat.TRANSLUCENT );
//           // MediaController mediaController = new MediaController( this );
//
//
//
//
////            mediaController.setAnchorView( videoView );
////            Uri videoUri = Uri.parse(videoUrl);
////            videoView.setMediaController(mediaController);
////            videoView.setVideoURI( videoUri );
////            videoView.requestFocus();
////
////            videoView.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
////                @Override
////                public void onPrepared(MediaPlayer mp) {
////                    pd.dismiss();
////                    videoView.start();
////                }
////            } );
//
//        }catch (Exception e){}
//    }

    @Override
    public void onBackPressed() {

        return;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogTheme);
//        builder.setTitle("Rate us and get a chance to win");
//      //  builder.setMessage("Rate us and get a chance to win");
//        // add the buttons
//        builder.setPositiveButton("Rate Now",  new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                try {
//                    databaseReference.child("Applink").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if(dataSnapshot.exists()){
//                                final String url = dataSnapshot.getValue().toString();
//                                Intent viewIntent =
//                                        new Intent("android.intent.action.VIEW",
//                                                Uri.parse(url));
//                                startActivity(viewIntent);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }catch(Exception e) {
//                    Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...",
//                            Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
//        builder.setNeutralButton("Remind me later", null);
//        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        // create and show the alert dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//

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
