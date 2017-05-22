package com.example.a1.projecttest.zavedushaia.adapters;

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
import com.example.a1.projecttest.rest.Models.GetKinderGartenGroup;

import java.util.List;

public class DayOfWeekAdapter  extends ArrayAdapter<GetAllDaysModel> implements SpinnerAdapter {
    List<GetAllDaysModel> getKinderGartensByCityCodes;
    Context context;
    Typeface typeface;
    public DayOfWeekAdapter(@NonNull Context context, List<GetAllDaysModel> getKinderGartensByCityCodes) {
        super(context, 0, getKinderGartensByCityCodes);
        this.getKinderGartensByCityCodes = getKinderGartensByCityCodes;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GetAllDaysModel serviceListModel = (GetAllDaysModel) getItem(position);
        typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setTextColor(context.getResources().getColor(R.color.whiteColor));
        name.setText(serviceListModel.getName());
        name.setTextColor(context.getResources().getColor(R.color.black));
        name.setTypeface(typeface);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        GetAllDaysModel servicesEntity = (GetAllDaysModel) getItem(position);
        typeface = Typeface.createFromAsset(context.getAssets(), "font/opensans.ttf");
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
        name.setText(servicesEntity.getName());
        name.setTextColor(context.getResources().getColor(R.color.black));
        name.setTypeface(typeface);
        return convertView;
    }
}
