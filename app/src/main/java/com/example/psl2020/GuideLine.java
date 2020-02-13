package com.example.psl2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.jsoup.Connection;

public class GuideLine extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_guide_line);
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_guide_line;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.guideline;
    }
}
