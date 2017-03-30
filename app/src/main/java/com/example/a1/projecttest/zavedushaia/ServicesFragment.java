package com.example.a1.projecttest.zavedushaia;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.a1.projecttest.Entities.ChildStatusEntity;
import com.example.a1.projecttest.Entities.ChildStatusEntity_Table;
import com.example.a1.projecttest.Entities.ServicesEntity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.adapters.ServiceEditorFragmentAdapter;
import com.example.a1.projecttest.adapters.SpinnerDialogAdapter;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@EFragment(R.layout.services_redaction_fragment)
public class ServicesFragment extends Fragment{
    RecyclerView recyclerView;
    Button addButton;

    DateFormat dfDate_day_time= new SimpleDateFormat("HH:mm");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.services_redaction_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_service);
        addButton = (Button) view.findViewById(R.id.add_serviceBT);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadServices();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }


    @Background
    public void loadServices () {
        getLoaderManager().restartLoader(ConstantsManager.ID_LOADER, null, new LoaderManager.LoaderCallbacks<List<ChildStatusEntity>>() {

            @Override
            public Loader<List<ChildStatusEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<ChildStatusEntity>> loader = new AsyncTaskLoader<List<ChildStatusEntity>>(getActivity()) {
                    @Override
                    public List<ChildStatusEntity> loadInBackground() {
                        return ChildStatusEntity.selectChilds();
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<ChildStatusEntity>> loader, List<ChildStatusEntity> data) {
                VospitannikAdapter adapter = new VospitannikAdapter(data);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<ChildStatusEntity>> loader) {

            }
        });
    }

    public void showDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_service_layout);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        final Button saveServiceButton = (Button) dialog.findViewById(R.id.save_serviceBT);
        final Button cancelServiceButton = (Button) dialog.findViewById(R.id.cancel_serviceBT);
        final Spinner spinner = (Spinner) dialog.findViewById(R.id.type_service_add_SP);

        final EditText editDirect = (EditText) dialog.findViewById(R.id.edit_directET);
        final EditText timeIn = (EditText) dialog.findViewById(R.id.since_edit_ET);
        final EditText timeOut = (EditText) dialog.findViewById(R.id.till_edit_ET);

        final EditText nameServiceEditor = (EditText) dialog.findViewById(R.id.name_service_editorET);
        List childStatusEntities  = new ArrayList<>();
        childStatusEntities.addAll(ServicesEntity.select());

        SpinnerDialogAdapter spinnerDialogAdapter = new SpinnerDialogAdapter(getActivity(),childStatusEntities);

        spinnerDialogAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDialogAdapter.notifyDataSetChanged();
        spinner.setAdapter(spinnerDialogAdapter);

        saveServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    ChildStatusEntity.insert(nameServiceEditor.getText().toString(),
                            VospitannikFragment.getDateString(Integer.valueOf(timeIn.getText().toString()),0,0),
                            VospitannikFragment.getDateString(Integer.valueOf(timeOut.getText().toString()),0,0),
                            spinner.getSelectedItemId(),
                            1,
                            "",
                            VospitannikFragment.generatedColor(),
                            View.VISIBLE);
                    loadServices();
                    dialog.dismiss();

            }
        });

        cancelServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
