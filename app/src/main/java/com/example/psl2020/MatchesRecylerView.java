package com.example.psl2020;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MatchesRecylerView extends RecyclerView.Adapter<MatchesRecylerView.ViewHolder> {
    ArrayList<ScheduleAttr> scheduleAttrs;
    Context activity;


    public MatchesRecylerView(ArrayList<ScheduleAttr> matchesAttributes, Context activity) {
        this.scheduleAttrs = matchesAttributes;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.matchesviewmain, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Integer id = scheduleAttrs.get( position ).getSid();
        String no;
        if (id == 0) {
            no = "1st";
        } else if (id == 1) {
            no = "2nd";
        } else if (id == 2) {
            no = "3rd";
        } else {
            id++;
            no = String.valueOf( id + "th" );
        }
        holder.liveMatches.setText( no + " Match" );
        String date = new SimpleDateFormat( "dd/MM/yyyy" ).format( Calendar.getInstance().getTime() );
        if (date.equals( scheduleAttrs.get( position ).getDate() ))
            holder.date.setText( "Today " );
        else
            holder.date.setText( scheduleAttrs.get( position ).getDate() + "  " );

        String status = scheduleAttrs.get( position ).getStatus();
        if (!status.equals( "Live" )) {
            holder.live.setVisibility( View.GONE );
        }
        if(status.equals( "Live" )){
            holder.date.setVisibility( View.GONE );
            holder.time.setVisibility( View.GONE );
        }
        holder.time.setText( scheduleAttrs.get( position ).getTime() );
        String teamOne = scheduleAttrs.get( position ).getTeamOne();
        String teamTwo = scheduleAttrs.get( position ).getTeamTwo();
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
        dref.child( "Teams" ).child( teamOne ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load( dataSnapshot.child( "ImgUrl" ).getValue().toString() ).into( holder.imgteam1 );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        dref.child( "Teams" ).child( teamTwo ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load( dataSnapshot.child( "ImgUrl" ).getValue().toString() ).into( holder.imgteam2 );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }

    @Override
    public int getItemCount() {
        return scheduleAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgteam1, imgteam2, live;
        TextView liveMatches, time, date;
        LinearLayout matchTime;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            liveMatches = (TextView) itemView.findViewById( R.id.txtmatches );
            date = (TextView) itemView.findViewById( R.id.txtDay );
            time = (TextView) itemView.findViewById( R.id.txtTime );
            imgteam1 = (ImageView) itemView.findViewById( R.id.team1 );
            imgteam2 = (ImageView) itemView.findViewById( R.id.team2 );
            live = (ImageView) itemView.findViewById( R.id.imgLive );
            matchTime = (LinearLayout) itemView.findViewById( R.id.matchTime );
        }
    }
}
