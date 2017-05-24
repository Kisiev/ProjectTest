package com.example.a1.projecttest.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetKidsByGroupIdModel;
import com.example.a1.projecttest.rest.Models.GetScheduleStatusesByGroupIdModel;
import com.example.a1.projecttest.rest.Models.GetStatusKidModel;
import com.example.a1.projecttest.rest.Models.GetUserData;

import java.util.List;

import static ru.yandex.core.CoreApplication.getActivity;
import static ru.yandex.core.CoreApplication.readRootForSharedFromGlobalSettings;

public class DialogTutorListChildAdapter extends RecyclerView.Adapter<DialogTutorListChildAdapter.DialogTutorListChildHolder> {
    List<GetKidsByGroupIdModel> users;
    Typeface typeface;
    Context context;
    List<GetScheduleStatusesByGroupIdModel> getScheduleStatusesByGroupIdModels;
    public DialogTutorListChildAdapter (List<GetKidsByGroupIdModel> users, List<GetScheduleStatusesByGroupIdModel> getScheduleStatusesByGroupIdModels, Context context){
        this.users = users;
        this.context = context;
        this.getScheduleStatusesByGroupIdModels = getScheduleStatusesByGroupIdModels;
    }

    @Override
    public DialogTutorListChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_child_for_tutor_dialog_item, parent, false);
        return new DialogTutorListChildHolder(view);
    }

    @Override
    public void onBindViewHolder(final DialogTutorListChildHolder holder, int position) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");
        holder.nameChild.setText(users.get(position).getSurname()
                +" "+ users.get(position).getName().substring(0, 1)
                +". "+ users.get(position).getPatronymic().substring(0, 1));
        holder.lowSmile.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_24dp);
        holder.mediumSmile.setImageResource(R.drawable.ic_sentiment_satisfied_black_24dp);
        holder.highSmile.setImageResource(R.drawable.ic_sentiment_very_satisfied_black_24dp);
        holder.nameChild.setTypeface(typeface);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.colorBlueOpacity));
            }
        });
        if (getScheduleStatusesByGroupIdModels != null) {
            for (GetScheduleStatusesByGroupIdModel i : getScheduleStatusesByGroupIdModels) {
                if (i.getUserId().equals(users.get(position).getId())){
                    switch (i.getStatusId()){
                        case "1":
                            holder.lowSmile.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sentiment_dissatisfied_red_24dp));
                            break;
                        case "2":
                            holder.mediumSmile.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sentiment_satisfied_yellow_24dp));
                            break;
                        case "3":
                            holder.highSmile.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sentiment_very_satisfied_green_24dp));
                            break;
                    }
                }
            }
        }

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
        RelativeLayout relativeLayout;
        public DialogTutorListChildHolder(View itemView) {
            super(itemView);
            nameChild = (TextView) itemView.findViewById(R.id.name_child_text_dialog);
            lowSmile = (ImageView) itemView.findViewById(R.id.low_smile_image_dialog);
            mediumSmile = (ImageView) itemView.findViewById(R.id.medium_smile_image_dialog);
            highSmile = (ImageView) itemView.findViewById(R.id.high_smile_image_dialog);
            sendMessageImage = (ImageView) itemView.findViewById(R.id.send_message_to_parent);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_list_dialog_tutor);
        }
    }
}
