package com.example.a1.projecttest.zavedushaia.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.RecyclerTouchListener;
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

    TextView header;
    EditText nameEdit;
    EditText surnameEdit;
    EditText patronymicEdit;
    EditText idParentEdit;


    List<GetKinderGartenGroup> getKinderGartenGroups;
    RecyclerView recyclerView;
    GetKinderGarten getKinderGarten;
    Button addGroupsButton;
    GetStatusCode getStatusCodeSetGroup;
    Typeface typeface;
    Dialog dialog;
    Dialog addKidDialog;
    EditText nameGroup;
    EditText idTutor;
    GetStatusCode getStatusCode;
    int pos = -1;
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
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Button reductionButtonGroup = (Button) view.findViewById(R.id.edit_button_group);
                reductionButtonGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "РЕДАКТОР", Toast.LENGTH_SHORT).show();
                    }
                });
                CardView cardView = (CardView) view.findViewById(R.id.card_group_list_fragment);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pos = position;
                        showDialogAddKid();
                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
            case R.id.add_button_child_in_group:
                if (pos != -1){
                    addKidThread(idParentEdit.getText().toString(),
                            nameEdit.getText().toString(),
                            surnameEdit.getText().toString(),
                            patronymicEdit.getText().toString(),
                            getKinderGartenGroups.get(pos).getGroupId());
                }
                if (getStatusCode.getCode().equals("200")){
                    Toast.makeText(getActivity(), R.string.successful, Toast.LENGTH_SHORT).show();
                } else if (getStatusCode.getCode().equals("200")){
                    Toast.makeText(getActivity(), getStatusCode.getStatus(), Toast.LENGTH_SHORT).show();
                }
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

    public void showDialogAddKid(){
        addKidDialog = new Dialog(getActivity());
        addKidDialog.setContentView(R.layout.add_kids_in_group_dialog);
        addKidDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        header = (TextView) addKidDialog.findViewById(R.id.header_dialog_add_kid);
        nameEdit = (EditText) addKidDialog.findViewById(R.id.name_child_add_kid_dialog);
        surnameEdit = (EditText) addKidDialog.findViewById(R.id.surname_child_add_kid_dialog);
        patronymicEdit = (EditText) addKidDialog.findViewById(R.id.patronimic_child_add_kid_dialog);
        idParentEdit = (EditText) addKidDialog.findViewById(R.id.id_parent_child_add_kid_dialog);
        Button addKidButton = (Button) addKidDialog.findViewById(R.id.add_button_child_in_group);
        Button cancelButton = (Button) addKidDialog.findViewById(R.id.cancel_button_child_in_group);
        addKidButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        header.setTypeface(typeface);
        nameEdit.setTypeface(typeface);
        surnameEdit.setTypeface(typeface);
        patronymicEdit.setTypeface(typeface);
        addKidButton.setTypeface(typeface);
        cancelButton.setTypeface(typeface);

        header.setText(header.getText() +" "+ getKinderGartenGroups.get(pos).getGroupName());


        addKidDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                pos = -1;
            }
        });
        addKidDialog.show();
    }

}
