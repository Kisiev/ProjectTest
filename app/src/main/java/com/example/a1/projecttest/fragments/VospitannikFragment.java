package com.example.a1.projecttest.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1.projecttest.Entities.ChildStatusEntity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.RecyclerTouchListener;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@EFragment(R.layout.vospitanik_fragment)
public class VospitannikFragment extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.vospitanik_fragment, container, false);

        SimpleDateFormat dfDate_day= new SimpleDateFormat("E, dd.MM.yyyy");
        SimpleDateFormat dfDate_day_time= new SimpleDateFormat("HH:mm");
        final Calendar calendar = Calendar.getInstance();

        List<Date> time = new ArrayList<>();
        try {
            time.add(dfDate_day_time.parse("7:00"));
            time.add(dfDate_day_time.parse("8:00"));
            time.add(dfDate_day_time.parse("8:15"));
            time.add(dfDate_day_time.parse("8:30"));
            time.add(dfDate_day_time.parse("9:10"));
            time.add(dfDate_day_time.parse("9:10"));
            time.add(dfDate_day_time.parse("12:00"));
            time.add(dfDate_day_time.parse("12:10"));
            time.add(dfDate_day_time.parse("13:10"));
            time.add(dfDate_day_time.parse("15:10"));
            time.add(dfDate_day_time.parse("15:10"));
            time.add(dfDate_day_time.parse("16:00"));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        List<Date> time1 = new ArrayList<>();

        try {
            time1.add(dfDate_day_time.parse("8:00"));
            time1.add(dfDate_day_time.parse("8:15"));
            time1.add(dfDate_day_time.parse("8:30"));
            time1.add(dfDate_day_time.parse("9:10"));
            time1.add(dfDate_day_time.parse("12:00"));
            time1.add(dfDate_day_time.parse("12:00"));
            time1.add(dfDate_day_time.parse("12:10"));
            time1.add(dfDate_day_time.parse("13:10"));
            time1.add(dfDate_day_time.parse("15:10"));
            time1.add(dfDate_day_time.parse("15:30"));
            time1.add(dfDate_day_time.parse("16:00"));
            time1.add(dfDate_day_time.parse("17:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        List<String> listService = new ArrayList<>();
        listService.add("Утренний прием детей на улице");
        listService.add("Утреняя гимнастика");
        listService.add("Свободная деятельность");
        listService.add("Завтрак");
        listService.add("Подготовка к завтраку, завтрак");
        listService.add("Подготовка к прогулке, прогулка");
        listService.add("Гигиенические процедуры");
        listService.add("Подготовка к обеду, обед");
        listService.add("Подготовка ко сну, дневной сон");
        listService.add("Подъем, закаливающие процедуры");
        listService.add("Подготовка к полднику, полдник");
        listService.add("Полдник и домой");

        List<String> coments = new ArrayList<>();
        coments.add("Ваш ребенок плакал после вашего ухода");
        coments.add("Ребенок плохо себя вел");
        coments.add("Ребенок прыгал с унитаза на унитаз");
        coments.add("Он задушил воздушный шарик метомод дихотомии");
        coments.add("Ездил на БМВ класса С");
        coments.add("Дважды ограбил банк");
        coments.add("Разбил стекло тети Пети");
        coments.add("Раздражал всех кроме беременных всадников");
        coments.add("Уехал в сауну");
        coments.add("Прыгал на кропати вместе с охранником");
        coments.add("Шатал трубу");
        coments.add("Шатал трубу второй раз");

        List<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.color2));
        colors.add(getResources().getColor(R.color.color3));
        colors.add(getResources().getColor(R.color.color5));
        colors.add(getResources().getColor(R.color.color6));
        colors.add(getResources().getColor(R.color.color2));
        colors.add(getResources().getColor(R.color.color3));
        colors.add(getResources().getColor(R.color.color5));
        colors.add(getResources().getColor(R.color.color6));
        colors.add(getResources().getColor(R.color.color2));
        colors.add(getResources().getColor(R.color.color3));
        colors.add(getResources().getColor(R.color.color6));
        colors.add(getResources().getColor(R.color.color1));

        TextView date = (TextView) view.findViewById(R.id.date_in_childTV);
        TextView times = (TextView) view.findViewById(R.id.time_in_child);


        if (ChildStatusEntity.selectChilds().size() == 0) {
            for (int i = 0; i < listService.size(); i ++){
                ChildStatusEntity.insert(listService.get(i), time.get(i), time1.get(i), 1, coments.get(i), colors.get(i));
            }
        }



        date.setText(dfDate_day.format(calendar.getTime()));
        times.setText("Время: " + dfDate_day_time.format(calendar.getTime()));
        recyclerView = (RecyclerView) view.findViewById(R.id.vospit_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadEntity();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

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

        return view;
    }

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
                VospitannikAdapter adapter = new VospitannikAdapter(data);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<ChildStatusEntity>> loader) {

            }
        });
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

