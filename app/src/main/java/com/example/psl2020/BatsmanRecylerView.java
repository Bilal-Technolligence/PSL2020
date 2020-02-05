package com.example.psl2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class BatsmanRecylerView extends RecyclerView.Adapter<BatsmanRecylerView.ViewHolder> {
    ArrayList<BatsmanAttr> batsmanAttrs;
    Context activity;


    public BatsmanRecylerView(ArrayList<BatsmanAttr> batsmanAttrs, Context activity) {
        this.batsmanAttrs = batsmanAttrs;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.batsmansummary, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.runs.setText( batsmanAttrs.get( position ).getScore() );
        holder.name.setText( batsmanAttrs.get( position ).getName() );
        holder.sixs.setText( batsmanAttrs.get( position ).getSixs() );
        holder.sr.setText( batsmanAttrs.get( position ).getRr() );
        holder.four.setText( batsmanAttrs.get( position ).getFours() );
        holder.balls.setText( batsmanAttrs.get( position ).getBalls() );
    }

    @Override
    public int getItemCount() {
        return batsmanAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, runs, sixs,four,sr,balls;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            name = (TextView) itemView.findViewById( R.id.txtBatsmanName );
            runs = (TextView) itemView.findViewById( R.id.txtBatsmanRuns );
            sixs = (TextView) itemView.findViewById( R.id.txtBatsmanSixes );
            sr = (TextView) itemView.findViewById( R.id.txtBatsmanSr );
            four = (TextView) itemView.findViewById( R.id.txtBatsmanFours );
            balls = (TextView) itemView.findViewById( R.id.txtBatsmanBalls );
        }
    }
}
