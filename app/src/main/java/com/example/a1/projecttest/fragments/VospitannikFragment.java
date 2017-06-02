package com.example.a1.projecttest.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.rest.Models.GetAllDaysModel;
import com.example.a1.projecttest.rest.Models.GetScheduleListModel;
import com.example.a1.projecttest.rest.Models.GetStatusKidModel;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.RecyclerTouchListener;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.vospitanik_fragment)
public class VospitannikFragment extends Fragment {
    XRecyclerView recyclerView;
    Thread getScheduleThread;
    List<GetStatusKidModel> getStatusKidModels;
    List<GetScheduleListModel> getScheduleListModels;
    Observable<List<GetStatusKidModel>> getStatusKidObserver;
    Observable<List<GetScheduleListModel>> getScheduleListObserver;

    public static String getDateString(int hours, int mins, int sec){
        Time time = new Time(hours, mins, sec);
        return String.valueOf(time);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.vospitanik_fragment, container, false);

        recyclerView = (XRecyclerView) view.findViewById(R.id.vospit_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        getScheduleList();

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getScheduleList();
                recyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                recyclerView.loadMoreComplete();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick( View view, final int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Button button = (Button) view.findViewById(R.id.child_not_arriveBT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/opensans.ttf");
        button.setTypeface(typeface);
        return view;
    }

    public static int generatedColor(){
        Random random = new Random();
        int red = 0;
        int green = 0;
        int blue = 0;
        while ((red <= 230) && (green <= 230) && (blue <= 230)) {
            red = random.nextInt(254);
            green = random.nextInt(254);
            blue = random.nextInt(254);
        }

        if (red >= 230){
            green = random.nextInt(230);
            blue = random.nextInt(10);
        }

        if (green >= 230){
            red = random.nextInt(230);
            blue = random.nextInt(10);
        }

        if (blue >= 230){
            red = random.nextInt(200);
            green = random.nextInt(10);
        }
        Integer colors = Color.argb(40 ,red, green, blue);
        return colors;
    }

/*
    @Background
    public void loadEntity() {
        getLoaderManager().restartLoader(ConstantsManager.ID_LOADER, null, new LoaderManager.LoaderCallbacks<List<ChildStatusEntity>>() {

            @Override
            public Loader<List<ChildStatusEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<ChildStatusEntity>> loader = new AsyncTaskLoader<List<ChildStatusEntity>>(getActivity()) {
                    @Override
                    public List<ChildStatusEntity> loadInBackground() {
                        return ChildStatusEntity.selectChilds();
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<ChildStatusEntity>> loader, List<ChildStatusEntity> data) {
                VospitannikAdapter adapter = new VospitannikAdapter(data, getActivity());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<ChildStatusEntity>> loader) {

            }
        });
    }
*/

    public void getScheduleList(){
        final RestService restService = new RestService();
        final UserLoginSession userLoginSession = new UserLoginSession(getActivity());
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        try {
            if (!userLoginSession.getKidGroupSchedule().equals("")) {
                getScheduleListObserver = restService.getScheduleListModel(userLoginSession.getKidGroupSchedule(), String.valueOf(day == 1 ? 7 : day - 1));
                getScheduleListObserver.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<GetScheduleListModel>>() {
                            @Override
                            public void onCompleted() {
                                try {
                                    getStatusKidObserver = restService.getStatusKidModels(userLoginSession.getKidId());
                                    getStatusKidObserver.subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<List<GetStatusKidModel>>() {
                                                @Override
                                                public void onCompleted() {
                                                    recyclerView.setAdapter(new VospitannikAdapter(getScheduleListModels, getStatusKidModels, getActivity()));
                                                }

                                                @Override
                                                public void onError(Throwable e) {

                                                }

                                                @Override
                                                public void onNext(List<GetStatusKidModel> getStatusKidModels) {
                                                    VospitannikFragment.this.getStatusKidModels = getStatusKidModels;
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
                            public void onNext(List<GetScheduleListModel> getScheduleListModels) {
                                VospitannikFragment.this.getScheduleListModels = getScheduleListModels;
                            }
                        });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public void showDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.reson_child_not_arrive);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        final EditText editText = (EditText) dialog.findViewById(R.id.reson_text_ed);
        Button okButton = (Button) dialog.findViewById(R.id.send_bt);
        final Button cancelButton = (Button) dialog.findViewById(R.id.cancel_bt);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Editable text = editText.getText();

                if (!TextUtils.isEmpty(text)){
                    dialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}

