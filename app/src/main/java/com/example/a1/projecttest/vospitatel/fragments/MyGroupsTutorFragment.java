package com.example.a1.projecttest.vospitatel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.vospitatel.adapters.MyGroupTutorAdapter;

import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

@EFragment()
public class MyGroupsTutorFragment extends Fragment {
    RecyclerView recyclerViewMenu;
    List<Integer> resursesImage;
    List<String> resursesButton;
    List<Integer> resursesColor;
    private void setResursesForList(){
        resursesImage.add(R.mipmap.kolkol);
        resursesImage.add(R.mipmap.list);
        resursesImage.add(R.mipmap.calendar_1);
        resursesImage.add(R.mipmap.child_menu);
        resursesButton.add("Расписание");
        resursesButton.add("Распорядок");
        resursesButton.add("Календарь");
        resursesButton.add("Дети");
        resursesColor.add(getActivity().getResources().getColor(R.color.colorYellow));
        resursesColor.add(getActivity().getResources().getColor(R.color.colorGreen));
        resursesColor.add(getActivity().getResources().getColor(R.color.colorViolet));
        resursesColor.add(getActivity().getResources().getColor(R.color.colorOrange));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_groups_tutor_fragment, container, false);
        resursesImage = new ArrayList<>();
        resursesButton = new ArrayList<>();
        resursesColor = new ArrayList<>();
        recyclerViewMenu = (RecyclerView) view.findViewById(R.id.recycler_group_menu);
        recyclerViewMenu.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        setResursesForList();
        recyclerViewMenu.setAdapter(new MyGroupTutorAdapter(resursesImage, resursesButton, resursesColor));
        return view;
    }
}
