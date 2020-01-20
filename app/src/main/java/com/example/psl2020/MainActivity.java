package com.example.psl2020;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import hotchemi.android.rate.AppRate;

public class MainActivity extends BaseActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        //fb login button code
        callbackManager = CallbackManager.Factory.create();
        loginButton= findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email","public_profile","user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Snackbar.make(drawerLayout, ""+error, Snackbar.LENGTH_LONG).show();

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
            if(currentAccessToken==null){
                Snackbar.make(drawerLayout, "Sorry", Snackbar.LENGTH_LONG).show();
            }
            else{
                LoadUserData(currentAccessToken);
            }
        }
    };
    private void LoadUserData(AccessToken newAccessToken){
        GraphRequest request= GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name=object.getString("first_name");
                    String last_name=object.getString("last_name");
                    String email=object.getString("email");
                    String id=object.getString("id");
                    String image_url="https://graph.facebook.com/"+id+"/picture?type=normal";
                    Snackbar.make(drawerLayout, first_name+" "+last_name, Snackbar.LENGTH_LONG).show();
//                    RequestOptions requestOptions=new RequestOptions();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters= new Bundle();
        parameters.putString("fields","first_name,last_name,email,id,");
        request.setParameters(parameters);
        request.executeAsync();
      //  setContentView( R.layout.activity_main );
        AppRate.with(this)
                .setInstallDays( 0 )
                .setLaunchTimes( 3 )
                .setRemindInterval( 5 )
                .monitor();

        AppRate.showRateDialogIfMeetsConditions( this );
        AppRate.with( this ).clearAgreeShowDialog();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
