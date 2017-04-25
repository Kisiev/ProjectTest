package com.example.a1.projecttest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.rest.Models.GetServiceListModel;
import com.example.a1.projecttest.rest.Models.GetServicesByServiceTypeModel;

import java.util.List;

public class ServiceByServiceTypeAdapter extends ArrayAdapter<GetServicesByServiceTypeModel> implements SpinnerAdapter {
    List<GetServicesByServiceTypeModel> services;
    public ServiceByServiceTypeAdapter(@NonNull Context context, List<GetServicesByServiceTypeModel> services) {
        super(context, 0, services);
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GetServicesByServiceTypeModel serviceListModel = (GetServicesByServiceTypeModel) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(serviceListModel.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        GetServicesByServiceTypeModel servicesEntity = (GetServicesByServiceTypeModel) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(servicesEntity.getName());

        return convertView;
    }
}
