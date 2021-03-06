package com.example.a1.projecttest.zavedushaia.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.PositionSaveSession;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.SpinnerDialogDaysAdapter;
import com.example.a1.projecttest.rest.Models.GetAllTutors;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.Models.GetKinderGartenGroup;
import com.example.a1.projecttest.rest.Models.GetScheduleListModel;
import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.RecyclerTouchListener;
import com.example.a1.projecttest.zavedushaia.ChildListActivity;
import com.example.a1.projecttest.zavedushaia.ChildListActivity_;
import com.example.a1.projecttest.zavedushaia.adapters.GroupsAdapter;
import com.example.a1.projecttest.zavedushaia.adapters.TutorSpinnerAdapter;

import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinWorkerThread;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment
public class GroupsFragment extends Fragment implements View.OnClickListener{




    List<GetKinderGartenGroup> getKinderGartenGroups;
    RecyclerView recyclerView;
    GetKinderGarten getKinderGarten;
    Button addGroupsButton;
    GetStatusCode getStatusCodeSetGroup;
    Typeface typeface;
    Dialog dialog;
    Dialog addKidDialog;
    EditText nameGroup;
    Spinner idTutor;
    EditText idTutor_v;
    GetStatusCode getStatusCode;
    Observable<List<GetAllTutors>> getAllTutorObserver;
    List<GetAllTutors> getAllTutorsOn;
    int pos = -1;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.groups_layout_fragment, container, false);
        setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/SF-UI-Text-Regular.ttf");

        threadGetGroup();
        GroupsAdapter groupsAdapter = new GroupsAdapter(getKinderGartenGroups, getActivity());
        recyclerView.setAdapter(groupsAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                view.findViewById(R.id.card_group_list_fragment).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //pos = position;
                        //showDialogAddKid();
                        Intent intent = new Intent(getActivity(), ChildListActivity_.class);
                        intent.putExtra(ConstantsManager.GROUP_INTENT, getKinderGartenGroups.get(position).getGroupId());
                        intent.putExtra(ConstantsManager.GROUP_NAME_INTENT, getKinderGartenGroups.get(position).getGroupName());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_item_in_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item_menu:
                showDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void addKidThread(final String parentId, final String name, final String surname, final String patronymic, final String groupId){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                addKidInGroup(parentId, name, surname, patronymic, groupId);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addKidInGroup(String parentId, String name, String surname, String patronymic, String groupId){
        RestService restService = new RestService();
        try {
            getStatusCode = restService.setKidInGroup(parentId, name, surname, patronymic, groupId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_group:
                //threadInsertGroup(nameGroup.getText().toString(), getKinderGarten.getId(), ((GetAllTutors) idTutor.getSelectedItem()).getId());
                threadInsertGroup(nameGroup.getText().toString(), getKinderGarten.getId(), idTutor_v.getText().toString());
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
            case R.id.cancel_button_child_in_group:
                addKidDialog.dismiss();
                break;

        }
    }

    public void getAllTutors(){
        RestService restService = new RestService();
        try {
            getAllTutorObserver = restService.getAllTutorsObserver(getKinderGarten.getId());
            getAllTutorObserver.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<GetAllTutors>>() {
                        @Override
                        public void onCompleted() {
                            TutorSpinnerAdapter adapter = new TutorSpinnerAdapter(getActivity(), getAllTutorsOn);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            idTutor.setAdapter(adapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<GetAllTutors> getAllTutorses) {
                            getAllTutorsOn = getAllTutorses;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
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
        idTutor = (Spinner) dialog.findViewById(R.id.id_tutor_add_groups_dialog);
        idTutor_v = (EditText) dialog.findViewById(R.id.id_tutor_add_groups_dialog_v);
        getAllTutors();

        dialog.show();
    }

}
