package com.technolligence.cricketstream;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class BowlerFragment extends Fragment {
    ArrayList<TopBallAttr> ballAttrs;
    RecyclerView bowlerRecycler;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bowler, container, false);

        bowlerRecycler = view.findViewById(R.id.recyclerbowler);
        ballAttrs = new ArrayList<TopBallAttr>();
        bowlerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        reference.child("Wickets").orderByChild("wickets").limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ballAttrs.clear();
                //profiledata.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TopBallAttr p = dataSnapshot1.getValue(TopBallAttr.class);
                    ballAttrs.add(p);
                }
                Collections.reverse(ballAttrs);

                bowlerRecycler.setAdapter(new TopBowlerRecylerView(ballAttrs, getActivity()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
