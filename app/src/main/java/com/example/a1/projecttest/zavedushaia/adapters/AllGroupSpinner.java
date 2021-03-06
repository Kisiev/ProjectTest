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
import com.example.a1.projecttest.rest.Models.GetKinderGartenGroup;
import com.example.a1.projecttest.rest.Models.GetKinderGartensByCityCode;

import java.util.List;

/**
 * Created by User on 10.05.2017.
 */

    public class AllGroupSpinner extends ArrayAdapter<GetKinderGartenGroup> implements SpinnerAdapter {
        List<GetKinderGartenGroup> getKinderGartensByCityCodes;
        Context context;
        Typeface typeface;
        public AllGroupSpinner(@NonNull Context context, List<GetKinderGartenGroup> getKinderGartensByCityCodes) {
            super(context, 0, getKinderGartensByCityCodes);
            this.getKinderGartensByCityCodes = getKinderGartensByCityCodes;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            GetKinderGartenGroup serviceListModel = (GetKinderGartenGroup) getItem(position);
            typeface = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
            name.setTextColor(context.getResources().getColor(R.color.whiteColor));
            name.setText(serviceListModel.getGroupName());
            name.setTextColor(context.getResources().getColor(R.color.black));
            name.setTypeface(typeface);
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            GetKinderGartenGroup servicesEntity = (GetKinderGartenGroup) getItem(position);
            typeface = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Text-Regular.ttf");
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name_serviceItemTV);
            name.setText(servicesEntity.getGroupName());
            name.setTextColor(context.getResources().getColor(R.color.black));
            name.setTypeface(typeface);
            return convertView;
        }
    }

