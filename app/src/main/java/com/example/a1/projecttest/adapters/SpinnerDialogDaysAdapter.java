package com.example.a1.projecttest.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.a1.projecttest.Entities.DayOfWeek;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetServicesByServiceTypeModel;

import java.util.List;

public class SpinnerDialogDaysAdapter extends ArrayAdapter<DayOfWeek> implements SpinnerAdapter {
    List<DayOfWeek> services;
    public SpinnerDialogDaysAdapter(@NonNull Context context, List<DayOfWeek> services) {
        super(context, 0, services);
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DayOfWeek serviceListModel = (DayOfWeek) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(serviceListModel.getDay());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        DayOfWeek servicesEntity = (DayOfWeek) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(servicesEntity.getDay());

        return convertView;
    }
}