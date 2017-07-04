package com.example.a1.projecttest.zavedushaia.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetKidsByGroupIdModel;

import java.util.List;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ChildListHolder>{
    List<GetKidsByGroupIdModel> getKidsByGroupIdModels;
    Typeface bold;
    Typeface normal;
    Context context;
    public ChildListAdapter(Context context, List<GetKidsByGroupIdModel> getKidsByGroupIdModels){
        normal = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
        bold = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Bold.ttf");
        this.getKidsByGroupIdModels = getKidsByGroupIdModels;
        this.context = context;
    }
    @Override
    public ChildListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_list_layout_item, parent, false);
        return new ChildListHolder(view);
    }

    @Override
    public void onBindViewHolder(ChildListHolder holder, int position) {
        holder.nameChild.setText(getKidsByGroupIdModels.get(position).getSurname()
                + " " + getKidsByGroupIdModels.get(position).getName().substring(0, 1)
                + ". " + getKidsByGroupIdModels.get(position).getPatronymic().substring(0, 1) + ".");
        holder.idChild.setText("Идентификатор: " + getKidsByGroupIdModels.get(position).getId());
        holder.dateChild.setText("Дата рождения: " );

        holder.nameChild.setTypeface(bold);
        holder.idChild.setTypeface(normal);
        holder.dateChild.setTypeface(normal);
    }

    @Override
    public int getItemCount() {
        return getKidsByGroupIdModels.size();
    }

    class ChildListHolder extends RecyclerView.ViewHolder {
        TextView nameChild;
        TextView idChild;
        TextView dateChild;
        public ChildListHolder(View itemView) {
            super(itemView);
            nameChild = (TextView) itemView.findViewById(R.id.name_child_list_text_view);
            idChild = (TextView) itemView.findViewById(R.id.id_child_list_text_view);
            dateChild = (TextView) itemView.findViewById(R.id.date_child_text_view);
        }
    }
}
