package com.example.a1.projecttest.adapters;


import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.fragments.VospitannikFragment;

import java.util.ArrayList;
import java.util.List;

public class CustomDrawerMenuAdapter extends RecyclerView.Adapter<CustomDrawerMenuAdapter.CustomDrawerMenuHolder>{

    List<String> names;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    public CustomDrawerMenuAdapter(List<String> names){
        this.names = names;
    }

    @Override
    public CustomDrawerMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_item, parent, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        return new CustomDrawerMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomDrawerMenuHolder holder, int position) {
        String pos = names.get(position);
        holder.name.setText(pos);
        holder.role.setText("Сын");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<String>();
                list.add("Коля");
                list.add(("Саня"));
                list.add("Геор");
                list.add(("Миша"));
                MainActivity mainActivity1 = new MainActivity();
                FragmentManager fragmentManager = mainActivity1.getSupportFragmentManager();
                VospitannikFragment fragment = new VospitannikFragment();

                MainActivity mainActivity = new MainActivity(drawerLayout, recyclerView, list, fragmentManager, fragmentManager.beginTransaction());
                mainActivity.replaceFragment(fragment);
            }
        });
    }

    private void replaceFragment(){
        MainActivity mainActivity = new MainActivity();
        VospitannikFragment vospitannikFragment = new VospitannikFragment();
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
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
