package com.example.a1.projecttest.vospitatel.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a1.projecttest.R;

import java.util.List;

public class MyGroupTutorAdapter extends RecyclerView.Adapter<MyGroupTutorAdapter.MyGroupTutorHolder>{

    List<Integer> resursesImage;
    List<String> resursesButton;
    List<Integer> resursesColor;
    public MyGroupTutorAdapter ( List<Integer> resursesImage, List<String> resursesButton, List<Integer> resursesColor){
        this.resursesImage = resursesImage;
        this.resursesButton = resursesButton;
        this.resursesColor = resursesColor;
    }

    @Override
    public MyGroupTutorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_group_tutor_fragment_menu_item, parent, false);
        return new MyGroupTutorHolder(view);
    }

    @Override
    public void onBindViewHolder(MyGroupTutorHolder holder, int position) {
        holder.imageView.setImageResource(resursesImage.get(position));
        holder.button.setText(resursesButton.get(position));
        holder.view.setBackgroundColor(resursesColor.get(position));
    }

    @Override
    public int getItemCount() {
        return resursesButton.size();
    }

    class MyGroupTutorHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        Button button;
        View view;
        public MyGroupTutorHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_group_tutor_menu_item);
            button = (Button) itemView.findViewById(R.id.text_group_tutor);
            view = itemView.findViewById(R.id.item_group_tutor);
        }
    }
}
