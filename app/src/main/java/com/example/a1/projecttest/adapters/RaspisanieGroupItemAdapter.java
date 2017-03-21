package com.example.a1.projecttest.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a1.projecttest.R;

import java.util.List;

/**
 * Created by 1 on 21.03.2017.
 */

public class RaspisanieGroupItemAdapter extends RecyclerView.Adapter<RaspisanieGroupItemAdapter.RaspisanieGroupItemHolder>{

    List<String> listNames;
    int color;
    public RaspisanieGroupItemAdapter (List<String> listNames, int color) {
        this.listNames = listNames;
        this.color = color;
    }

    @Override
    public RaspisanieGroupItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raspisanie_group_item, parent, false);
        return new RaspisanieGroupItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RaspisanieGroupItemHolder holder, int position) {
        holder.name.setText(listNames.get(position));
        holder.name.setTextColor(Color.WHITE);
        holder.cardView.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return listNames.size();
    }

    class RaspisanieGroupItemHolder extends RecyclerView.ViewHolder{
        TextView name;
        CardView cardView;
        public RaspisanieGroupItemHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_child_raspisanie);
            cardView = (CardView) itemView.findViewById(R.id.card_group_raspisanie);
        }
    }
}
