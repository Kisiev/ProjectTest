package com.example.a1.projecttest.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1.projecttest.R;

import java.util.List;

import static ru.yandex.core.CoreApplication.getActivity;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    List<String> list;
    Context context;
    Typeface typeface;
    public FeedAdapter (Context context, List<String> list) {
        this.list = list;
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
        if (position == 0) {
            holder.newsNameTV.setText("Ребенок");
            holder.timeTV.setText("20 минут назад");
            holder.dateTV.setText("13.12.2017");
            holder.nameChildTV.setText("Иванов В.В");
            holder.serviceNameTV.setText("Завтрак в столовой");
        } else if (position == 1) {
            holder.newsNameTV.setText("Болеющие дети");
            holder.timeTV.setText("25 минут назад");
            holder.dateTV.setText("13.12.2017");
            holder.nameChildTV.setText("В группе, вероятно, болеют:");
            holder.serviceNameTV.setText("5 человек");
        } else if (position == 2) {
            holder.newsNameTV.setText("Ребенок");
            holder.timeTV.setText("30 минут назад");
            holder.dateTV.setText("13.12.2017");
            holder.nameChildTV.setText("Иванов А.В");
            holder.serviceNameTV.setText("Прибыл к точке: Музей искусств");
        }
        holder.newsNameTV.setTypeface(typeface);
        holder.timeTV.setTypeface(typeface);
        holder.dateTV.setTypeface(typeface);
        holder.nameChildTV.setTypeface(typeface);
        holder.serviceNameTV.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class FeedHolder extends RecyclerView.ViewHolder {
        TextView newsNameTV;
        TextView timeTV;
        TextView dateTV;
        TextView nameChildTV;
        TextView serviceNameTV;
        public FeedHolder(View itemView, Context context) {
            super(itemView);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");
            newsNameTV     = (TextView) itemView.findViewById(R.id.news_nameTV);
            timeTV = (TextView) itemView.findViewById(R.id.time_TV);
            dateTV = (TextView) itemView.findViewById(R.id.date_TV);
            nameChildTV = (TextView) itemView.findViewById(R.id.name_childTV);
            serviceNameTV = (TextView) itemView.findViewById(R.id.service_nameTV);
            newsNameTV.setTypeface(typeface);
            timeTV.setTypeface(typeface);
            dateTV.setTypeface(typeface);
            nameChildTV.setTypeface(typeface);
            serviceNameTV.setTypeface(typeface);
        }
    }
}
