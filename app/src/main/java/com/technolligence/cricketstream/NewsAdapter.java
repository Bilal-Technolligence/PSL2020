package com.technolligence.cricketstream;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<NewsDataClass> newsDataClassArray;
    Activity activity;

    public NewsAdapter(ArrayList<NewsDataClass> newsDataClassArray, Activity activity) {
        this.newsDataClassArray = newsDataClassArray;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewsDataClass newsDataClass = newsDataClassArray.get(position);
        holder.newsTitle.setText(newsDataClass.getNewsTitle());
        //holder.newsDetail.setText(newsDataClass.getNewsDetails());
        holder.newsDatetime.setText(newsDataClass.getNewsDatetime());
        Picasso.get().load(newsDataClass.getImgUrl()).into(holder.newsImage);
        holder.newsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NewsDetail.class);
                intent.putExtra("title", newsDataClass.getNewsTitle());
                intent.putExtra("date", newsDataClass.getNewsDatetime());
                intent.putExtra("link", newsDataClass.getNewsDetails());
                intent.putExtra("imgUrl", newsDataClass.getImgUrl());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsDataClassArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle, newsDetail, newsDatetime;
        ImageView newsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = (TextView) itemView.findViewById(R.id.newsTitle);
            //newsDetail=(TextView) itemView.findViewById(R.id.newsDetail);
            newsDatetime = (TextView) itemView.findViewById(R.id.newsDatetime);
            newsImage = (ImageView) itemView.findViewById(R.id.newsImage);
        }
    }
}
