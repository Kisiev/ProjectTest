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
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;

import java.util.List;



public class RegisterKidsAdapter extends RecyclerView.Adapter<RegisterKidsAdapter.RegisterKidsHolder>{
    List<GetAllKidsModel> getAllKidsModels;
    Context context;
    Typeface typeface;
    public RegisterKidsAdapter (List<GetAllKidsModel> getAllKidsModels, Context context){
        this.getAllKidsModels = getAllKidsModels;
        this.context = context;
    }
    @Override
    public RegisterKidsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kids_fragment_zav_item, parent, false);
        return new RegisterKidsHolder(view);
    }

    @Override
    public void onBindViewHolder(RegisterKidsHolder holder, int position) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
        holder.nameChild.setText("ФИО: " + getAllKidsModels.get(position).getSurname()
        +" " + getAllKidsModels.get(position).getName().substring(0, 1)
        + ". "+ getAllKidsModels.get(position).getPatronymic().substring(0, 1) + ".");
        holder.nameChild.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return getAllKidsModels.size();
    }

    class RegisterKidsHolder extends RecyclerView.ViewHolder{
        TextView nameChild;
        public RegisterKidsHolder(View itemView) {
            super(itemView);
            nameChild = (TextView) itemView.findViewById(R.id.child_name_zav_text_view);
        }
    }
}
