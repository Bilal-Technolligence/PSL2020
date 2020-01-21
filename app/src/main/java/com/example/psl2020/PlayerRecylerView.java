package com.example.psl2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


    public class PlayerRecylerView extends RecyclerView.Adapter<PlayerRecylerView.ViewHolder> {
        ArrayList<PlayerAttributes> playerAttributes;
        Context activity;


        public PlayerRecylerView(ArrayList<PlayerAttributes> playerAttributes, Context activity) {
            this.playerAttributes = playerAttributes;
            this.activity = activity;
        }

//
//        @NonNull
//        @Override
//        public com.example.psl2020.MatchesRecylerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matchesviewmain, parent, false);
//            return new com.example.psl2020.MatchesRecylerView.ViewHolder(view);
//        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teamsplayer, parent, false);
                       return new PlayerRecylerView.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.playerName.setText(playerAttributes.get(position).getName());
            holder.playerCategory.setText(playerAttributes.get(position).getType());
            Picasso.get().load(playerAttributes.get(position).getImage_url()).into(holder.playerImage);

           // holder.time.setText(scheduleAttrs.get(position).getTime());
        }

        @Override
        public int getItemCount() {
            return playerAttributes.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView playerImage;
            TextView playerName,playerCategory;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                playerName =(TextView) itemView.findViewById(R.id.txtPName);
                playerCategory=(TextView) itemView.findViewById(R.id.txtPCategory);
                playerImage = (ImageView) itemView.findViewById(R.id.playerImage);
            }
        }
    }

