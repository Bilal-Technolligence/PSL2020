package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;

public class Points extends BaseActivity {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    TextView name, point;
    CardView card;
    ImageView userProfileImage;
    ArrayList<PointsAttr> scheduleAttrs;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_points);
        name = findViewById(R.id.txtNamee);
        point = findViewById(R.id.txtPoints);
       userProfileImage = (ImageView)findViewById(R.id.imgProfileOne);
        card = findViewById(R.id.card);
        recyclerView=findViewById(R.id.pointList);
        scheduleAttrs = new ArrayList<PointsAttr>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.child("UsersPoints").orderByChild("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                scheduleAttrs.clear();
                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    PointsAttr p = dataSnapshot1.getValue(PointsAttr.class);
                    scheduleAttrs.add(p);
                }
                Collections.reverse(scheduleAttrs);
                recyclerView.setAdapter(new PointsAdapter(scheduleAttrs ,Points.this));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SharedPreferences prefs = getSharedPreferences("Log", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            String userId = prefs.getString("id", "");
            if (!userId.equals("")) {
                databaseReference.child("UsersPoints").child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            try {
                                name.setText(dataSnapshot.child("name").getValue().toString());
                            }
                            catch (Exception e){}
                            try {
                                point.setText(dataSnapshot.child("points").getValue().toString());
                            }
                            catch (Exception e){}
                            try {
                                Picasso.get().load(dataSnapshot.child("image_url").getValue().toString()).into(userProfileImage);
                            }
                            catch (Exception e){}
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        } else {
            card.setVisibility(View.GONE);
        }
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_points;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}
