package com.example.a1.projecttest.zavedushaia.fragments;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.PositionSaveSession;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.Models.GetKinderGartenGroup;
import com.example.a1.projecttest.rest.Models.GetScheduleListModel;
import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.zavedushaia.adapters.GroupsAdapter;

import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinWorkerThread;

@EFragment
public class GroupsFragment extends Fragment implements View.OnClickListener{
    TextView nameGroupTv;
    TextView tutorNameTv;
    List<GetKinderGartenGroup> getKinderGartenGroups;
    RecyclerView recyclerView;
    GetKinderGarten getKinderGarten;
    Button addGroupsButton;
    GetStatusCode getStatusCodeSetGroup;
    Typeface typeface;
    Dialog dialog;
    EditText nameGroup;
    EditText idTutor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.groups_layout_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/OpenSans-Regular.ttf");
        addGroupsButton = (Button) view.findViewById(R.id.add_groups_button);
        addGroupsButton.setOnClickListener(this);
        addGroupsButton.setTypeface(typeface);
        threadGetGroup();
        GroupsAdapter groupsAdapter = new GroupsAdapter(getKinderGartenGroups, getActivity());
        recyclerView.setAdapter(groupsAdapter);
        return view;
    }

    public void setGroup(String name, String kinderGartenId, String tutorId){
        RestService restService = new RestService();
        try {
            getStatusCodeSetGroup = restService.setGroups(name, kinderGartenId, tutorId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void threadInsertGroup(final String name, final String kinderGartenId, final String tutorId){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                setGroup(name, kinderGartenId, tutorId);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            getKinderGarten = restService.getKinderGartenZav(userLoginSession.getID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_groups_button:
                showDialog();
                break;
            case R.id.save_group:
                threadInsertGroup(nameGroup.getText().toString(), getKinderGarten.getId(), idTutor.getText().toString());
                if (getStatusCodeSetGroup.getCode().equals("200")){
                    threadGetGroup();
                    recyclerView.setAdapter(new GroupsAdapter(getKinderGartenGroups, getActivity()));
                } else if (getStatusCodeSetGroup.getCode().equals("500")){
                    Toast.makeText(getActivity(), getStatusCodeSetGroup.getStatus(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel_button_add_group:
                dialog.dismiss();
                break;
        }
    }
    public void showDialog() {

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_groups_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        Button saveButton = (Button) dialog.findViewById(R.id.save_group);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_button_add_group);
        saveButton.setOnClickListener(this);
        cancel.setOnClickListener(this);
        nameGroup = (EditText) dialog.findViewById(R.id.name_group_add_groups_dialog);
        idTutor = (EditText) dialog.findViewById(R.id.id_tutor_add_groups_dialog);
        dialog.show();
    }
}
