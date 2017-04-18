package com.example.a1.projecttest.adapters;


import android.content.Context;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a1.projecttest.Entities.ChildStatusEntity;
import com.example.a1.projecttest.Entities.ChildStatusEntity_Table;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import static android.os.Build.VERSION.SDK;
import static android.os.Build.VERSION.SDK_INT;

public class VospitannikAdapter extends RecyclerView.Adapter<VospitannikAdapter.VospitannikHolder> {

    List<ChildStatusEntity> services;
    DateFormat dfDate_day_time= new SimpleDateFormat("HH:mm:ss");
    UserLoginSession userLoginSession;
    Context context;
    public VospitannikAdapter (List<ChildStatusEntity> services, Context context) {
        this.services = services;
        this.context = context;
        userLoginSession = new UserLoginSession(context);
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

        holder.timeTv.setText((services.get(holder.getAdapterPosition()).getTimeIn().substring(0, 5))
                + " - "
                + (services.get(holder.getAdapterPosition()).getTimeOut().substring(0, 5)));

       // holder.cardView.setCardBackgroundColor(services.get(holder.getAdapterPosition()).getColor());
        holder.false_tv.setText(services.get(holder.getAdapterPosition()).getComments());
        if (services.get(position).getVisible() == View.GONE) {
            holder.false_tv.setVisibility(View.GONE);
        } else holder.false_tv.setVisibility(View.VISIBLE);

        if (userLoginSession.getLogin().equals("Nataly")){
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        }

        Calendar now = Calendar.getInstance();
        Time date = Time.valueOf(dfDate_day_time.format(now.getTime()));

        date.setHours(date.getHours());

        if (Time.valueOf(services.get(position).getTimeOut()).after(date)){
            holder.imageTime.setImageResource(R.drawable.ic_clear_black_24dp);
        }

        if (Time.valueOf(services.get(position).getTimeOut()).before(date)) {
            holder.imageTime.setImageResource(R.drawable.ic_check_black_24dp);
        }

        if (Time.valueOf(services.get(position).getTimeIn()).before(date)){
            if (Time.valueOf(services.get(position).getTimeOut()).after(date)){
                holder.imageTime.setImageResource(R.drawable.ic_access_time_black_24dp);
            }
        }
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
        Button editButton;
        Button deleteButton;
        public VospitannikHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name_service);
            cardView = (CardView) itemView.findViewById(R.id.card_lay_vospit);
            timeTv = (TextView) itemView.findViewById(R.id.timeTVinCard);
            false_tv = (TextView) itemView.findViewById(R.id.false_tv);
            imageTime = (ImageView) itemView.findViewById(R.id.image_time);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.vospit_recycler);
            editButton = (Button) itemView.findViewById(R.id.edit_button_raspisanie);
            deleteButton = (Button) itemView.findViewById(R.id.delete_button_raspisanie);

        }

    }
}
