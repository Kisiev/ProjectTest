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
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.rest.Models.GetAllTutors;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.zavedushaia.adapters.TutorAdapter;

import java.io.IOException;
import java.util.List;

public class TutorFragment extends Fragment {
    RecyclerView recyclerView;
    List<GetAllTutors> getAllTutorses;
    GetKinderGarten getKinderGarten;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutor_zav_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_tutor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        beginThread();
        recyclerView.setAdapter(new TutorAdapter(getAllTutorses, getActivity()));
        return view;
    }
    public void beginThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getTutors();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void getTutors(){
        RestService restService = new RestService();
        UserLoginSession userLoginSession = new UserLoginSession(getActivity());
        try {
            getKinderGarten = restService.getKinderGartenTutor(userLoginSession.getID());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            getAllTutorses = restService.getAllTutorses(getKinderGarten.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
