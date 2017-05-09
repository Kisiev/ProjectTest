package com.example.a1.projecttest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.utils.GreedDataType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.a1.projecttest.R.id.imageView;

public class ArrivingFragment extends Fragment {
    Spinner spinnerGroup;
    Spinner spinnerDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.poseshaemost_layout, container, false);
        createTable(view);
        initSpiners(view);

        return view;
    }

    public void initSpiners(View view){
        spinnerDate = (Spinner) view.findViewById(R.id.date_spinner);
        spinnerGroup = (Spinner) view.findViewById(R.id.group_spinner);
        spinnerDate.setPrompt("Выберите время");
        spinnerGroup.setPrompt("Выберите группу");

        String[] data = {"Алые паруса", "Солнышко", "Веселые медведи", "Пираты", "Птенчики"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroup.setAdapter(adapter);

    }

    public void createTable(View view){
        List<GreedDataType> greedDataTypes = new ArrayList<>();
        GreedDataType greedDataType = new GreedDataType();
        greedDataType.setFIO("Иванов П.А.");
        greedDataType.setYavka("Б");
        greedDataType.setTime_arrive("15:07");
        greedDataType.setTime_end("15:08");
        greedDataType.setMore("Сломал ногу");
        for (int i =0; i < 50; i ++)
            greedDataTypes.add(greedDataType);

        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.tablelayout);
        TableRow header = new TableRow(getActivity());
        header.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        header.setPadding(16, 24, 16, 24);
        header.setBackgroundColor(getResources().getColor(R.color.color_for_table));
        setTextInTextView("ФИО", header, 0);
        setTextInTextView("Явка", header, 1);
        setTextInTextView("Время прибытия", header, 2);
        setTextInTextView("Время ухода", header, 3);
        setTextInTextView("Примечания", header, 4);
        tableLayout.addView(header, 0);
        for (int i = 0; i < 50; i++) {

            TableRow tableRow = new TableRow(getActivity());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            if (i%2 != 0)
                tableRow.setBackgroundColor(getResources().getColor(R.color.color_for_table));
            tableRow.setPadding(16, 24, 16, 24);
            setTextInTextView(greedDataTypes.get(i).getFIO(), tableRow, 0);
            setTextInTextView(greedDataTypes.get(i).getYavka(), tableRow, 1);
            setTextInTextView(greedDataTypes.get(i).getTime_arrive(), tableRow, 2);
            setTextInTextView(greedDataTypes.get(i).getTime_end(), tableRow, 3);
            setTextInTextView(greedDataTypes.get(i).getMore(), tableRow, 4);
            tableLayout.addView(tableRow, i + 1);

        }
    }
    public void setTextInTextView(String text, TableRow tableRow, int index){
        TextView textView = new TextView(getActivity());
        textView.setPadding(16, 0, 24, 0);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        tableRow.addView(textView, index);
    }
}
