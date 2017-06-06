package com.example.a1.projecttest.vospitatel.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


public class AttendanceAdapter extends XRecyclerView.Adapter<AttendanceAdapter.AttendanceHolder> {

    Context context;
    Typeface typeface;
    public AttendanceAdapter(Context context){
        this.context = context;
    }

    @Override
    public AttendanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_layout_item, parent, false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceHolder holder, int position) {
        holder.fioTextView.setText("Кисиев Валерий");
        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_done_green_24dp));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class AttendanceHolder extends XRecyclerView.ViewHolder{
        TextView fioTextView;
        Button isPresentButton;
        Button isNotPresentButton;
        ImageView imageView;
        public AttendanceHolder(View itemView) {
            super(itemView);
            typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");
            fioTextView = (TextView) itemView.findViewById(R.id.attendance_fio_text_view);
            isPresentButton = (Button) itemView.findViewById(R.id.present_in_button);
            isNotPresentButton = (Button) itemView.findViewById(R.id.present_out_button);
            imageView = (ImageView) itemView.findViewById(R.id.is_present_icon);
            fioTextView.setTypeface(typeface);
            isPresentButton.setTypeface(typeface);
            isNotPresentButton.setTypeface(typeface);
        }
    }
}
