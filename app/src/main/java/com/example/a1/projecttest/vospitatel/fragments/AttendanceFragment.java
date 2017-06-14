package com.example.a1.projecttest.vospitatel.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a1.projecttest.PositionSaveSession;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.rest.Models.GetAttendanceModel;
import com.example.a1.projecttest.rest.Models.GetKidsByGroupIdModel;
import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.RecyclerTouchListener;
import com.example.a1.projecttest.vospitatel.adapters.AttendanceAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.http.GET;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
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
    Observable<GetStatusCode> getStatusCodeObservable;
    GetStatusCode getStatusCodeOn;
    EditText dateEdit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attendance_layout, container, false);
        session = new UserLoginSession(getActivity());
        getGroupAttendance = new ArrayList<>();
        dateEdit = (EditText) view.findViewById(R.id.attendance_date_edit_text);
        textChange(dateEdit);
        recyclerView = (XRecyclerView) view.findViewById(R.id.kid_present_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getGroupAttendance = new ArrayList<>();
                getAttendanceList(false, "");
                recyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                recyclerView.loadMoreComplete();
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                view.findViewById(R.id.present_in_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addAttendance(position, "1");
                    }
                });
                view.findViewById(R.id.present_out_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addAttendance(position, "0");
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
    public void getAttendanceList(boolean isSeek, String date){
        RestService restService = new RestService();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        dateEdit.setText(simpleDateFormat.format(calendar.getTime()));
        try {
            getAttendanceObserver = restService.getAttendance(session.getTutorGroupId(), isSeek ? date : simpleDateFormat.format(calendar.getTime()));
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getAllKidByGroup(){
        RestService restService = new RestService();
        try {
            getKidsByGroupIdObserver = restService.getKidsByGroupIdObserber(session.getTutorGroupId());
            getKidsByGroupIdObserver.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<GetKidsByGroupIdModel>>() {

                        @Override
                        public void onCompleted() {
                            getAttendanceList(false, "");
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<GetKidsByGroupIdModel> getKidsByGroupIdModels) {
                            getKidsByGroupIdModelsOn = getKidsByGroupIdModels;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addAttendance(int position, String isPresent){
        RestService restService = new RestService();
        try {
            getStatusCodeObservable = restService.createAttendance(getGroupAttendance.get(position - 1).getUserId(), isPresent);
            getStatusCodeObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<GetStatusCode>() {
                        @Override
                        public void onCompleted() {
                            if (getStatusCodeOn != null)
                                if (getStatusCodeOn.getCode().equals("200")){
                                    getGroupAttendance.clear();
                                    getAllKidByGroup();
                                }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(GetStatusCode getStatusCode) {
                            getStatusCodeOn = getStatusCode;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void createAttendanceList(){
        boolean isNotPresent;
        getGroupAttendance = new ArrayList<>();
        for (GetKidsByGroupIdModel i: getKidsByGroupIdModelsOn){
            isNotPresent = false;
            GetAttendanceModel getAttendanceModel = new GetAttendanceModel();
            for (GetAttendanceModel j: getAttendanceModelsOn) {
                if (i.getId().equals(j.getUserId())) {
                    getAttendanceModel.setUserId(i.getId());
                    getAttendanceModel.setName(i.getName());
                    getAttendanceModel.setPatronymic(i.getPatronymic());
                    getAttendanceModel.setSurname(i.getSurname());
                    getAttendanceModel.setIsPresent(j.getIsPresent());
                    getAttendanceModel.setTime(j.getTime().substring(0, 5));
                    getGroupAttendance.add(new GetAttendanceModel(getAttendanceModel));
                    isNotPresent = true;
                }
            }
            if (!isNotPresent){
                getAttendanceModel.setUserId(i.getId());
                getAttendanceModel.setName(i.getName());
                getAttendanceModel.setPatronymic(i.getPatronymic());
                getAttendanceModel.setSurname(i.getSurname());
                getAttendanceModel.setIsPresent("0");
                getGroupAttendance.add(new GetAttendanceModel(getAttendanceModel));
            }
        }
    }

    public void textChange(final EditText editText){

        TextWatcher textWatcher = new TextWatcher() {
            private String current = "";
            private String yyyymmdd = "ГГГГММДД";
            private Calendar cal = Calendar.getInstance();
            boolean isEnd = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getId() == R.id.attendance_date_edit_text) {
                    if (isEnd)
                        isEnd = false;
                    else {
                        if (!s.toString().equals(current)) {
                            String clean = s.toString().replaceAll("[^\\d.]", "");
                            String cleanC = current.replaceAll("[^\\d.]", "");
                            int year = 0;
                            int month = 0;
                            int day = 0;
                            int cl = clean.length();
                            int sel = cl;
                            for (int i = 4; i <= cl && i < 8; i += 2) {
                                sel++;
                            }
                            //Fix for pressing delete next to a forward slash
                            if (clean.equals(cleanC)) sel--;

                            if (clean.length() < 8) {
                                clean = clean + yyyymmdd.substring(clean.length());
                            } else {
                                //This part makes sure that when we finish entering numbers
                                //the date is correct, fixing it otherwise
                                year = Integer.parseInt(clean.substring(0, 4));
                                month = Integer.parseInt(clean.substring(4, 6));
                                day = Integer.parseInt(clean.substring(6, 8));
                                if (year > cal.get(Calendar.YEAR) || year < 2000) {
                                    clean = clean.replace(clean.substring(0, 4), String.valueOf(cal.get(Calendar.YEAR)));
                                    year = cal.get(Calendar.YEAR);
                                }
                                if (month > 12 || month < 0) {
                                    if (cal.get(Calendar.MONTH) > 10)
                                        clean = clean.replace(clean.substring(4, 6), String.valueOf(cal.get(Calendar.MONTH)));
                                    else
                                        clean = clean.replace(clean.substring(4, 6), String.valueOf("0" + cal.get(Calendar.MONTH)));
                                    month = cal.get(Calendar.MONTH);
                                }
                                if (day > cal.getActualMaximum(Calendar.DAY_OF_MONTH) || day < 0) {
                                    clean = clean.replace(clean.substring(6, 8), String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH)));
                                    day = cal.get(Calendar.DAY_OF_MONTH);
                                }
                                cal.set(Calendar.YEAR, year);
                                cal.set(Calendar.MONTH, month);
                                cal.set(Calendar.DAY_OF_MONTH, day);

                                clean = String.format("%02d%02d%02d", year, month, day);
                                isEnd = true;
                            }

                            clean = String.format("%s-%s-%s", clean.substring(0, 4),
                                    clean.substring(4, 6), clean.substring(6, 8));

                            sel = sel < 0 ? 0 : sel;
                            current = clean;
                            if (isEnd) {
                                getAttendanceList(true, current);
                            }
                            editText.setText(current);
                            editText.setSelection(sel < current.length() ? sel : current.length());
                        }
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(textWatcher);
    }

}
