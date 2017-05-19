package com.example.a1.projecttest.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;
import com.example.a1.projecttest.rest.Models.GetStatusKidModel;

import java.util.List;

import static ru.yandex.core.CoreApplication.getActivity;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    List<String> list;
    Context context;
    Typeface typeface;
    List<GetStatusKidModel> getStatusKidModels;
    List<GetAllKidsModel> getAllKidsModels;
    public FeedAdapter (Context context, List<GetStatusKidModel> getStatusKidModels, List<GetAllKidsModel> getAllKidsModels) {
        this.getStatusKidModels = getStatusKidModels;
        this.getAllKidsModels = getAllKidsModels;
        this.context = context;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_fragment_item, parent, false);
        return new FeedHolder(view, context);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");

        for (int j = 0; j < getAllKidsModels.size(); j ++) {
            if (getStatusKidModels.get(position).getUserId().equals(getAllKidsModels.get(j).getId())) {
                holder.nameChildTV.setText(getAllKidsModels.get(j).getName());
                holder.serviceNameTV.setText(getStatusKidModels.get(position).getScheduleName() + " " + getStatusKidModels.get(position).getName());
                holder.nameChildTV.setTypeface(typeface);
                holder.serviceNameTV.setTypeface(typeface);
                switch (getStatusKidModels.get(position).getStatusId()){
                    case "1":
                        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorRedOpacity));
                        break;
                    case "2":
                        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorYellowOpacity));
                        break;
                    case "3":
                        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorGreenOpacity));
                        break;
                }

            }
        }


    }

    @Override
    public int getItemCount() {
        return getStatusKidModels.size();
    }

    class FeedHolder extends RecyclerView.ViewHolder {
        TextView nameChildTV;
        TextView serviceNameTV;
        CardView cardView;
        public FeedHolder(View itemView, Context context) {
            super(itemView);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");
            nameChildTV = (TextView) itemView.findViewById(R.id.name_childTV);
            serviceNameTV = (TextView) itemView.findViewById(R.id.service_nameTV);
            cardView = (CardView) itemView.findViewById(R.id.card_feed_parent);
            nameChildTV.setTypeface(typeface);
            serviceNameTV.setTypeface(typeface);
        }
    }
}
