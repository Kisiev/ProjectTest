package com.example.a1.projecttest.vospitatel.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.adapters.RaspisanieAdapter;
import com.example.a1.projecttest.adapters.RaspisanieGroupItemAdapter;
import com.example.a1.projecttest.adapters.VospitannikAdapter;

import org.androidannotations.annotations.EFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RaspisanieFragment extends Fragment {

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.raspisanie_fragment, container, false);

        List<String> listNames = new ArrayList<>();
        listNames.add("Иванов В.П");
        listNames.add("Сидоров В.К");
        listNames.add("Петров М.Т");
        listNames.add("Иващенко Е.П");
        listNames.add("Андреев В.Р");

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

        int colorApacity = getResources().getColor(R.color.colorForApacityNull);
        final Calendar calendar = Calendar.getInstance();
        TextView date = (TextView) view.findViewById(R.id.date_in_raspisanieTV);
        TextView times = (TextView) view.findViewById(R.id.time_in_raspisanieTV);
        SimpleDateFormat dfDate_day= new SimpleDateFormat("E, dd.MM.yyyy");
        SimpleDateFormat dfDate_day_time= new SimpleDateFormat("HH:mm");

        date.setText(dfDate_day.format(calendar.getTime()));
        //times.setText("Время: " + dfDate_day_time.format(calendar.getTime()));
        times.setText("Время: 08:03");
        recyclerView = (RecyclerView) view.findViewById(R.id.raspisanie_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RaspisanieFragment raspisanieFragment = new RaspisanieFragment();
        recyclerView.setAdapter(new RaspisanieAdapter(listService, colors, time, raspisanieFragment, listNames, getActivity(), colorApacity));


        return view;
    }
}
