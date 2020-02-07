package com.example.psl2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.os.Build.ID;

public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.ViewHolder> {
    ArrayList<ScheduleAttr> scheduleAttrs;
    Context activity;


    public FixtureAdapter(ArrayList<ScheduleAttr> matchesAttributes, Context activity) {
        this.scheduleAttrs = matchesAttributes;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixtures, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.winning.setText(scheduleAttrs.get(position).getWinner());
        String teamOne = scheduleAttrs.get( position ).getTeamOne();
        String teamTwo = scheduleAttrs.get( position ).getTeamTwo();
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
        dref.child( "Teams" ).child( teamOne ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                Picasso.get().load( dataSnapshot.child( "ImgUrl" ).getValue().toString() ).into( holder.img1a );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        dref.child( "Teams" ).child( teamTwo ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                Picasso.get().load( dataSnapshot.child( "ImgUrl" ).getValue().toString() ).into( holder.img1b );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        String date = scheduleAttrs.get( position ).getDate();
        String day = date.substring( 0,2 );
        String m = date.substring( 3,5 );
        String no="";
        String month = "";
        if(m.equals( "02" )){
             month = "feb";
        }
        else if(m.equals( "03" )){
             month = "mar";
        }
        else if(m.equals( "04" )){
            month = "apr";
        }
        else if(m.equals( "01" )){
            month = "jan";
        }
        Integer id = scheduleAttrs.get( position ).getSid();
        if(id==0){
             no = "1st";
        }
        else if(id==1){
             no = "2nd";
        }
        else if(id==2 ){
             no = "3rd";
        }
        else {
            id++;
             no =String.valueOf( id+"th");
        }
        String city = scheduleAttrs.get( position ).getCity();
        String time = scheduleAttrs.get(position).getTime();
        String finalString = ( no+" Match at "+city+" on "+day+" "+ month +", "+time);
        holder.liveMatches.setText( finalString);
        try {
            holder.matchresult.setText(scheduleAttrs.get(position).getNote());
        }
        catch (Exception e){}
//        String winner = scheduleAttrs.get(position).getWinner();
//        final int secid = scheduleAttrs.get(position).getSid();
//        dref.child("FinishMatches").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
//                    String ch = dataSnapshot1.child(String.valueOf(secid)).getValue().toString();
//                    if(!ch.isEmpty()){
//                        holder.matchresult.setText(dataSnapshot1.child("note").getValue().toString());
//                    }
//
////                    int id = Integer.parseInt(dataSnapshot1.child("id").getValue().toString());
////                    if(id ==secid){
////                        holder.matchresult.setText(dataSnapshot1.child("note").getValue().toString());
////                    }
//                    else{
//                        holder.matchresult.setText("  ");
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return scheduleAttrs.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img1a,img1b;
        TextView liveMatches,winning, matchresult;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            liveMatches =(TextView) itemView.findViewById(R.id.txtMatches);
            winning=(TextView) itemView.findViewById(R.id.txtWinner);
            img1a = (ImageView) itemView.findViewById(R.id.team1a);
            img1b = (ImageView) itemView.findViewById(R.id.team1b);
            matchresult = (TextView) itemView.findViewById(R.id.txtmatchresult);
        }
    }
}
