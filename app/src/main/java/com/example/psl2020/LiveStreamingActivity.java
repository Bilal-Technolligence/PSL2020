package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
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

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String url = "";
    WebView mWebView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mWebView = (WebView) findViewById(R.id.webView);
        progressBar.setVisibility(View.VISIBLE);
        mWebView.setWebViewClient(new Browser_home());
        mWebView.setWebChromeClient(new MyChrome());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);


        databaseReference.child("LiveMatch").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    url = dataSnapshot.child( "1" ).child( "url" ).getValue(  ).toString();
                    loadWebsite(url);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadWebsite(String url) {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            mWebView.loadUrl( this.url );
        } else {
            mWebView.setVisibility(View.GONE);
        }
    }

    class Browser_home extends WebViewClient {

        Browser_home() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
          //  setTitle(view.getTitle());
            progressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);

        }
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

//        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//        mWebView = (WebView) findViewById(R.id.webView);
//
//        progressBar.setVisibility(View.VISIBLE);
//        mWebView.setWebViewClient(new Browser_home());
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setAppCacheEnabled(true);
//        loadWebsite();
//
//    }
//
//    private void loadWebsite() {
//        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
//
//
//            databaseReference.child("LiveMatch").addValueEventListener(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                  //  ProgressBar progressBar1=(ProgressBar) findViewById(R.id.progress_barVideos);
////                    progressBar1.setVisibility(View.VISIBLE);
//                        if (dataSnapshot.exists()) {
//                          url = dataSnapshot.child( "1" ).getValue(  ).toString();
//                            mWebView.loadUrl(url);
//                      //  progressBar1.setVisibility(View.GONE);
//                    }
//
//
//
//                }
//
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//        } else {
//            mWebView.setVisibility(View.GONE);
//        }
//    }
//
//    class Browser_home extends WebViewClient {
//
//        Browser_home() {
//        }
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            setTitle(view.getTitle());
//            progressBar.setVisibility(View.GONE);
//            super.onPageFinished(view, url);
//
//        }
//    }
////        mWebView = (WebView) findViewById(R.id.webView);
////        mWebView.setWebChromeClient(new WebChromeClient());
////        WebSettings webSettings = mWebView.getSettings();
////        webSettings.setJavaScriptEnabled(true);
////        webSettings.setUseWideViewPort(true);
////        webSettings.setLoadWithOverviewMode(true);
////        mWebView.loadUrl(url);
////
////        mWebView.setWebViewClient(new WebViewClient() {
////            @Override
////            public void onPageStarted(WebView view, String url, Bitmap favicon) {
////                super.onPageStarted(view, url, favicon);
////            }
////            @SuppressWarnings("deprecation")
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                return false;
////            }
////
////            @TargetApi(Build.VERSION_CODES.N)
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
////                view.loadUrl(request.getUrl().toString());
////                return true;
////            }
////        });
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
                    databaseReference.child("Applink").addValueEventListener(new ValueEventListener() {
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

    @Override
    int getContentViewId() {
        return R.layout.activity_live_streaming;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_liveStream;
    }
}
