package com.example.a1.projecttest.vospitatel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


public class AttendanceAdapter extends XRecyclerView.Adapter<AttendanceAdapter.AttendanceHolder> {

    @Override
    public AttendanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_layout_item, parent, false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceHolder holder, int position) {
        holder.fioTextView.setText("Кисиев Валерий");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class AttendanceHolder extends XRecyclerView.ViewHolder{
        TextView fioTextView;
        Button isPresentButton;
        Button isNotPresentButton;
        public AttendanceHolder(View itemView) {
            super(itemView);
            fioTextView = (TextView) itemView.findViewById(R.id.attendance_fio_text_view);
            isPresentButton = (Button) itemView.findViewById(R.id.present_in_button);
            isNotPresentButton = (Button) itemView.findViewById(R.id.present_out_button);
        }
    }
}
