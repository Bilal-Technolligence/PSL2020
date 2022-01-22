package com.technolligence.cricketstream;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batsmansummary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.runs.setText(batsmanAttrs.get(position).getScore());

        String UserFullName = batsmanAttrs.get(position).getName();
        int firstSpace = UserFullName.indexOf(" "); // detect the first space character
        String firstName = UserFullName.substring(0, firstSpace);  // get everything upto the first space character
        String lastName = UserFullName.substring(firstSpace).trim();
        holder.name.setText(firstName.substring(0, 1) + " " + lastName);

        holder.sixs.setText(batsmanAttrs.get(position).getSixs());
        holder.sr.setText(batsmanAttrs.get(position).getRr());
        holder.four.setText(batsmanAttrs.get(position).getFours());
        holder.balls.setText(batsmanAttrs.get(position).getBalls());
    }

    @Override
    public int getItemCount() {
        return batsmanAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, runs, sixs, four, sr, balls;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txtBatsmanName);
            runs = (TextView) itemView.findViewById(R.id.txtBatsmanRuns);
            sixs = (TextView) itemView.findViewById(R.id.txtBatsmanSixes);
            sr = (TextView) itemView.findViewById(R.id.txtBatsmanSr);
            four = (TextView) itemView.findViewById(R.id.txtBatsmanFours);
            balls = (TextView) itemView.findViewById(R.id.txtBatsmanBalls);
        }
    }
}
