package com.example.a1.projecttest.zavedushaia.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.zavedushaia.adapters.RegisterKidsAdapter;

import java.io.IOException;
import java.util.List;

public class RegisterChildFragment extends Fragment{
    RecyclerView recyclerView;
    GetKinderGarten getKinderGarten;
    List<GetAllKidsModel> getAllKidsModels;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kids_fragment_zav, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_kids);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        beginThread();
        recyclerView.setAdapter(new RegisterKidsAdapter(getAllKidsModels, getActivity()));
        return view;
    }
    public void beginThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getAllKids();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getAllKids(){
        RestService restService = new RestService();
        try {
            getKinderGarten = restService.getKinderGartenZav("352");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            getAllKidsModels = restService.getAllKidsModels(getKinderGarten.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
