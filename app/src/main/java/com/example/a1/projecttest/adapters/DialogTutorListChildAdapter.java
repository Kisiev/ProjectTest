package com.example.a1.projecttest.adapters;


import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetUserData;

import java.util.List;

public class DialogTutorListChildAdapter extends RecyclerView.Adapter<DialogTutorListChildAdapter.DialogTutorListChildHolder> {
    List<GetUserData> users;
    public DialogTutorListChildAdapter (List<GetUserData> users){
        this.users = users;
    }

    @Override
    public DialogTutorListChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_child_for_tutor_dialog_item, parent, false);
        return new DialogTutorListChildHolder(view);
    }

    @Override
    public void onBindViewHolder(DialogTutorListChildHolder holder, int position) {
        holder.nameChild.setText(users.get(position).getSurname()
                +" "+ users.get(position).getName().substring(0, 1)
                +". "+ users.get(position).getPatronymic().substring(0, 1));
        holder.lowSmile.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp);
        holder.mediumSmile.setImageResource(R.drawable.ic_sentiment_satisfied_black_24dp);
        holder.highSmile.setImageResource(R.drawable.ic_sentiment_very_satisfied_black_24dp);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class DialogTutorListChildHolder extends RecyclerView.ViewHolder{
        TextView nameChild;
        ImageView lowSmile;
        ImageView mediumSmile;
        ImageView highSmile;
        ImageView sendMessageImage;
        public DialogTutorListChildHolder(View itemView) {
            super(itemView);
            nameChild = (TextView) itemView.findViewById(R.id.name_child_text_dialog);
            lowSmile = (ImageView) itemView.findViewById(R.id.low_smile_image_dialog);
            mediumSmile = (ImageView) itemView.findViewById(R.id.medium_smile_image_dialog);
            highSmile = (ImageView) itemView.findViewById(R.id.high_smile_image_dialog);
            sendMessageImage = (ImageView) itemView.findViewById(R.id.send_message_to_parent);
        }
    }
}
