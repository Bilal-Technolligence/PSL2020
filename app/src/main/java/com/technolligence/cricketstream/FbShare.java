package com.technolligence.cricketstream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.technolligence.cricketstream.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FbShare extends AppCompatActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialog=new ShareDialog(this);
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_share);
         Button button=(Button) findViewById(R.id.fb_share_button);
        callbackManager = CallbackManager.Factory.create();
        reference.child("Applink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    link=dataSnapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(link))
                    .setContentTitle("Win PSL 2020 Final Tickets")
                    .setQuote("Download this app for PSL live score, standings, updates and all latest news. Play and get a chance to win PSL 2020 final ticket.")
                    .setContentDescription("Download this app for PSL live score, standings, updates and all latest news. Play and get a chance to win PSL 2020 final ticket.")
                    .build();
            shareDialog.show(linkContent);
        }
             }
         });

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                //shared prefrences
                SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
                boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
                if (isLoggedIn) {
                    final String userId=prefs.getString("id","");
                    if(!userId.equals("")) {
                        reference.child("UsersPoints").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               // if(dataSnapshot.exists()){
                                    int oldPoints = Integer.valueOf(dataSnapshot.child("points").getValue().toString());
                                    int newPoints = oldPoints+50;
                                    reference.child("UsersPoints").child(userId).child("points").setValue(newPoints);

                                //}
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Kindly Login First", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
