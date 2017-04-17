package com.example.a1.projecttest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.a1.projecttest.Entities.CareEntity;
import com.example.a1.projecttest.Entities.ServiceListEntity;
import com.example.a1.projecttest.R;

import java.util.List;


public class SpinnerDialogAdapter extends ArrayAdapter<ServiceListEntity> implements SpinnerAdapter {

    List<ServiceListEntity> services;
    public SpinnerDialogAdapter(@NonNull Context context, List<ServiceListEntity> services) {
        super(context, 0, services);
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ServiceListEntity servicesEntity = (ServiceListEntity) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(servicesEntity.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        ServiceListEntity servicesEntity = (ServiceListEntity) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(servicesEntity.getName());

        return convertView;
    }
}
