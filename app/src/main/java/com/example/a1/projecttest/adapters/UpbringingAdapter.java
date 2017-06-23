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
import com.example.a1.projecttest.rest.Models.GetServiceType;

import java.util.List;

import static ru.yandex.core.CoreApplication.getActivity;


public class UpbringingAdapter extends ArrayAdapter<GetServiceType> implements SpinnerAdapter {

    List<GetServiceType> upbringingEntities;
    Typeface typeface;
    Context context;
    public UpbringingAdapter(@NonNull Context context, List<GetServiceType> upbringingEntities) {
        super(context, 0, upbringingEntities);
        this.upbringingEntities = upbringingEntities;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        typeface = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
        GetServiceType upbringingEntity = (GetServiceType) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(upbringingEntity.getName());
        name.setTypeface(typeface);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        GetServiceType upbringingEntity = (GetServiceType) getItem(position);
        typeface = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(upbringingEntity.getName());
        name.setTypeface(typeface);
        return convertView;
    }
}