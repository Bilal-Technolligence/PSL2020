package com.example.psl2020;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Team2Fragment extends Fragment {
    ArrayList<BatsmanAttr> batsmanAttrs;
    ArrayList<BowlerAttr> bowlerAttrs;
    RecyclerView batsmanRecycler,bowlingRecycler;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    String one , two;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_team2, container, false);
        batsmanRecycler = view.findViewById(R.id.recyclerbating);
        batsmanAttrs = new ArrayList<BatsmanAttr>();
        batsmanRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        bowlingRecycler = view.findViewById(R.id.recyclerbowling);
        bowlerAttrs = new ArrayList<BowlerAttr>();
        bowlingRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        reference.child("Schedule").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.child("status").getValue().toString().equals("Live")) {
                        one = dataSnapshot1.child("teamOne").getValue().toString();
                        two = dataSnapshot1.child("teamTwo").getValue().toString();
                        reference.child("LiveScore").child("summary").child(one).child("batting").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                batsmanAttrs.clear();
                                //profiledata.clear();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    BatsmanAttr p = dataSnapshot1.getValue(BatsmanAttr.class);
                                    batsmanAttrs.add(p);
                                }

                                batsmanRecycler.setAdapter(new BatsmanRecylerView(batsmanAttrs, getActivity()));


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        reference.child("LiveScore").child("summary").child(one).child("bowling").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                bowlerAttrs.clear();
                                //profiledata.clear();
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    BowlerAttr p = dataSnapshot1.getValue(BowlerAttr.class);
                                    bowlerAttrs.add(p);
                                }

                                bowlingRecycler.setAdapter(new BowlerRecylerView(bowlerAttrs, getActivity()));


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
