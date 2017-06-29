package com.example.a1.projecttest.zavedushaia.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.R;

import org.androidannotations.annotations.EFragment;

import java.util.Calendar;

@EFragment(R.layout.event_zav_fragment)
public class EventFragmentZav extends Fragment {
    DatePicker datePicker;
    Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_zav_fragment, container, false);
        datePicker = (DatePicker) view.findViewById(R.id.calendar_event_zav);
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(getActivity(), "События дня" + " " + dayOfMonth +" - "+ monthOfYear + " - " + year, Toast.LENGTH_SHORT).show();
                showDialogAddEvent();
            }
        });
        return view;
    }
    public void showDialogAddEvent(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.create_event_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;


        dialog.show();
    }
}
