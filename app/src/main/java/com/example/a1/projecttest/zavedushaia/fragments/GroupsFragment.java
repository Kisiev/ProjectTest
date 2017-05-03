package com.example.a1.projecttest.zavedushaia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.Models.GetKinderGartenGroup;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.zavedushaia.adapters.GroupsAdapter;

import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.util.List;

@EFragment
public class GroupsFragment extends Fragment {
    TextView nameGroupTv;
    TextView tutorNameTv;
    List<GetKinderGartenGroup> getKinderGartenGroups;
    RecyclerView recyclerView;
    GetKinderGarten getKinderGarten;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.groups_layout_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        threadGetGroup();
        GroupsAdapter groupsAdapter = new GroupsAdapter(getKinderGartenGroups, getActivity());
        recyclerView.setAdapter(groupsAdapter);
        return view;
    }
    public void threadGetGroup(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getKinderGartenZav();
                getGroups();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void getGroups (){
        RestService restService = new RestService();
        try {
            getKinderGartenGroups = restService.getKinderGartenGroups(getKinderGarten.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getKinderGartenZav(){
        RestService restService = new RestService();
        UserLoginSession userLoginSession = new UserLoginSession(getActivity());
        try {
            getKinderGarten = restService.getKinderGartenZav("352");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
