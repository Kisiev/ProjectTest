package com.example.a1.projecttest.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetAllDaysModel;
import com.example.a1.projecttest.rest.Models.GetServicesByServiceTypeModel;

import java.util.List;

import static ru.yandex.core.CoreApplication.getActivity;

public class SpinnerDialogDaysAdapter extends ArrayAdapter<GetAllDaysModel> implements SpinnerAdapter {
    List<GetAllDaysModel> services;
    Typeface typeface;
    Context context;
    public SpinnerDialogDaysAdapter(@NonNull Context context, List<GetAllDaysModel> services) {
        super(context, 0, services);
        this.services = services;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GetAllDaysModel serviceListModel = (GetAllDaysModel) getItem(position);
        typeface = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(serviceListModel.getName());
        name.setTypeface(typeface);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        GetAllDaysModel servicesEntity = (GetAllDaysModel) getItem(position);
        typeface = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(servicesEntity.getName());
        name.setTypeface(typeface);
        return convertView;
    }
}
