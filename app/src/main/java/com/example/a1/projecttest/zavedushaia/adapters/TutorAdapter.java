package com.example.a1.projecttest.zavedushaia.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetAllTutors;

import java.util.List;


public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorHolder> {
    List<GetAllTutors> getAllTutorses;
    Context context;
    Typeface typeface;
    public TutorAdapter (List<GetAllTutors> getAllTutorses, Context context){
        this.getAllTutorses = getAllTutorses;
        this.context = context;
    }
    @Override
    public TutorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.tutor_zav_fragment_item, parent, false);
        return new TutorHolder(view);
    }

    @Override
    public void onBindViewHolder(TutorHolder holder, int position) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");
        holder.name.setText("ФИО: " + getAllTutorses.get(position).getSurname()
        + " " + getAllTutorses.get(position).getName().substring(0, 1)
        + ". "+ getAllTutorses.get(position).getPatronymic().substring(0, 1) + ".");
        holder.name.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return getAllTutorses.size();
    }

    class TutorHolder extends RecyclerView.ViewHolder{
        TextView name;
        public TutorHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tutor_name_zav_text_view);
        }
    }
}
