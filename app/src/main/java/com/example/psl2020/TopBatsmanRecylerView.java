package com.example.psl2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TopBatsmanRecylerView extends RecyclerView.Adapter<TopBatsmanRecylerView.ViewHolder> {
    ArrayList<TopBatAttr> batsmanAttrs;
    Context activity;


    public TopBatsmanRecylerView(ArrayList<TopBatAttr> batsmanAttrs, Context activity) {
        this.batsmanAttrs = batsmanAttrs;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.batdesign, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.runs.setText( String.valueOf(batsmanAttrs.get( position ).getScore()) );

        String UserFullName =  batsmanAttrs.get( position ).getName();
        int firstSpace = UserFullName.indexOf(" "); // detect the first space character
        String firstName = UserFullName.substring(0, firstSpace);  // get everything upto the first space character
        String lastName = UserFullName.substring(firstSpace).trim();
        holder.name.setText( UserFullName);
        if(batsmanAttrs.get( position ).getTeam().equals("Peshawar Zalmi")){
            holder.team.setText( "PZ");
        }
        else if(batsmanAttrs.get( position ).getTeam().equals("Multan Sultan")){
            holder.team.setText( "MS");
        }
        else if(batsmanAttrs.get( position ).getTeam().equals("Quetta Gladiators")){
            holder.team.setText( "QG");
        }
        else if(batsmanAttrs.get( position ).getTeam().equals("Islamabad United")){
            holder.team.setText( "IU");
        }
        else if(batsmanAttrs.get( position ).getTeam().equals("Karachi Kings")){
            holder.team.setText( "KK");
        }
        else if(batsmanAttrs.get( position ).getTeam().equals("Lahore Qalandars")){
            holder.team.setText( "LQ");
        }

        holder.serial.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return batsmanAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, runs, team,serial;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            name = (TextView) itemView.findViewById( R.id.txtName );
            runs = (TextView) itemView.findViewById( R.id.txtScore );
            team = (TextView) itemView.findViewById( R.id.txtTeam );
            serial = (TextView) itemView.findViewById( R.id.txtSerial );

        }
    }
}
