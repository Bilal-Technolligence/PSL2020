package com.example.psl2020;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Scoreboard extends Fragment {
    TextView iuwin, iulose, iumatch, iupoint, iutie, iunrr;
    TextView lqwin, lqlose, lqmatch, lqpoint, lqtie, lqnrr;
    TextView kkwin, kklose, kkmatch, kkpoint, kktie, kknrr;
    TextView mswin, mslose, msmatch, mspoint, mstie, msnrr;
    TextView pzwin, pzlose, pzmatch, pzpoint, pztie, pznrr;
    TextView qgwin, qglose, qgmatch, qgpoint, qgtie, qgnrr;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        iumatch = (TextView) view.findViewById(R.id.txtIUMatches);
        iuwin = (TextView) view.findViewById(R.id.txtIUWin);
        iulose = (TextView) view.findViewById(R.id.txtIULose);
        iutie = (TextView) view.findViewById(R.id.txtIUTie);
        iupoint = (TextView) view.findViewById(R.id.txtIUPts);
        iunrr = (TextView) view.findViewById(R.id.txtIUNRR);

        lqmatch = (TextView) view.findViewById(R.id.txtLQMatches);
        lqwin = (TextView) view.findViewById(R.id.txtLQWin);
        lqlose = (TextView) view.findViewById(R.id.txtLQLose);
        lqtie = (TextView) view.findViewById(R.id.txtLQTie);
        lqpoint = (TextView) view.findViewById(R.id.txtLQPts);
        lqnrr = (TextView) view.findViewById(R.id.txtLQNRR);

        kkmatch = (TextView) view.findViewById(R.id.txtKKMatches);
        kkwin = (TextView) view.findViewById(R.id.txtKKWin);
        kklose = (TextView) view.findViewById(R.id.txtKKLose);
        kktie = (TextView) view.findViewById(R.id.txtKKTie);
        kkpoint = (TextView) view.findViewById(R.id.txtKKPts);
        kknrr = (TextView) view.findViewById(R.id.txtKKNRR);

        msmatch = (TextView) view.findViewById(R.id.txtMSMatches);
        mswin = (TextView) view.findViewById(R.id.txtMSWin);
        mslose = (TextView) view.findViewById(R.id.txtMSLose);
        mstie = (TextView) view.findViewById(R.id.txtMSTie);
        mspoint = (TextView) view.findViewById(R.id.txtMSPts);
        msnrr = (TextView) view.findViewById(R.id.txtMSNRR);

        pzmatch = (TextView) view.findViewById(R.id.txtPZMatches);
        pzwin = (TextView) view.findViewById(R.id.txtPZWin);
        pzlose = (TextView) view.findViewById(R.id.txtPZLose);
        pztie = (TextView) view.findViewById(R.id.txtPZTie);
        pzpoint = (TextView) view.findViewById(R.id.txtPZPts);
        pznrr = (TextView) view.findViewById(R.id.txtPZNRR);

        qgmatch = (TextView) view.findViewById(R.id.txtQGMatches);
        qgwin = (TextView) view.findViewById(R.id.txtQGWin);
        qglose = (TextView) view.findViewById(R.id.txtQGLose);
        qgtie = (TextView) view.findViewById(R.id.txtQGTie);
        qgpoint = (TextView) view.findViewById(R.id.txtQGPts);
        qgnrr = (TextView) view.findViewById(R.id.txtQGNRR);


        dref.child("Points").child("Quetta Gladiators").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    qgmatch.setText(dataSnapshot.child("Total").getValue().toString());
                    qgwin.setText(dataSnapshot.child("Win").getValue().toString());
                    qglose.setText(dataSnapshot.child("Lose").getValue().toString());
                    qgtie.setText(dataSnapshot.child("Draw").getValue().toString());
                    qgpoint.setText(dataSnapshot.child("Points").getValue().toString());
                    qgnrr.setText(dataSnapshot.child("NRR").getValue().toString());
                } catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dref.child("Points").child("Peshawar Zalmi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    pzmatch.setText(dataSnapshot.child("Total").getValue().toString());
                    pzwin.setText(dataSnapshot.child("Win").getValue().toString());
                    pzlose.setText(dataSnapshot.child("Lose").getValue().toString());
                    pztie.setText(dataSnapshot.child("Draw").getValue().toString());
                    pzpoint.setText(dataSnapshot.child("Points").getValue().toString());
                    pznrr.setText(dataSnapshot.child("NRR").getValue().toString());

                } catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dref.child("Points").child("Islamabad United").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    iumatch.setText(dataSnapshot.child("Total").getValue().toString());
                    iuwin.setText(dataSnapshot.child("Win").getValue().toString());
                    iulose.setText(dataSnapshot.child("Lose").getValue().toString());
                    iutie.setText(dataSnapshot.child("Draw").getValue().toString());
                    iupoint.setText(dataSnapshot.child("Points").getValue().toString());
                    iunrr.setText(dataSnapshot.child("NRR").getValue().toString());
                } catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dref.child("Points").child("Lahore Qalandars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    lqmatch.setText(dataSnapshot.child("Total").getValue().toString());
                    lqwin.setText(dataSnapshot.child("Win").getValue().toString());
                    lqlose.setText(dataSnapshot.child("Lose").getValue().toString());
                    lqtie.setText(dataSnapshot.child("Draw").getValue().toString());
                    lqpoint.setText(dataSnapshot.child("Points").getValue().toString());
                    lqnrr.setText(dataSnapshot.child("NRR").getValue().toString());
                } catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dref.child("Points").child("Karachi Kings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    kkmatch.setText(dataSnapshot.child("Total").getValue().toString());
                    kkwin.setText(dataSnapshot.child("Win").getValue().toString());
                    kklose.setText(dataSnapshot.child("Lose").getValue().toString());
                    kktie.setText(dataSnapshot.child("Draw").getValue().toString());
                    kkpoint.setText(dataSnapshot.child("Points").getValue().toString());
                    kknrr.setText(dataSnapshot.child("NRR").getValue().toString());
                } catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dref.child("Points").child("Multan Sultan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    msmatch.setText(dataSnapshot.child("Total").getValue().toString());
                    mswin.setText(dataSnapshot.child("Win").getValue().toString());
                    mslose.setText(dataSnapshot.child("Lose").getValue().toString());
                    mstie.setText(dataSnapshot.child("Draw").getValue().toString());
                    mspoint.setText(dataSnapshot.child("Points").getValue().toString());
                    msnrr.setText(dataSnapshot.child("NRR").getValue().toString());
                } catch (Exception e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
