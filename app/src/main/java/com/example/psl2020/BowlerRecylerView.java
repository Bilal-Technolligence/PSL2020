package com.example.psl2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BowlerRecylerView extends RecyclerView.Adapter<BowlerRecylerView.ViewHolder> {
    ArrayList<BowlerAttr> bowlerAttrs;
    Context activity;


    public BowlerRecylerView(ArrayList<BowlerAttr> bowlerAttrs, Context activity) {
        this.bowlerAttrs = bowlerAttrs;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.bowlersummary, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.runs.setText( bowlerAttrs.get( position ).getScore() );
        String UserFullName =  bowlerAttrs.get( position ).getName();
        int firstSpace = UserFullName.indexOf(" "); // detect the first space character
        String firstName = UserFullName.substring(0, firstSpace);  // get everything upto the first space character
        String lastName = UserFullName.substring(firstSpace).trim();
        holder.name.setText( firstName.substring(0, 1) +" "+lastName );
        holder.overs.setText( bowlerAttrs.get( position ).getOvers() );
        holder.medians.setText( bowlerAttrs.get( position ).getMedians() );
        holder.wickets.setText( bowlerAttrs.get( position ).getWickets() );
        holder.eco.setText( bowlerAttrs.get( position ).getEcon() );
    }

    @Override
    public int getItemCount() {
        return bowlerAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, runs, overs,medians,wickets,eco;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            name = (TextView) itemView.findViewById( R.id.txtBowlerName );
            runs = (TextView) itemView.findViewById( R.id.txtBowlerRuns );
            overs = (TextView) itemView.findViewById( R.id.txtBowlerOvers );
            medians = (TextView) itemView.findViewById( R.id.txtBowlerMiddens );
            wickets = (TextView) itemView.findViewById( R.id.txtBowlerWickts );
            eco = (TextView) itemView.findViewById( R.id.txtBowlerEcon );
        }
    }
}
