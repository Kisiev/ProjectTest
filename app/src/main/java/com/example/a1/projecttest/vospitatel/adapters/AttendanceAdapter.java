package com.example.a1.projecttest.vospitatel.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetAttendanceModel;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;


public class AttendanceAdapter extends XRecyclerView.Adapter<AttendanceAdapter.AttendanceHolder> {

    Context context;
    Typeface typeface;
    List<GetAttendanceModel> getAttendanceModels;
    public AttendanceAdapter(Context context, List<GetAttendanceModel> getAttendanceModels){
        this.context = context;
        this.getAttendanceModels = getAttendanceModels;
    }

    @Override
    public AttendanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_layout_item, parent, false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceHolder holder, int position) {
        holder.fioTextView.setText(getAttendanceModels.get(position).getSurname() +" "+ getAttendanceModels.get(position).getName());
        if (getAttendanceModels.get(position).getTime() != null)
            holder.time.setText(getAttendanceModels.get(position).getTime());
        if (getAttendanceModels.get(position).getIsPresent() != null)
            if (getAttendanceModels.get(position).getTime() != null) {
                holder.imageView.setImageDrawable(getAttendanceModels.get(position).getIsPresent().equals("0") ?
                        AppCompatDrawableManager.get().getDrawable(context, R.drawable.ic_clear_red_24dp) :
                        AppCompatDrawableManager.get().getDrawable(context, R.drawable.ic_done_green_24dp));
            }
    }

    @Override
    public int getItemCount() {
        return getAttendanceModels.size();
    }

    class AttendanceHolder extends XRecyclerView.ViewHolder{
        TextView fioTextView;
        Button isPresentButton;
        Button isNotPresentButton;
        ImageView imageView;
        TextView time;
        public AttendanceHolder(View itemView) {
            super(itemView);
            typeface = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
            fioTextView = (TextView) itemView.findViewById(R.id.attendance_fio_text_view);
            isPresentButton = (Button) itemView.findViewById(R.id.present_in_button);
            isNotPresentButton = (Button) itemView.findViewById(R.id.present_out_button);
            imageView = (ImageView) itemView.findViewById(R.id.is_present_icon);
            time = (TextView) itemView.findViewById(R.id.time_present_text_view);
            fioTextView.setTypeface(typeface);
            isPresentButton.setTypeface(typeface);
            isNotPresentButton.setTypeface(typeface);
        }
    }
}
