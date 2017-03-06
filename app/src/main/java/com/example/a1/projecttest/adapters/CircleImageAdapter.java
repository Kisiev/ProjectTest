package com.example.a1.projecttest.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.google.android.gms.maps.model.Circle;

import java.util.List;


public class CircleImageAdapter extends RecyclerView.Adapter<CircleImageAdapter.CircleImageHolder>{

    List<String> item;
    Context context;
    public CircleImageAdapter (List<String> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public CircleImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.circle_image_item, parent, false);
        return new CircleImageHolder(view);
    }

    @Override
    public void onBindViewHolder(CircleImageHolder holder, int position) {
       // String pos = item.get(position);
        MainActivity.saveGlideParam(holder.imageView, context, R.mipmap.image_human);
       // holder.imageView.setImageResource(R.mipmap.ic_launcher);
        holder.textView.setText(item.get(position));
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class CircleImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        public CircleImageHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_circle);
            textView = (TextView) itemView.findViewById(R.id.text_circle);
        }
    }
}
