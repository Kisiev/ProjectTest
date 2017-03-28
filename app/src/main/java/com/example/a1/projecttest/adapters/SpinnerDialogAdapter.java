package com.example.a1.projecttest.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.a1.projecttest.Entities.ChildStatusEntity;
import com.example.a1.projecttest.Entities.ServicesEntity;
import com.example.a1.projecttest.R;

import java.util.List;

import static android.R.attr.category;
import static android.R.attr.thickness;


public class SpinnerDialogAdapter extends ArrayAdapter<ServicesEntity> implements SpinnerAdapter {

    List<ServicesEntity> services;
    public SpinnerDialogAdapter(@NonNull Context context, List<ServicesEntity> services) {
        super(context, 0, services);
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ServicesEntity servicesEntity = (ServicesEntity) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(servicesEntity.getTypeService());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        ServicesEntity servicesEntity = (ServicesEntity) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(servicesEntity.getTypeService());

        return convertView;
    }
}
