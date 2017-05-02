package com.example.a1.projecttest.zavedushaia.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetKinderGartenGroup;

import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupsHolder>{
    List<GetKinderGartenGroup> getKinderGartenGroups;
    public GroupsAdapter(List<GetKinderGartenGroup> getKinderGartenGroups){
        this.getKinderGartenGroups = getKinderGartenGroups;
    }

    @Override
    public GroupsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groups_layout_item, parent, false);
        return new GroupsHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupsHolder holder, int position) {
        holder.nameGroup.setText("Группа: " + getKinderGartenGroups.get(position).getGroupName());
        holder.tutorName.setText("Воспитатель: " + getKinderGartenGroups.get(position).getTutorSurname()
        +" "+ getKinderGartenGroups.get(position).getTutorName().substring(0, 1)
        +". "+ getKinderGartenGroups.get(position).getTutorPatronymic().substring(0, 1)
        +".");
    }

    @Override
    public int getItemCount() {
        return getKinderGartenGroups.size();
    }

    class GroupsHolder extends RecyclerView.ViewHolder{
        TextView nameGroup;
        TextView tutorName;
        public GroupsHolder(View itemView) {
            super(itemView);
            nameGroup = (TextView) itemView.findViewById(R.id.name_group_text_view);
            tutorName = (TextView) itemView.findViewById(R.id.tutor_name_text_view);
        }
    }
}
