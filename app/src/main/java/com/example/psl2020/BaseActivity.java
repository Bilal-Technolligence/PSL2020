package com.example.psl2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    protected BottomNavigationView navigationView;
    protected DrawerLayout drawerLayout;
    ImageView imageView;
    protected ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);

        //drawer navigation
        drawerLayout = (DrawerLayout) findViewById(R.id.drawarLayout);
        //adding drawar button
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //drawer navbar item click
        NavigationView drawerNavigationView = (NavigationView) findViewById(R.id.drawerNavigationView);
        drawerNavigationView.setNavigationItemSelectedListener(this);

        //header click navbar
        View headerview = drawerNavigationView.getHeaderView(0);
        imageView = (ImageView) headerview.findViewById(R.id.profile_image);
    //profile img click
        imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View v){

            Snackbar.make(drawerLayout, "You must have account", Snackbar.LENGTH_LONG).show();
    }
    } );
}

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int itemId = item.getItemId();
//        if (itemId == R.id.nav_home) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        } else if (itemId == R.id.nav_matches) {
//            Intent intent=new Intent(this, LiveScoreActivity.class);
//          //  intent.putExtra( "id","Friends" );
//            startActivity(intent);
//            finish();
//        } else if (itemId == R.id.nav_fixture) {
//            Intent intent=new Intent(this, MainActivity.class);
//          //  intent.putExtra( "id","Family" );
//            startActivity(intent);
//            finish();
//        }
//        else if (itemId == R.id.nav_liveStream) {
//            startActivity(new Intent(this, LiveStreamingActivity.class));
//            finish();
//        }
//        else if (itemId == R.id.nav_more) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
//        else if(itemId==R.id.inviteFriends) {
//            Snackbar.make(drawerLayout, "Invite Friends", Snackbar.LENGTH_LONG).show();
//
//        }
//
//        return true;
//    }
    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

    ////////drawer navigation code///////////


    //drawer open close click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected( item )) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.nav_matches: {
//                    Snackbar.make(drawerLayout, "invite Friends", Snackbar.LENGTH_LONG).show();
//                    break;
//                }
//            }
//            return false;
//        }
//    };
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.inviteFriends: {
                            Snackbar.make(drawerLayout, "Invite Friends", Snackbar.LENGTH_LONG).show();
                break;
            }
            case R.id.nav_matches:{
                Intent intent=new Intent(this, LiveScoreActivity.class);
                startActivity(intent);
                finish();
//                Snackbar.make(drawerLayout, " Friends", Snackbar.LENGTH_LONG).show();

                break;
            }



        }
        return false;
    }


}

