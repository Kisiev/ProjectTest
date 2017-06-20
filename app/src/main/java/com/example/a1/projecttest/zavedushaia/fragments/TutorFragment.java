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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.rest.Models.GetAllTutors;
import com.example.a1.projecttest.rest.Models.GetAttendanceModel;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.zavedushaia.adapters.TutorAdapter;
import com.example.a1.projecttest.zavedushaia.adapters.TutorSpinnerAdapter;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TutorFragment extends Fragment implements View.OnClickListener{
    RecyclerView recyclerView;
    List<GetAllTutors> getAllTutorsesOn;
    GetKinderGarten getKinderGartenOn;
    Button addTutorButton;
    Dialog dialog;
    TextView headerDialog;
    Spinner tutorListSpinner;
    Button addTutorDialogButton;
    Button cancelTutorDialogButton;
    Typeface typeface;
    TutorSpinnerAdapter spinnerAdapter;
    Observable<List<GetAllTutors>> getAllTutorObserverable;
    Observable<GetKinderGarten> getKinderGartenObservable;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutor_zav_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_tutor);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/OpenSans-Regular.ttf");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addTutorButton = (Button) view.findViewById(R.id.add_tutor_button);
        addTutorButton.setOnClickListener(this);
        addTutorButton.setTypeface(typeface);
        getTutors();
        return view;
    }

    public void getTutors(){
        final RestService restService = new RestService();
        UserLoginSession userLoginSession = new UserLoginSession(getActivity());
        try {
            getKinderGartenObservable = restService.getKinderGartenTutorObserver(userLoginSession.getID());
            getKinderGartenObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<GetKinderGarten>() {
                        @Override
                        public void onCompleted() {
                            try {
                                getAllTutorObserverable = restService.getAllTutorsObserver(getKinderGartenOn.getId());
                                getAllTutorObserverable.subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<List<GetAllTutors>>() {
                                            @Override
                                            public void onCompleted() {
                                                recyclerView.setAdapter(new TutorAdapter(getAllTutorsesOn, getActivity()));
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onNext(List<GetAllTutors> getAllTutorses) {
                                                getAllTutorsesOn = getAllTutorses;
                                            }
                                        });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(GetKinderGarten getKinderGarten) {
                            getKinderGartenOn = getKinderGarten;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTutorsWithoutGroups(){

    }

    public void showDialogAddTutor(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_tutor_in_kindergarten_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

        headerDialog = (TextView) dialog.findViewById(R.id.header_add_tutor_in_kin_dialog);
        tutorListSpinner = (Spinner) dialog.findViewById(R.id.tutor_without_kindergarten_spinner);
        addTutorDialogButton = (Button) dialog.findViewById(R.id.add_tutor_in_kin_button);
        cancelTutorDialogButton = (Button) dialog.findViewById(R.id.cancel_button_add_tutor_in_kin);

        headerDialog.setTypeface(typeface);
        addTutorDialogButton.setTypeface(typeface);
        cancelTutorDialogButton.setTypeface(typeface);

       // spinnerAdapter = new TutorSpinnerAdapter(getActivity(), list);
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //tutorListSpinner.setAdapter(spinnerAdapter);
        addTutorDialogButton.setOnClickListener(this);
        cancelTutorDialogButton.setOnClickListener(this);

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_tutor_button:
                showDialogAddTutor();
                break;
            case R.id.cancel_button_add_tutor_in_kin:
                dialog.dismiss();
                break;
            case R.id.add_tutor_in_kin_button:
                break;
        }
    }
}
