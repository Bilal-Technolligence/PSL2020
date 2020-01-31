package com.example.psl2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<NewsDataClass> newsDataClassArray;
    ArrayList<String> str;
    Context context;

    public NewsAdapter(ArrayList<NewsDataClass> newsDataClassArray,Context context,ArrayList<String> str){
        this.str=str;
        this.newsDataClassArray=newsDataClassArray;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        NewsDataClass newsDataClass=newsDataClassArray.get(position);
//        holder.newsTitle.setText(newsDataClass.getNewsTitle());
//        holder.newsDetail.setText(newsDataClass.getNewsDetails());
//        holder.newsDatetime.setText(newsDataClass.getNewsDatetime());
//        Picasso.get().load(newsDataClass.getImgUrl()).into(holder.newsImage);
        holder.newsTitle.setText(str.get(position));

    }

    @Override
    public int getItemCount() {
        return str.size();//newsDataClassArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle,newsDetail,newsDatetime;
        ImageView newsImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle=(TextView) itemView.findViewById(R.id.newsTitle);
            newsDetail=(TextView) itemView.findViewById(R.id.newsDetail);
            newsDatetime=(TextView) itemView.findViewById(R.id.newsDatetime);
            newsImage=(ImageView) itemView.findViewById(R.id.newsImage);
        }
    }
}
