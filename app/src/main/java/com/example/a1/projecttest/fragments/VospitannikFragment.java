package com.example.a1.projecttest.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangeListener;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VospitannikFragment extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.vospitanik_fragment, container, false);
        List<String> time = new ArrayList<>();
        time.add("7:00 - 8:00");
        time.add("8:00 - 8:15");
        time.add("8:15 - 8:30");
        time.add("8:30 - 9:10");
        time.add("9:10 - 12:00");
        time.add("9:10 - 12:00");
        time.add("12:00 - 12:10");
        time.add("12:10 - 13:10");
        time.add("13:10 - 15:10");
        time.add("15:10 - 15:30");
        time.add("15:10 - 16:00");
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

        final Calendar calendar = Calendar.getInstance();
        TextView date = (TextView) view.findViewById(R.id.date_in_childTV);
        TextView times = (TextView) view.findViewById(R.id.time_in_child);
        SimpleDateFormat dfDate_day= new SimpleDateFormat("E, dd.MM.yyyy");
        SimpleDateFormat dfDate_day_time= new SimpleDateFormat("HH:mm");

        date.setText(dfDate_day.format(calendar.getTime()));
        //times.setText("Время: " + dfDate_day_time.format(calendar.getTime()));
        times.setText("Время: 08:03");
        recyclerView = (RecyclerView) view.findViewById(R.id.vospit_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        reBuild();

        Button button = (Button) view.findViewById(R.id.child_not_arriveBT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    public void reBuild(){
        List<String> time = new ArrayList<>();
        time.add("7:00 - 8:00");
        time.add("8:00 - 8:15");
        time.add("8:15 - 8:30");
        time.add("8:30 - 9:10");
        time.add("9:10 - 12:00");
        time.add("9:10 - 12:00");
        time.add("12:00 - 12:10");
        time.add("12:10 - 13:10");
        time.add("13:10 - 15:10");
        time.add("15:10 - 15:30");
        time.add("15:10 - 16:00");
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
        recyclerView.setAdapter(new VospitannikAdapter(listService, colors, time));
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

