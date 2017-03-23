package com.example.a1.projecttest.adapters;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a1.projecttest.Entities.ChildStatusEntity;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import static android.os.Build.VERSION.SDK;

public class VospitannikAdapter extends RecyclerView.Adapter<VospitannikAdapter.VospitannikHolder> {

    List<ChildStatusEntity> services;

    public VospitannikAdapter (List<ChildStatusEntity> services ) {
        this.services = services;
    }

    @Override
    public VospitannikHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.vospit_item, parent, false);
        return new VospitannikHolder(view);
    }

    @Override
    public void onBindViewHolder(final VospitannikHolder holder, final int position) {
        holder.textView.setText(services.get(holder.getAdapterPosition()).getServiceName());
        holder.timeTv.setText(services.get(holder.getAdapterPosition()).getTimeIn() + " - " + services.get(holder.getAdapterPosition()).getTimeOut());
        holder.cardView.setCardBackgroundColor(services.get(holder.getAdapterPosition()).getColor());
        holder.false_tv.setText(services.get(holder.getAdapterPosition()).getComments());

        if (holder.getAdapterPosition() < 1) {
            holder.imageTime.setImageResource(R.drawable.ic_check_black_24dp);
            holder.imageView.setImageResource(R.mipmap.green_smile);
        }
        else if (holder.getAdapterPosition() == 1)
            holder.imageTime.setImageResource(R.drawable.ic_access_time_black_24dp);
        else if (holder.getAdapterPosition() > 1)  holder.imageTime.setImageResource(R.drawable.ic_clear_black_24dp);

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class VospitannikHolder extends RecyclerView.ViewHolder{
        TextView textView;
        RecyclerView recyclerView;
        TextView timeTv;
        CardView cardView;
        TextView false_tv;
        ImageView imageTime;
        ImageView imageView;

        public VospitannikHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name_service);
            cardView = (CardView) itemView.findViewById(R.id.card_lay_vospit);
            timeTv = (TextView) itemView.findViewById(R.id.timeTVinCard);
            false_tv = (TextView) itemView.findViewById(R.id.false_tv);
            imageTime = (ImageView) itemView.findViewById(R.id.image_time);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.vospit_recycler);

        }

    }
}
