package com.example.a1.projecttest.zavedushaia.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetAllTutors;


public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorHolder> {

    @Override
    public TutorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TutorHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TutorHolder extends RecyclerView.ViewHolder{
        TextView name;
        public TutorHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tutor_name_zav_text_view);
        }
    }
}
