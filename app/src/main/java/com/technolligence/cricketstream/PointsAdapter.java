package com.technolligence.cricketstream;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technolligence.cricketstream.R;
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
        try {
            holder.name.setText(scheduleAttrs.get(position).getName());
        }
        catch (Exception e){}
        try {
            holder.point.setText(scheduleAttrs.get(position).getPoints().toString());
        }
        catch (Exception e){}
        holder.serial.setText( String.valueOf(position+1));
        try {
            Picasso.get().load(scheduleAttrs.get(position).getImage_url()).into(holder.userProfileImage);
        }
        catch (Exception e){}
        }

    @Override
    public int getItemCount() {
        return scheduleAttrs.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,point , serial;
        ImageView userProfileImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name =(TextView) itemView.findViewById(R.id.txtNamee);
            serial =(TextView) itemView.findViewById(R.id.txtSerial);
            point=(TextView) itemView.findViewById(R.id.txtPoints);
            userProfileImage =(ImageView)itemView.findViewById( R.id.imgProfile );

        }
    }
}
