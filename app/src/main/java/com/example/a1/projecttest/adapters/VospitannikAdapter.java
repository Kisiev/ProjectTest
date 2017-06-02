package com.example.a1.projecttest.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.rest.Models.GetScheduleListModel;
import com.example.a1.projecttest.rest.Models.GetStatusKidModel;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static ru.yandex.core.CoreApplication.getActivity;

public class VospitannikAdapter extends XRecyclerView.Adapter<VospitannikAdapter.VospitannikHolder> {

    List<GetScheduleListModel> services;
    DateFormat dfDate_day_time= new SimpleDateFormat("HH:mm:ss");
    UserLoginSession userLoginSession;
    Typeface typeface;
    Typeface typefaceItalic;
    List<GetStatusKidModel> getStatusKidModels;
    Context context;
    public VospitannikAdapter (List<GetScheduleListModel> services, List<GetStatusKidModel> getStatusKidModels, Context context) {
        this.services = services;
        this.context = context;
        userLoginSession = new UserLoginSession(context);
        this.getStatusKidModels = getStatusKidModels;
    }

    @Override
    public VospitannikHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.vospit_item, parent, false);
        return new VospitannikHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final VospitannikHolder holder, final int position) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");
        typefaceItalic = Typeface.createFromAsset(context.getAssets(), "font/OpenSans-Italic.ttf");
        holder.textView.setText(services.get(position).getName());
        holder.timeTv.setText((services.get(position).getTimeFrom().substring(0, 5))
                + " - "
                + (services.get(position).getTimeTo().substring(0, 5)));

       // holder.cardView.setCardBackgroundColor(services.get(holder.getAdapterPosition()).getColor());
       // holder.false_tv.setText(services.get(holder.getAdapterPosition()).getComments());
/*
        if (services.get(position).getVisible() == View.GONE) {
            holder.false_tv.setVisibility(View.GONE);
        } else holder.false_tv.setVisibility(View.VISIBLE);
*/
        holder.textView.setTypeface(typeface);
        holder.timeTv.setTypeface(typeface);
        holder.editButton.setTypeface(typeface);
        holder.deleteButton.setTypeface(typeface);
        holder.false_tv.setTypeface(typefaceItalic);
        if (userLoginSession.getRoleId() == 2){
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        }

        if (userLoginSession.getRoleId() == 1){
            if (getStatusKidModels != null)
            for (GetStatusKidModel i : getStatusKidModels){
                if (i.getScheduleId().equals(services.get(position).getId())){
                    switch (i.getStatusId()){
                        case "1":
                            holder.imageView.setImageResource(R.drawable.ic_sentiment_dissatisfied_red_24dp);
                            if (i.getComment() != null)
                                if (!i.getComment().equals(""))
                                    holder.false_tv.setText("Комментарий: " + i.getComment());
                            break;
                        case "2":
                            holder.imageView.setImageResource(R.drawable.ic_sentiment_satisfied_yellow_24dp);
                            if (i.getComment() != null)
                                if (!i.getComment().equals(""))
                                    holder.false_tv.setText("Комментарий: " + i.getComment());
                            break;
                        case "3":
                            holder.imageView.setImageResource(R.drawable.ic_sentiment_very_satisfied_green_24dp);
                            if (i.getComment() != null)
                                if (!i.getComment().equals(""))
                                    holder.false_tv.setText("Комментарий: " + i.getComment());
                            break;
                    }

                }
            }


        }

        Calendar now = Calendar.getInstance();
        Time date = Time.valueOf(dfDate_day_time.format(now.getTime()));

        date.setHours(date.getHours());

        if (Time.valueOf(services.get(position).getTimeTo()).after(date)){
            holder.imageTime.setImageResource(R.drawable.ic_clear_black_24dp);
        }

        if (Time.valueOf(services.get(position).getTimeTo()).before(date)) {
            holder.imageTime.setImageResource(R.drawable.ic_check_black_24dp);
        }

        if (Time.valueOf(services.get(position).getTimeFrom()).before(date)){
            if (Time.valueOf(services.get(position).getTimeTo()).after(date)){
                holder.imageTime.setImageResource(R.drawable.ic_access_time_black_24dp);
            }
        }
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class VospitannikHolder extends XRecyclerView.ViewHolder{
        TextView textView;
        RecyclerView recyclerView;
        TextView timeTv;
        CardView cardView;
        TextView false_tv;
        ImageView imageTime;
        ImageView imageView;
        Button editButton;
        Button deleteButton;
        public VospitannikHolder(View itemView, Context context) {
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

            Typeface typefaceBold = Typeface.createFromAsset(context.getAssets(), "font/OpenSans-Bold.ttf");
            Typeface typefaceItalic = Typeface.createFromAsset(context.getAssets(), "font/OpenSans-Italic.ttf");
            textView.setTypeface(typefaceBold);
            timeTv.setTypeface(typefaceItalic);
            false_tv.setTypeface(typefaceItalic);
            editButton.setTypeface(typefaceBold);
            deleteButton.setTypeface(typefaceBold);
        }

    }
}
