package com.example.a1.projecttest.vospitatel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.vospitatel.adapters.AttendanceAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.androidannotations.annotations.EFragment;

@EFragment
public class AttendanceFragment extends Fragment {
    XRecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attendance_layout, container, false);
        recyclerView = (XRecyclerView) view.findViewById(R.id.kid_present_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new AttendanceAdapter());
        return view;
    }
}
