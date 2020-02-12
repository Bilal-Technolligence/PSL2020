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
import java.util.Collection;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class BatsmanFragment extends Fragment {
    ArrayList<TopBatAttr> batAttrs;
    RecyclerView batsmanRecycler;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_batsman, container, false);

        batsmanRecycler = view.findViewById(R.id.recyclerbating);
        batAttrs = new ArrayList<TopBatAttr>();
        batsmanRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        reference.child("Runs").orderByChild("score").limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                batAttrs.clear();
                //profiledata.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TopBatAttr p = dataSnapshot1.getValue(TopBatAttr.class);
                    batAttrs.add(p);
                }
                Collections.reverse(batAttrs);

                batsmanRecycler.setAdapter(new TopBatsmanRecylerView(batAttrs, getActivity()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

}
