package com.example.a1.projecttest.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a1.projecttest.R;

import java.util.List;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.vospitatel.VospitatelMainActivity;
import com.example.a1.projecttest.vospitatel.fragments.RaspisanieFragment;

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

import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

public class RaspisanieAdapter extends RecyclerView.Adapter<RaspisanieAdapter.RaspisanieHolder> {

    List<String> serviceName;
    List<Integer> colors;
    List<String> time;
    Fragment fragment;
    List<String> listNames;
    View view;
    Context context;
    int colorApNull;
    public RaspisanieAdapter (List<String> serviceName, List<Integer> colors, List<String> time, Fragment fragment, List<String> listNames, Context context, int colorApNull) {
        this.time = time;
        this.serviceName = serviceName;
        this.colors = colors;
        this.fragment = fragment;
        this.listNames = listNames;
        this.context = context;
        this.colorApNull = colorApNull;
    }

    @Override
    public RaspisanieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.raspisanie_item, parent, false);
        return new RaspisanieHolder(view);
    }

    @Override
    public void onBindViewHolder(final RaspisanieHolder holder, final int position) {
        final String pos = serviceName.get(position);
        holder.textView.setText(pos);
        holder.cardView.setTag(position);
        holder.cardView.setCardBackgroundColor(colors.get(holder.getAdapterPosition()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.recyclerView.getVisibility() != View.VISIBLE) {
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    holder.recyclerView.setAdapter(new RaspisanieGroupItemAdapter(listNames, colorApNull, holder.imageView));
                } else {
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });
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
        return serviceName.size();
    }


    public class RaspisanieHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        ImageView imageTime;
        ImageView imageView;
        RecyclerView recyclerView;

        public RaspisanieHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name_service);
            cardView = (CardView) itemView.findViewById(R.id.card_lay_raspisanie);
            imageTime = (ImageView) itemView.findViewById(R.id.image_time);
            imageView = (ImageView) itemView.findViewById(R.id.image_smile);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_smile);
        }

    }
}
