package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.api.Context;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlayersActivity extends BaseActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    ArrayList<PlayerAttributes> playerAttributes;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
       // setContentView( R.layout.activity_players );


        Context context;
        //GridLayoutManager layoutManager= new GridLayoutManager(this, GridLayout., false );

        recyclerView=findViewById(R.id.teamsRecyclerView);
        playerAttributes = new ArrayList<PlayerAttributes>();
        recyclerView.setLayoutManager(new GridLayoutManager( this,2 ));

        reference.child("Players").child( "Peshawar Zalmi" ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                playerAttributes.clear();
                //profiledata.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    PlayerAttributes p = dataSnapshot1.getValue(PlayerAttributes.class);
                    playerAttributes.add(p);
                }

                recyclerView.setAdapter(new PlayerRecylerView(playerAttributes ,getApplicationContext()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_players;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_team;
    }
}
