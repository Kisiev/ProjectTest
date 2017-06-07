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
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.rest.Models.GetAttendanceModel;
import com.example.a1.projecttest.rest.Models.GetKidsByGroupIdModel;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.RecyclerTouchListener;
import com.example.a1.projecttest.vospitatel.adapters.AttendanceAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.http.GET;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment
public class AttendanceFragment extends Fragment {
    XRecyclerView recyclerView;
    UserLoginSession session;
    List<GetAttendanceModel> getAttendanceModelsOn;
    List<GetAttendanceModel> getGroupAttendance;
    List<GetKidsByGroupIdModel> getKidsByGroupIdModelsOn;
    Observable<List<GetKidsByGroupIdModel>> getKidsByGroupIdObserver;
    Observable<List<GetAttendanceModel>> getAttendanceObserver;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attendance_layout, container, false);
        session = new UserLoginSession(getActivity());
        getGroupAttendance = new ArrayList<>();
        recyclerView = (XRecyclerView) view.findViewById(R.id.kid_present_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                view.findViewById(R.id.present_in_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getAllKidByGroup();
        return view;
    }
    public void getAttendanceList(){
        RestService restService = new RestService();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        try {
            getAttendanceObserver = restService.getAttendance(session.getTutorGroupId(), simpleDateFormat.format(calendar.getTime()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        getAttendanceObserver.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GetAttendanceModel>>() {
                    @Override
                    public void onCompleted() {
                        createAttendanceList();
                        recyclerView.setAdapter(new AttendanceAdapter(getActivity(), getGroupAttendance));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<GetAttendanceModel> getAttendanceModels) {
                        getAttendanceModelsOn = getAttendanceModels;
                    }
                });
    }

    public void getAllKidByGroup(){
        RestService restService = new RestService();
        try {
            getKidsByGroupIdObserver = restService.getKidsByGroupIdObserber(session.getTutorGroupId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        getKidsByGroupIdObserver.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GetKidsByGroupIdModel>>() {

                    @Override
                    public void onCompleted() {
                        getAttendanceList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<GetKidsByGroupIdModel> getKidsByGroupIdModels) {
                        getKidsByGroupIdModelsOn = getKidsByGroupIdModels;
                    }
                });
    }

    public void addAttendance(){

    }

    public void createAttendanceList(){
        SimpleDateFormat date = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        boolean isNotPresent;
        GetAttendanceModel getAttendanceModel = new GetAttendanceModel();
        for (GetKidsByGroupIdModel i: getKidsByGroupIdModelsOn){
            isNotPresent = false;
            for (GetAttendanceModel j: getAttendanceModelsOn) {
                if (i.getId().equals(j.getId())) {
                    getAttendanceModel.setUserId(i.getId());
                    getAttendanceModel.setName(i.getName());
                    getAttendanceModel.setPatronymic(i.getPatronymic());
                    getAttendanceModel.setSurname(i.getSurname());
                    getAttendanceModel.setIsPresent("1");
                    getAttendanceModel.setTime(date.format(j.getTime()));
                    getGroupAttendance.add(getAttendanceModel);
                    isNotPresent = true;
                }
            }
            if (!isNotPresent){
                getAttendanceModel.setUserId(i.getId());
                getAttendanceModel.setName(i.getName());
                getAttendanceModel.setPatronymic(i.getPatronymic());
                getAttendanceModel.setSurname(i.getSurname());
                getAttendanceModel.setIsPresent("0");
                getGroupAttendance.add(getAttendanceModel);
            }
        }
    }
}
