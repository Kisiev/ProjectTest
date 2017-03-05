package com.example.a1.projecttest.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a1.projecttest.R;

import java.util.List;
import java.util.zip.Inflater;

public class VospitannikAdapter extends RecyclerView.Adapter<VospitannikAdapter.VospitannikHolder>{

    List<String> serviceName;

    public VospitannikAdapter (List<String> serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public VospitannikHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.vospit_item, parent, false);
        return new VospitannikHolder(view);
    }

    @Override
    public void onBindViewHolder(final VospitannikHolder holder, int position) {
        String pos = serviceName.get(position);
        holder.textView.setText(pos);
        holder.progressBar.setMax(5);
        holder.progressBar.setProgress(3);
        holder.progressPitanie.setMax(5);
        holder.progressPitanie.setProgress(3);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.progressPitanie.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceName.size();
    }

    class VospitannikHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ProgressBar progressBar;
        ProgressBar progressPitanie;

        public VospitannikHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name_service);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            progressPitanie = (ProgressBar) itemView.findViewById(R.id.progres_pitanie);
        }
    }
}
