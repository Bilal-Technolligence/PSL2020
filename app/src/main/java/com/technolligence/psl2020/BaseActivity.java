package com.technolligence.psl2020;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.technolligence.psl2020.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.RewardData;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    protected BottomNavigationView bottomNavigationView;
    protected DrawerLayout drawerLayout;
    protected NavigationView drawerNavigationView;
    ImageView imageView;
    TextView textView,pointsTextView;
    ShareDialog shareDialog=new ShareDialog(this);
    String link;
    private CallbackManager callbackManager;
    protected ActionBarDrawerToggle drawerToggle;
    private RewardedVideoAd rewardedVideoAd;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(getContentViewId());
        AudienceNetworkAds.initialize(this);
        rewardedVideoAd = new RewardedVideoAd(this, "188011879101516_197869234782447");

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //drawer navigation
        drawerLayout = (DrawerLayout) findViewById(R.id.drawarLayout);
        //adding drawar button
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //drawer navbar item click
        drawerNavigationView = (NavigationView) findViewById(R.id.drawerNavigationView);
        drawerNavigationView.setNavigationItemSelectedListener(this);

        //header click navbar
        View headerview = drawerNavigationView.getHeaderView(0);
        imageView = (ImageView) headerview.findViewById(R.id.profile_image);
        textView=(TextView) headerview.findViewById(R.id.name);
        pointsTextView=(TextView) headerview.findViewById(R.id.userPoints);

        //shared prefrences
        SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            String userId=prefs.getString("id","");
            if(!userId.equals("")){
                databaseReference.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            textView.setText(dataSnapshot.child("name").getValue().toString());
                            Picasso.get().load(dataSnapshot.child("image_url").getValue().toString()).into(imageView);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                databaseReference.child("UsersPoints").child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try{
                            if(dataSnapshot.exists()){
                                pointsTextView.setText(dataSnapshot.child("points").getValue().toString()+" Points");
                            }
                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        } else {
            //profile img click
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //fb login button code
                    callbackManager = CallbackManager.Factory.create();
                    //loginButton= findViewById(R.id.login_button);
                    LoginManager.getInstance().logInWithReadPermissions(BaseActivity.this,Arrays.asList("public_profile"));
                    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(final LoginResult loginResult) {
//                            String accessToken=loginResult.getAccessToken().getToken();
                            GraphRequest request=GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    try {
//                            name.setText( object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));
//                            Toast.makeText(getApplicationContext(), friend, Toast.LENGTH_LONG).show();
                                        final String first_name=object.getString("first_name");
                                        final String last_name=object.getString("last_name");
                                        final String userId=object.getString("id");
                                        final String image_url="https://graph.facebook.com/"+userId+"/picture?type=large";
                                        databaseReference.child("Users").child(userId).child("id").setValue(userId);
                                        databaseReference.child("Users").child(userId).child("image_url").setValue(image_url);
                                        databaseReference.child("Users").child(userId).child("name").setValue(first_name+" "+last_name);
                                        databaseReference.child("UsersPoints").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (!dataSnapshot.exists()) {
                                                    try {
                                                        databaseReference.child("UsersPoints").child(userId).child("id").setValue(userId);
                                                    }
                                                    catch (Exception e){}
                                                    try {
                                                        databaseReference.child("UsersPoints").child(userId).child("image_url").setValue(image_url);
                                                    }
                                                    catch (Exception e){}
                                                    try {
                                                        databaseReference.child("UsersPoints").child(userId).child("name").setValue(first_name + " " + last_name);
                                                    }
                                                    catch (Exception e){}
                                                    try {
                                                        databaseReference.child("UsersPoints").child(userId).child("points").setValue(0);
                                                    }catch (Exception e){}

                                                }
                                                else{
                                                    //databaseReference.child("UsersPoints").child(userId).child("image_url").setValue(image_url);
                                                    try{databaseReference.child("UsersPoints").child(userId).child("name").setValue(first_name + " " + last_name);
                                                }catch (Exception e){}
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        SharedPreferences.Editor editor = getSharedPreferences("Log", MODE_PRIVATE).edit();
                                        editor.putBoolean("isLoggedIn", true );
                                        editor.putString("id", userId );
                                        editor.commit();
                                        recreate();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                            Bundle parameters= new Bundle();
                            parameters.putString("fields","first_name,last_name,id");
                            request.setParameters(parameters);
                            request.executeAsync();
                        }

                        @Override
                        public void onCancel() {
                            Snackbar.make(drawerLayout, "You cancel, kindly try again", Snackbar.LENGTH_LONG).show();

                        }

                        @Override
                        public void onError(FacebookException error) {
                            Snackbar.make(drawerLayout, ""+error, Snackbar.LENGTH_LONG).show();


                        }
                    });
                }
            });
        }
        //get app link
        databaseReference.child("Applink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    if(dataSnapshot.exists()){
                        link=dataSnapshot.getValue().toString();
                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //fb login code 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        if (rewardedVideoAd != null) {
            rewardedVideoAd.destroy();
            rewardedVideoAd = null;
        }
        super.onDestroy();
    }
    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else if (itemId == R.id.nav_team) {
            Intent intent = new Intent(this, TeamsActivity.class);
            //  intent.putExtra( "id","Friends" );
            startActivity(intent);
            finish();
        }
        else if (itemId == R.id.nav_matches) {
            Intent intent = new Intent(this, LiveScoreActivity.class);
            //  intent.putExtra( "id","Friends" );
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_statistics) {
            Intent intent = new Intent(this, Statistics.class);
            //  intent.putExtra( "id","Family" );
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_liveStream) {
            startActivity(new Intent(this, LiveStreamingActivity.class));
            finish();
        }else if(itemId == R.id.rewardPoints){
            //shared prefrences
            SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
            if (isLoggedIn) {
                final String userId=prefs.getString("id","");
                if(!userId.equals("")) {

                    rewardedVideoAd.setAdListener(new RewardedVideoAdListener() {
                        @Override
                        public void onError(Ad ad, AdError error) {
                          //  Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAdLoaded(Ad ad) {
                            rewardedVideoAd.show();
                           // Toast.makeText(getApplicationContext(), ""+ad, Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onAdClicked(Ad ad) {
                          // Toast.makeText(getApplicationContext(), ""+ad, Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {
                          //  Toast.makeText(getApplicationContext(), ""+ad, Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onRewardedVideoCompleted() {
                           // Toast.makeText(getApplicationContext(), "completed", Toast.LENGTH_LONG).show();

                            databaseReference.child("UsersPoints").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // if(dataSnapshot.exists()){
                                    int oldPoints = Integer.valueOf(dataSnapshot.child("points").getValue().toString());
                                    int newPoints = oldPoints+100;
                                    databaseReference.child("UsersPoints").child(userId).child("points").setValue(newPoints);

                                    //}
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onRewardedVideoClosed() {
                            //Toast.makeText(getApplicationContext(), "close", Toast.LENGTH_LONG).show();

                        }
                    });
                    //Set the rewarded ad data
                    rewardedVideoAd.setRewardData(new RewardData(userId, "100 Points"));
                    rewardedVideoAd.loadAd();

                }
            }
            else{
                Snackbar.make(drawerLayout, "Kindly Login First", Snackbar.LENGTH_LONG).show();
            }

        } else if (itemId == R.id.inviteFriends) {
            //shared prefrences
            SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
            if (isLoggedIn) {
                final String userId=prefs.getString("id","");
                if(!userId.equals("")) {

                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(link))
                                .setQuote("Download this app for PSL live score, standings, updates and all latest news. Play and get a chance to win PSL 2020 final ticket.")
                                .build();
                        shareDialog.show(linkContent);
                    }
                    callbackManager = CallbackManager.Factory.create();
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result result) {
                                    databaseReference.child("UsersPoints").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            // if(dataSnapshot.exists()){
                                            int oldPoints = Integer.valueOf(dataSnapshot.child("points").getValue().toString());
                                            int newPoints = oldPoints+50;
                                            databaseReference.child("UsersPoints").child(userId).child("points").setValue(newPoints);

                                            //}
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {
                            Snackbar.make(drawerLayout, ""+ error, Snackbar.LENGTH_INDEFINITE).show();


                        }
                    });

                }
            }
            else{
                Snackbar.make(drawerLayout, "Kindly Login First", Snackbar.LENGTH_LONG).show();
            }







        } else if (itemId == R.id.scoreboard) {
            Intent intent = new Intent(this, Points.class);
            startActivity(intent);

        } else if (itemId == R.id.guideline) {
            Intent intent = new Intent(this, GuideLine.class);
            startActivity(intent);

        } else if (itemId == R.id.rateUs) {
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
        } else if (itemId == R.id.logout) {
            //shared prefrences
            SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
            if (isLoggedIn) {
                String userId=prefs.getString("id","");
                if(!userId.equals("")) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.AlertDialogTheme);
                    alertDialogBuilder.setMessage("Are you sure want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            LoginManager.getInstance().logOut();
                            Snackbar.make(drawerLayout, "Logout ok", Snackbar.LENGTH_LONG).show();
                                        SharedPreferences settings = getSharedPreferences("Log", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.remove("isLoggedIn");
                                        editor.remove("id");
                                        editor.commit();
                                        recreate();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
                }
            else{
                Snackbar.make(drawerLayout, "You are not login", Snackbar.LENGTH_LONG).show();
            }


        }

        return true;
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

    ////////drawer navigation code///////////


    //drawer open close click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

