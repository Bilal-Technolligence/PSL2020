package com.example.psl2020;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matchesviewmain, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.liveMatches.setText(scheduleAttrs.get(position).getStatus());
        holder.date.setText(scheduleAttrs.get(position).getDate());
        holder.time.setText(scheduleAttrs.get(position).getTime());


    }

    @Override
    public int getItemCount() {
        return scheduleAttrs.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgteam1,imgteam2;
        TextView liveMatches,time,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            liveMatches =(TextView) itemView.findViewById(R.id.txtmatches);
            date=(TextView) itemView.findViewById(R.id.txtDate);
            time=(TextView) itemView.findViewById(R.id.txtTime);
            imgteam1 = (ImageView) itemView.findViewById(R.id.team1);
            imgteam2 = (ImageView) itemView.findViewById(R.id.team2);
        }
    }
}
