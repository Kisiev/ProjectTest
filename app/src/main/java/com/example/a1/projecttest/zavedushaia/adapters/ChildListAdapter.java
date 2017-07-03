package com.example.a1.projecttest.zavedushaia.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1.projecttest.R;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ChildListHolder>{

    @Override
    public ChildListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ChildListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ChildListHolder extends RecyclerView.ViewHolder {
        TextView nameChild;
        TextView idChild;
        TextView dateChild;
        public ChildListHolder(View itemView) {
            super(itemView);
            nameChild = (TextView) itemView.findViewById(R.id.name_child_text_view);
            idChild = (TextView) itemView.findViewById(R.id.id_child_text_view);
            dateChild = (TextView) itemView.findViewById(R.id.date_child_text_view);
        }
    }
}
