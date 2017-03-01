package com.example.a1.projecttest.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.fragments.VospitannikFragment;

import java.util.List;

public class CustomDrawerMenuAdapter extends RecyclerView.Adapter<CustomDrawerMenuAdapter.CustomDrawerMenuHolder>{

    List<String> names;


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
        boolean b = Boolean.parseBoolean(null);
        MainActivity mainActivity = new MainActivity(b);
        mainActivity.clickListener(holder, position);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class CustomDrawerMenuHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView role;
        public CardView cardView;

        public CustomDrawerMenuHolder(View item){
            super(item);
            name = (TextView) item.findViewById(R.id.name_child);
            role = (TextView) item.findViewById(R.id.role_child);
            cardView = (CardView) item.findViewById(R.id.list_item);
        }

    }
}
