package com.example.a1.projecttest.zavedushaia.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.daimajia.swipe.SwipeLayout;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetAllTutors;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.Extension;
import java.util.List;
import java.util.Locale;


public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorHolder> {
    List<GetAllTutors> getAllTutorses;
    Context context;
    Typeface typeface;
    Typeface typefaceBold;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
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
    public void onBindViewHolder(TutorHolder holder, final int position) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
        typefaceBold = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Bold.ttf");
        holder.name.setText(getAllTutorses.get(position).getSurname()
        + " " + getAllTutorses.get(position).getName()
        + " "+ getAllTutorses.get(position).getPatronymic());
        holder.id.setText("Идентификатор: " + getAllTutorses.get(position).getId());
        holder.name.setTypeface(typefaceBold);
        holder.id.setTypeface(typeface);
       // binderHelper.bind(holder.swipeRevealLayout, getAllTutorses.get(position).getId());
       /* holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return getAllTutorses.size();
    }

    class TutorHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView id;
        SwipeLayout swipeRevealLayout;
        CardView cardView;
        ImageView editItem;
        ImageView deleteItem;
        public TutorHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tutor_name_zav_text_view);
            id = (TextView) itemView.findViewById(R.id.tutor_id_zav_text_view);
            swipeRevealLayout = (SwipeLayout) itemView.findViewById(R.id.swipe_layout);
            cardView = (CardView) itemView.findViewById(R.id.card_group_list_fragment);
            editItem = (ImageView) itemView.findViewById(R.id.image_edit_option);
            deleteItem = (ImageView) itemView.findViewById(R.id.image_delete_option);
           // deleteLayout = (Button) itemView.findViewById(R.id.delete_button_group);
           // deleteLayout = itemView.findViewById(R.id.delete_layout);
           // editLayout = (Button) itemView.findViewById(R.id.edit_button_group);
        }


    }

}
