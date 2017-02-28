package com.example.a1.projecttest.adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a1.projecttest.R;

import java.util.List;

public class CustomDrawerMenuAdapter extends RecyclerView.Adapter<CustomDrawerMenuAdapter.CustomDrawerMenuHolder>{

    List<String> names;

    private void clickListener(final CustomDrawerMenuHolder holder, final int pos){
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("43345345345345", "Нажата " + names.get(pos));
            }
        });
    }

    public CustomDrawerMenuAdapter(List<String> names){
        this.names = names;
    }

    @Override
    public CustomDrawerMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_item, parent, false);
        return new CustomDrawerMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomDrawerMenuHolder holder, int position) {
        String pos = names.get(position);
        holder.name.setText(pos);
        holder.role.setText("Сын");
        clickListener(holder, position);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class CustomDrawerMenuHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView role;
        CardView cardView;
        public CustomDrawerMenuHolder(View item){
            super(item);
            name = (TextView) item.findViewById(R.id.name_child);
            role = (TextView) item.findViewById(R.id.role_child);
            cardView = (CardView) item.findViewById(R.id.list_item);
        }

    }
}
