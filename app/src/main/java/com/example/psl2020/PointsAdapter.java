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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> {
    ArrayList<PointsAttr> scheduleAttrs;
    Activity activity;


    public PointsAdapter(ArrayList<PointsAttr> matchesAttributes, Activity activity) {
        this.scheduleAttrs = matchesAttributes;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pointdesign, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.name.setText(scheduleAttrs.get(position).getName());
        holder.point.setText( scheduleAttrs.get(position).getPoints().toString());
        Picasso.get().load(scheduleAttrs.get(position).getImage_url()).into(holder.userProfileImage);
    }

    @Override
    public int getItemCount() {
        return scheduleAttrs.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,point;
        ImageView userProfileImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name =(TextView) itemView.findViewById(R.id.txtNamee);
            point=(TextView) itemView.findViewById(R.id.txtPoints);
            userProfileImage =(ImageView)itemView.findViewById( R.id.imgProfile );

        }
    }
}
