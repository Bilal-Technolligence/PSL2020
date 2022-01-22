package com.technolligence.cricketstream;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NewsDetail extends AppCompatActivity {
    TextView title, detail, dateTime;
    ImageView newsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        dateTime = (TextView) findViewById(R.id.newsDatetime);
        newsImage = (ImageView) findViewById(R.id.newsImage);
        title = (TextView) findViewById(R.id.newsTitle);
        Intent intent = getIntent();
        String newsTitle = intent.getStringExtra("title");
        String newsDate = intent.getStringExtra("date");
        String imgUrl = intent.getStringExtra("imgUrl");
        Picasso.get().load(imgUrl).into(newsImage);
        title.setText(newsTitle);
        dateTime.setText(newsDate);
        new ScrapeNews().execute();
    }

    public class ScrapeNews extends AsyncTask<Void, Void, Void> {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        ArrayList<String> newsDetail = new ArrayList<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            detail = (TextView) findViewById(R.id.newsDetail);


        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            // Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Intent intent = getIntent();
                String url = intent.getStringExtra("link");
                Document document = Jsoup.connect(url).get();
                Elements element = document.select("div.col-md-12");
                int size = element.select("div.col-md-12").eq(1).select("p").size();
                for (int i = 0; i < size; i++) {
                    if (!element.select("div.col-md-12").eq(1).select("p").eq(i).text().equals("")) {
                        String value = element.select("div.col-md-12").eq(1).select("p").eq(i).text();
                        newsDetail.add(value);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_LONG).show();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            if (newsDetail.size() != 0) {
                String text = "";
                for (int j = 0; j < newsDetail.size(); j++) {
                    if (j == 0) {
                        text = text + newsDetail.get(j);
                    } else {
                        text = text + "\n \n" + newsDetail.get(j);
                    }

                    detail.setText(text);
                }
            }
        }
    }
}
