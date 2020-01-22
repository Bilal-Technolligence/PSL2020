package com.example.psl2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.winning.setText(scheduleAttrs.get(position).getWinner());
        String teamOne = scheduleAttrs.get( position ).getTeamOne();
        String teamTwo = scheduleAttrs.get( position ).getTeamTwo();
        if(teamOne.equals( "Lahore Qalandars" )){
            holder.img2a.setVisibility( View.GONE );
            holder.img3a.setVisibility( View.GONE );
            holder.img4a.setVisibility( View.GONE );
            holder.img5a.setVisibility( View.GONE );
            holder.img6a.setVisibility( View.GONE );

        }
        if(teamOne.equals( "Quetta Gladiators" )){
            holder.img1a.setVisibility( View.GONE );
            holder.img3a.setVisibility( View.GONE );
            holder.img4a.setVisibility( View.GONE );
            holder.img5a.setVisibility( View.GONE );
            holder.img6a.setVisibility( View.GONE );

        }
        if(teamOne.equals( "Islamabad United" )){
            holder.img1a.setVisibility( View.GONE );
            holder.img2a.setVisibility( View.GONE );
            holder.img4a.setVisibility( View.GONE );
            holder.img5a.setVisibility( View.GONE );
            holder.img6a.setVisibility( View.GONE );

        }
        if(teamOne.equals( "Peshawar Zalmi" )){
            holder.img1a.setVisibility( View.GONE );
            holder.img2a.setVisibility( View.GONE );
            holder.img3a.setVisibility( View.GONE );
            holder.img5a.setVisibility( View.GONE );
            holder.img6a.setVisibility( View.GONE );

        }
        if(teamOne.equals( "Multan Sultan" )){
            holder.img1a.setVisibility( View.GONE );
            holder.img2a.setVisibility( View.GONE );
            holder.img3a.setVisibility( View.GONE );
            holder.img4a.setVisibility( View.GONE );
            holder.img6a.setVisibility( View.GONE );

        }
        if(teamOne.equals( "Karachi Kings" )){
            holder.img1a.setVisibility( View.GONE );
            holder.img2a.setVisibility( View.GONE );
            holder.img3a.setVisibility( View.GONE );
            holder.img4a.setVisibility( View.GONE );
            holder.img5a.setVisibility( View.GONE );

        }
        if(teamTwo.equals( "Lahore Qalandars" )){
            holder.img2b.setVisibility( View.GONE );
            holder.img3b.setVisibility( View.GONE );
            holder.img4b.setVisibility( View.GONE );
            holder.img5b.setVisibility( View.GONE );
            holder.img6b.setVisibility( View.GONE );

        }
        if(teamTwo.equals( "Quetta Gladiators" )){
            holder.img1b.setVisibility( View.GONE );
            holder.img3b.setVisibility( View.GONE );
            holder.img4b.setVisibility( View.GONE );
            holder.img5b.setVisibility( View.GONE );
            holder.img6b.setVisibility( View.GONE );

        }
        if(teamTwo.equals( "Islamabad United" )){
            holder.img1b.setVisibility( View.GONE );
            holder.img2b.setVisibility( View.GONE );
            holder.img4b.setVisibility( View.GONE );
            holder.img5b.setVisibility( View.GONE );
            holder.img6b.setVisibility( View.GONE );

        }
        if(teamTwo.equals( "Peshawar Zalmi" )){
            holder.img1b.setVisibility( View.GONE );
            holder.img2b.setVisibility( View.GONE );
            holder.img3b.setVisibility( View.GONE );
            holder.img5b.setVisibility( View.GONE );
            holder.img6b.setVisibility( View.GONE );

        }
        if(teamTwo.equals( "Multan Sultan" )){
            holder.img1b.setVisibility( View.GONE );
            holder.img2b.setVisibility( View.GONE );
            holder.img3b.setVisibility( View.GONE );
            holder.img4b.setVisibility( View.GONE );
            holder.img6b.setVisibility( View.GONE );

        }
        if(teamTwo.equals( "Karachi Kings" )){
            holder.img1b.setVisibility( View.GONE );
            holder.img2b.setVisibility( View.GONE );
            holder.img3b.setVisibility( View.GONE );
            holder.img4b.setVisibility( View.GONE );
            holder.img5b.setVisibility( View.GONE );

        }
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


    }

    @Override
    public int getItemCount() {
        return scheduleAttrs.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img1a,img2a,img3a,img4a,img5a,img6a,img1b,img2b,img3b,img4b,img5b,img6b;
        TextView liveMatches,winning;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            liveMatches =(TextView) itemView.findViewById(R.id.txtMatches);
            winning=(TextView) itemView.findViewById(R.id.txtWinner);
            img1a = (ImageView) itemView.findViewById(R.id.team1a);
            img2a = (ImageView) itemView.findViewById(R.id.team2a);
            img3a = (ImageView) itemView.findViewById(R.id.team3a);
            img4a = (ImageView) itemView.findViewById(R.id.team4a);
            img5a = (ImageView) itemView.findViewById(R.id.team5a);
            img6a = (ImageView) itemView.findViewById(R.id.team6a);
            img1b = (ImageView) itemView.findViewById(R.id.team1b);
            img2b = (ImageView) itemView.findViewById(R.id.team2b);
            img3b = (ImageView) itemView.findViewById(R.id.team3b);
            img4b = (ImageView) itemView.findViewById(R.id.team4b);
            img5b = (ImageView) itemView.findViewById(R.id.team5b);
            img6b = (ImageView) itemView.findViewById(R.id.team6b);
        }
    }
}
