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
import com.example.a1.projecttest.rest.Models.GetServiceType;

import java.util.List;


public class UpbringingAdapter extends ArrayAdapter<GetServiceType> implements SpinnerAdapter {

    List<GetServiceType> upbringingEntities;
    public UpbringingAdapter(@NonNull Context context, List<GetServiceType> upbringingEntities) {
        super(context, 0, upbringingEntities);
        this.upbringingEntities = upbringingEntities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GetServiceType upbringingEntity = (GetServiceType) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(upbringingEntity.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        GetServiceType upbringingEntity = (GetServiceType) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(upbringingEntity.getName());

        return convertView;
    }
}