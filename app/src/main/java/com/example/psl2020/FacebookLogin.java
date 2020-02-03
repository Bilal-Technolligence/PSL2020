package com.example.psl2020;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import androidx.appcompat.app.AppCompatActivity;

public class FacebookLogin extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ImageView imageView;
    private TextView name, id;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        imageView=(ImageView) findViewById(R.id.img);
        name=(TextView) findViewById(R.id.name);
        id=(TextView) findViewById(R.id.id);
        SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            String userId=prefs.getString("id","");
            if(!userId.equals("")){
                databaseReference.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            name.setText(dataSnapshot.child("name").getValue().toString());
                            id.setText(dataSnapshot.child("id").getValue().toString());
                            Picasso.get().load(dataSnapshot.child("image_url").getValue().toString()).into(imageView);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        } else {
            Toast.makeText(this, "Not login", Toast.LENGTH_LONG).show();
        }

        final View parentLayout = findViewById(android.R.id.content);

        //fb login button code
        callbackManager = CallbackManager.Factory.create();
        loginButton= findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                String accessToken=loginResult.getAccessToken().getToken();
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
//                    recreate();
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

            }

            @Override
            public void onError(FacebookException error) {
                Snackbar.make(parentLayout, ""+error, Snackbar.LENGTH_LONG).show();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    AccessTokenTracker accessTokenTracker= new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            final View parentLayout = findViewById(android.R.id.content);
            if(currentAccessToken==null){
                Snackbar.make(parentLayout, "Logout", Snackbar.LENGTH_LONG).show();
                SharedPreferences settings = getSharedPreferences("Log", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("isLoggedIn");
                editor.remove("id");
                editor.commit();
            }
            else{
//                LoadUserData(currentAccessToken);
            }
        }
    };
    private void LoadUserData(final AccessToken newAccessToken){
        GraphRequest request= GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String friend = object.getJSONObject("friends").getJSONObject("summary").getString("total_count");
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
//                    recreate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters= new Bundle();
        parameters.putString("fields","first_name,last_name,id,friends");
        request.setParameters(parameters);
        request.executeAsync();
    }
}