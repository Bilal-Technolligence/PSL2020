package com.example.psl2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import com.google.api.Context;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamsActivity extends BaseActivity {
    CardView peshawerZalmi,islamabadUnited,quettaGladiator,karachiKings,lahoreQalanders,multanSultan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
      //  setContentView( R.layout.activity_teams );

        peshawerZalmi=findViewById( R.id.cardPeshawerZalmi );
        islamabadUnited=findViewById( R.id.cardIslamabad );

        quettaGladiator=findViewById( R.id.cardQuetta );

        karachiKings=findViewById( R.id.cardKarachi );
        lahoreQalanders=findViewById( R.id.cardLahoreQalandar );

        multanSultan=findViewById( R.id.cardMultanSultan );

        peshawerZalmi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Peshawar Zalmi" );
                startActivity(intent);
            }
        } );
        lahoreQalanders.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Lahore Qalandars" );
                startActivity(intent);
            }
        } );
        karachiKings.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Karachi Kings" );
                startActivity(intent);
            }
        } );
        islamabadUnited.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Islamabad United" );
                startActivity(intent);
            }
        } );
        multanSultan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Multan Sultan" );
                startActivity(intent);
            }
        } );
        quettaGladiator.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamsActivity.this,PlayersActivity.class );
                intent.putExtra( "teamname", "Quetta Gladiators" );
                startActivity(intent);
            }
        } );




    }

    @Override
    int getContentViewId() {
        return R.layout.activity_teams;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_team;
    }
}
