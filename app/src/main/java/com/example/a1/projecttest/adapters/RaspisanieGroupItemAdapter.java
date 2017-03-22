package com.example.a1.projecttest.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.a1.projecttest.R;

import java.util.List;

/**
 * Created by 1 on 21.03.2017.
 */

public class RaspisanieGroupItemAdapter extends RecyclerView.Adapter<RaspisanieGroupItemAdapter.RaspisanieGroupItemHolder>{

    List<String> listNames;
    ImageView imageView;
    int color;
    public RaspisanieGroupItemAdapter (List<String> listNames, int color, ImageView imageView) {
        this.listNames = listNames;
        this.color = color;
        this.imageView = imageView;
    }

    @Override
    public RaspisanieGroupItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raspisanie_group_item, parent, false);
        return new RaspisanieGroupItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final RaspisanieGroupItemHolder holder, int position) {
        holder.name.setText(listNames.get(position));
        holder.name.setTextColor(Color.WHITE);
        holder.cardView.setBackgroundColor(color);
        onClick(holder);

    }

    private void onClick(RaspisanieGroupItemHolder holder) {
        holder.red_smile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.mipmap.red_smile);
            }
        });

        holder.yellow_smile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.mipmap.yellow_smile);
            }
        });

        holder.green_smile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.mipmap.green_smile);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listNames.size();
    }

    class RaspisanieGroupItemHolder extends RecyclerView.ViewHolder{
        TextView name;
        CardView cardView;
        ImageView green_smile;
        ImageView yellow_smile;
        ImageView red_smile;
        public RaspisanieGroupItemHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_child_raspisanie);
            cardView = (CardView) itemView.findViewById(R.id.card_group_raspisanie);
            red_smile = (ImageView) itemView.findViewById(R.id.red_smile_IV);
            yellow_smile = (ImageView) itemView.findViewById(R.id.yellow_smile_IV);
            green_smile = (ImageView) itemView.findViewById(R.id.green_smile_IV);
        }
    }
}
