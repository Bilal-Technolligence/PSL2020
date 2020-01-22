package com.example.psl2020;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
    TextView textView;
    protected ProgressBar progressBar;
    private CallbackManager callbackManager;
    protected ActionBarDrawerToggle drawerToggle;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(getContentViewId());

        progressBar=(ProgressBar) findViewById(R.id.progress_bar);
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
            }

        } else {
            //profile img click
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //fb login button code
                    callbackManager = CallbackManager.Factory.create();
                    //loginButton= findViewById(R.id.login_button);
                    LoginManager.getInstance().logInWithReadPermissions(BaseActivity.this,Arrays.asList("public_profile","user_friends"));
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
                                        String first_name=object.getString("first_name");
                                        String last_name=object.getString("last_name");
                                        String userId=object.getString("id");
                                        String image_url="https://graph.facebook.com/"+userId+"/picture?type=large";
                                        databaseReference.child("Users").child(userId).child("id").setValue(userId);
                                        databaseReference.child("Users").child(userId).child("image_url").setValue(image_url);
                                        databaseReference.child("Users").child(userId).child("name").setValue(first_name+" "+last_name);
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
                            parameters.putString("fields","first_name,last_name,id,friends");
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
    }
    //fb login code 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
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
        } else if (itemId == R.id.nav_matches) {
            Intent intent = new Intent(this, LiveScoreActivity.class);
            //  intent.putExtra( "id","Friends" );
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_fixture) {
            Intent intent = new Intent(this, MainActivity.class);
            //  intent.putExtra( "id","Family" );
            startActivity(intent);
            finish();
        } else if (itemId == R.id.nav_liveStream) {
            startActivity(new Intent(this, LiveStreamingActivity.class));
            finish();
        } else if (itemId == R.id.nav_more) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (itemId == R.id.inviteFriends) {
            Snackbar.make(drawerLayout, "Invite Friends", Snackbar.LENGTH_LONG).show();

        } else if (itemId == R.id.scoreboard) {
            Snackbar.make(drawerLayout, "scoreboard", Snackbar.LENGTH_LONG).show();
        } else if (itemId == R.id.guideline) {
            Snackbar.make(drawerLayout, "GuideLine", Snackbar.LENGTH_LONG).show();
        } else if (itemId == R.id.rateUs) {
            Snackbar.make(drawerLayout, "rateus", Snackbar.LENGTH_LONG).show();
        } else if (itemId == R.id.logout) {
            //shared prefrences
            SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
            boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
            if (isLoggedIn) {
                String userId=prefs.getString("id","");
                if(!userId.equals("")) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
                Snackbar.make(drawerLayout, "Kindly Login First", Snackbar.LENGTH_LONG).show();
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

