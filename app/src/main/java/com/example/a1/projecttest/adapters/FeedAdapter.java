package com.example.a1.projecttest.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1.projecttest.R;

import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    List<String> list;

    public FeedAdapter (List<String> list) {
        this.list = list;
    }

    @Override
    public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_fragment_item, parent, false);
        return new FeedHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedHolder holder, int position) {
        holder.newsNameTV.setText("Ребенок");
        holder.timeTV.setText("20 минут назад");
        holder.dateTV.setText("13.12.2017");
        holder.nameChildTV.setText("Иванов В.П");
        holder.serviceNameTV.setText("Завтрак в столовой");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class FeedHolder extends RecyclerView.ViewHolder {
        TextView newsNameTV;
        TextView timeTV;
        TextView dateTV;
        TextView nameChildTV;
        TextView serviceNameTV;
        public FeedHolder(View itemView) {
            super(itemView);
            newsNameTV     = (TextView) itemView.findViewById(R.id.news_nameTV);
            timeTV = (TextView) itemView.findViewById(R.id.time_TV);
            dateTV = (TextView) itemView.findViewById(R.id.date_TV);
            nameChildTV = (TextView) itemView.findViewById(R.id.name_childTV);
            serviceNameTV = (TextView) itemView.findViewById(R.id.service_nameTV);
        }
    }
}
