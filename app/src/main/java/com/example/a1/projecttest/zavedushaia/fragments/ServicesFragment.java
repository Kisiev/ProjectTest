package com.example.a1.projecttest.zavedushaia.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.Entities.CareEntity;
import com.example.a1.projecttest.Entities.ChildStatusEntity;
import com.example.a1.projecttest.Entities.ServiceListEntity;
import com.example.a1.projecttest.Entities.UpbringingEntity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.CareSpinnerAdapter;
import com.example.a1.projecttest.adapters.SpinnerDialogAdapter;
import com.example.a1.projecttest.adapters.UpbringingAdapter;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.rest.Models.GetScheduleListModel;
import com.example.a1.projecttest.rest.Models.GetServiceType;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.RecyclerTouchListener;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@EFragment(R.layout.services_redaction_fragment)
public class ServicesFragment extends Fragment implements Dialog.OnDismissListener, Spinner.OnItemSelectedListener{
    RecyclerView recyclerView;
    Button addButton;
    Dialog dialog;
    List<GetServiceType> getServiceTypeCare;
    List<GetServiceType> getServiceTypeUpbring;
   Thread getServiceListThread;
    List<GetScheduleListModel> getScheduleListModels;
    DateFormat dfDate_day_time= new SimpleDateFormat("HH:mm");

    private void notifyInputError(String error){
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    private List<ChildStatusEntity> updateServiceList(List<ChildStatusEntity> service){
        service.clear();
        service.addAll(ChildStatusEntity.selectChilds());
        return service;
    }

    public void getServiceList(){
        RestService restService = new RestService();
        try {
            getServiceTypeCare = (restService.serviceType(String.valueOf(1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            getServiceTypeUpbring = (restService.serviceType(String.valueOf(2)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            getScheduleListModels = restService.getScheduleListModel("1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.services_redaction_fragment, container, false);
        ChildStatusEntity.updateVisibility(View.GONE);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_service);
        addButton = (Button) view.findViewById(R.id.add_serviceBT);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getServiceListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                getServiceList();
            }
        });
        getServiceListThread.start();
        try {
            getServiceListThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(new VospitannikAdapter(getScheduleListModels, getActivity()));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(false, 0);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                view.findViewById(R.id.edit_button_raspisanie).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(true, position);
                    }
                });
                view.findViewById(R.id.delete_button_raspisanie).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<ChildStatusEntity> service = ChildStatusEntity.selectChilds();
                        service.addAll(updateServiceList(service));
                        ChildStatusEntity.deleteItem(service.get(position).getId());
                        service.addAll(updateServiceList(service));
                        //loadServices();
                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getServiceListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                getServiceList();
            }
        });
        getServiceListThread.start();
        try {
            getServiceListThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UserLoginSession session = new UserLoginSession(getActivity());
        if (session.getStateDialogScreen()){
            showDialog(session.getIsReductionState(), session.getPositionState());
        }

    }



    /*   @Background
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
                VospitannikAdapter adapter = new VospitannikAdapter(data, getActivity());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<ChildStatusEntity>> loader) {

            }
        });
    }*/

    public int positionSpinnerUpbringing(int selectionItem, Spinner upbringingSp, List<GetScheduleListModel> allService, int position){
        List upbringingList  = new ArrayList<>();
        upbringingList.addAll(UpbringingEntity.selectAll());
        List careList  = new ArrayList<>();
        careList.addAll(CareEntity.select());
        int posSpinner = 0;
        switch (selectionItem){
            case 1:
                upbringingSp.setAdapter(new CareSpinnerAdapter(getActivity(), careList));
                for (int i = 0; i < upbringingSp.getCount(); i++) {
                        CareEntity selectionUpbringing = (CareEntity) upbringingSp.getItemAtPosition(i);
                        if (Integer.valueOf(getServiceTypeCare.get(i).getId()) == Integer.valueOf(allService.get(position).getServiceTypeId())) {
                            posSpinner = i;
                            break;
                        }
                    }
                break;
            case 2:
                upbringingSp.setAdapter(new UpbringingAdapter(getActivity(), upbringingList));
                for (int i = 0; i < upbringingSp.getCount(); i++) {
                    UpbringingEntity selectionUpbringing = (UpbringingEntity) upbringingSp.getItemAtPosition(i);
                    if (Integer.valueOf(getServiceTypeUpbring.get(i).getId()) == Integer.valueOf(allService.get(position).getServiceTypeId())) {
                        posSpinner = i;
                        break;
                    }
                }
                break;
        }
        return posSpinner;
    }

    @Background
    public void showDialog(final boolean isReduction, final int position) {

        final List<GetScheduleListModel> allService = new ArrayList<>();
        allService.addAll(getScheduleListModels);
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_service_layout);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        final UserLoginSession session = new UserLoginSession(getActivity());
        dialog.setOnDismissListener(this);
        final TextView headerDialog = (TextView) dialog.findViewById(R.id.header_dialog_menu);
        final Button saveServiceButton = (Button) dialog.findViewById(R.id.save_serviceBT);
        final Button cancelServiceButton = (Button) dialog.findViewById(R.id.cancel_serviceBT);
        final Spinner spinner = (Spinner) dialog.findViewById(R.id.careSp);

        final Spinner upbringingSp = (Spinner) dialog.findViewById(R.id.upbringingSp);
        final EditText timeIn = (EditText) dialog.findViewById(R.id.since_edit_ET);
        final EditText timeOut = (EditText) dialog.findViewById(R.id.till_edit_ET);
        final EditText nameServiceEditor = (EditText) dialog.findViewById(R.id.name_service_editorET);

        if (getServiceTypeCare != null && getServiceTypeUpbring != null) {
            CareEntity.deleteAll();
            UpbringingEntity.deleteAll();
            for (GetServiceType i: getServiceTypeCare) {
                CareEntity.insertCare(i.getName(), Integer.valueOf(i.getServiceListId()));
            }
            for (GetServiceType i: getServiceTypeUpbring) {
                UpbringingEntity.insertItem(i.getName(), Integer.parseInt(i.getServiceListId()));
            }
        }
        final List childStatusEntities  = new ArrayList<>();
        childStatusEntities.addAll(ServiceListEntity.select());

        final List upBringingEntity = new ArrayList<>();
        upBringingEntity.addAll(UpbringingEntity.selectAll());

        SpinnerDialogAdapter spinnerDialogAdapter = new SpinnerDialogAdapter(getActivity(),childStatusEntities);
        UpbringingAdapter upbringingAdapter = new UpbringingAdapter(getActivity(), upBringingEntity);

        spinnerDialogAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        upbringingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDialogAdapter.notifyDataSetChanged();
        upbringingAdapter.notifyDataSetChanged();

        spinner.setAdapter(spinnerDialogAdapter);
        upbringingSp.setAdapter(upbringingAdapter);

        if (session.getStateDialogScreen()){
            nameServiceEditor.setText(session.getSaveEditText(ConstantsManager.EDIT_TEXT_STATE));
            timeIn.setText(session.getSaveEditText(ConstantsManager.TIME_IN));
            timeOut.setText(session.getSaveEditText(ConstantsManager.TIME_OUT));
        }



        textChange(timeIn);
        textChange(timeOut);
        textChange(nameServiceEditor);
        session.saveStateDialogScreen(true, isReduction, position);
        if (isReduction) {
            headerDialog.setText(R.string.edit_header);
          //  nameServiceEditor.setText(allService.get(position).getServiceName());
            nameServiceEditor.setText(getScheduleListModels.get(position).getName());
/*            timeIn.setText(String.valueOf(allService.get(position).getTimeIn() + String.valueOf(allService.get(position).getTimeIn())));
            timeOut.setText(String.valueOf(allService.get(position).getTimeOut() + String.valueOf(allService.get(position).getTimeOut())));*/
            timeIn.setText(String.valueOf(getScheduleListModels.get(position).getTimeFrom()));
            timeOut.setText(String.valueOf(getScheduleListModels.get(position).getTimeTo()));
            spinner.setSelection(Integer.parseInt(allService.get(position).getServiceTypeId()) >= 4 ? 2:1);
            int selectionItem = Integer.valueOf(getScheduleListModels.get(position).getServiceTypeId());
                        //spinner.setSelection(i);
                        upbringingSp.setSelection(positionSpinnerUpbringing(selectionItem >= 4 ? 2:1, upbringingSp, allService, position));

        } else headerDialog.setText(R.string.addActivityTV);

        spinner.setOnItemSelectedListener(this);
        upbringingSp.setOnItemSelectedListener(this);

        saveServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceListEntity typeCare = (ServiceListEntity) spinner.getSelectedItem();
                CareEntity careEntity = new CareEntity();
                UpbringingEntity upbringingEntity = new UpbringingEntity();
                if (((ServiceListEntity) spinner.getSelectedItem()).getId() == 1){
                    careEntity = (CareEntity) upbringingSp.getSelectedItem();
                } else if (((ServiceListEntity) spinner.getSelectedItem()).getId() == 2){
                    upbringingEntity = (UpbringingEntity) upbringingSp.getSelectedItem();
                }
                    if (!nameServiceEditor.getText().toString().equals("")
                            && (!timeIn.getText().toString().equals("")
                            && (!timeIn.getText().toString().contains("м") || (!timeIn.getText().toString().contains("ч")))
                            && !timeOut.getText().toString().equals("")
                            && (!timeOut.getText().toString().contains("м"))||(!timeOut.getText().toString().contains("ч")))
                            && !typeCare.getName().equals("")) {
                            if (Time.valueOf(VospitannikFragment.getDateString(
                                    Integer.valueOf(timeIn.getText().toString().substring(0, 2)),
                                    Integer.valueOf(timeIn.getText().toString().substring(3, 5)), 0)).before(
                                            Time.valueOf(VospitannikFragment.getDateString(
                                    Integer.valueOf(timeOut.getText().toString().substring(0, 2)),
                                    Integer.valueOf(timeOut.getText().toString().substring(3, 5)), 0)))) {
                                if (!isReduction) {
                                    if (ChildStatusEntity.selectIndividualItem(nameServiceEditor.getText().toString()).size() <= 0) {
                                        ChildStatusEntity.insert(nameServiceEditor.getText().toString(),
                                                VospitannikFragment.getDateString(
                                                        Integer.valueOf(timeIn.getText().toString().substring(0, 2)),
                                                        Integer.valueOf(timeIn.getText().toString().substring(3, 5)), 0),
                                                VospitannikFragment.getDateString(
                                                        Integer.valueOf(timeOut.getText().toString().substring(0, 2)),
                                                        Integer.valueOf(timeOut.getText().toString().substring(3, 5)), 0),
                                                typeCare.getId(),
                                                upbringingEntity.getName() != null ? upbringingEntity.getId() : careEntity.getId(),
                                                upbringingSp.getSelectedItemPosition(),
                                                "",
                                                VospitannikFragment.generatedColor(),
                                                0,
                                                View.VISIBLE);
                                    } else notifyInputError(getString(R.string.invalidateNameService));
                                } else {
                                    ChildStatusEntity.updateItem(Integer.parseInt(allService.get(position).getId()), nameServiceEditor.getText().toString(),
                                            VospitannikFragment.getDateString(
                                                    Integer.valueOf(timeIn.getText().toString().substring(0, 2)),
                                                    Integer.valueOf(timeIn.getText().toString().substring(3, 5)), 0),
                                            VospitannikFragment.getDateString(
                                                    Integer.valueOf(timeOut.getText().toString().substring(0, 2)),
                                                    Integer.valueOf(timeOut.getText().toString().substring(3, 5)), 0),
                                            typeCare.getId(),
                                            upbringingEntity.getName() != null ? upbringingEntity.getId() : careEntity.getId(),
                                            upbringingSp.getSelectedItemPosition(),
                                            "",
                                            VospitannikFragment.generatedColor(),
                                            0,
                                            View.VISIBLE);
                                }
                            } else notifyInputError(getString(R.string.invalidateTime));
                        } else notifyInputError(getString(R.string.invalidateNameService));

                //loadServices();
               // dialog.dismiss();

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


    public void textChange(final EditText editText){

        TextWatcher textWatcher = new TextWatcher() {
            private String current = "";
            private String hhmm = "ччмм";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getId() != R.id.name_service_editorET) {
                    if (!s.toString().equals(current)) {
                        String clean = s.toString().replaceAll("[^\\d.]", "");
                        String cleanC = current.replaceAll("[^\\d.]", "");
                        int hour = 0;
                        int mins = 0;
                        int cl = clean.length();
                        int sel = cl;
                        for (int i = 2; i <= cl && i < 6; i += 2) {
                            sel++;
                        }
                        //Fix for pressing delete next to a forward slash
                        if (clean.equals(cleanC)) sel--;

                        if (clean.length() < 4) {
                            clean = clean + hhmm.substring(clean.length());
                        } else {
                            //This part makes sure that when we finish entering numbers
                            //the date is correct, fixing it otherwise
                            hour = Integer.parseInt(clean.substring(0, 2));
                            mins = Integer.parseInt(clean.substring(2, 4));
                            if (hour > 23) {
                                clean = clean.replace(clean.substring(0, 2), "23");
                                hour = 23;
                            }
                            if (mins > 59) {
                                clean = clean.replace(clean.substring(2, 4), "59");
                                mins = 59;
                            }
                            cal.set(Calendar.HOUR, hour);
                            cal.set(Calendar.MINUTE, mins);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            clean = String.format("%02d%02d", hour, mins);
                        }

                        clean = String.format("%s:%s", clean.substring(0, 2),
                                clean.substring(2, 4));

                        sel = sel < 0 ? 0 : sel;
                        current = clean;
                        editText.setText(current);
                        editText.setSelection(sel < current.length() ? sel : current.length());
                        getIdEditText(editText);
                    }
                } else {
                    UserLoginSession session = new UserLoginSession(getActivity());
                    session.saveStateEditText(ConstantsManager.EDIT_TEXT_STATE, editText.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(textWatcher);
    }
    public void getIdEditText(EditText editText){
        UserLoginSession session = new UserLoginSession(getActivity());
        switch (editText.getId()){
            case R.id.since_edit_ET:
                session.saveStateEditText(ConstantsManager.TIME_IN, editText.getText().toString());
                break;
            case R.id.till_edit_ET:
                session.saveStateEditText(ConstantsManager.TIME_OUT, editText.getText().toString());
                break;
        }
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        UserLoginSession session = new UserLoginSession(getActivity());
        session.saveStateDialogScreen(false, false, 0);
        session.saveStateEditText(ConstantsManager.EDIT_TEXT_STATE, "");
        session.saveStateEditText(ConstantsManager.TIME_IN, "");
        session.saveStateEditText(ConstantsManager.TIME_OUT, "");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        UserLoginSession session = new UserLoginSession(getActivity());
        Spinner spinner = (Spinner) dialog.findViewById(R.id.careSp);
        Spinner upBring = (Spinner) dialog.findViewById(R.id.upbringingSp);
        ServiceListEntity serviceListEntity = (ServiceListEntity) spinner.getSelectedItem();
        final List<GetScheduleListModel> allService = new ArrayList<>();
        allService.addAll(getScheduleListModels);
        switch (parent.getId()){
            case R.id.careSp:
                    session.saveStateSpinner(ConstantsManager.SPINNER_STATE, ((ServiceListEntity) parent.getSelectedItem()).getId());
                    switch (serviceListEntity.getId()) {
                        case 1:
                            List careList;
                            careList = (CareEntity.select());
                            CareSpinnerAdapter careSpinnerAdapter = new CareSpinnerAdapter(getActivity(), careList);
                            careSpinnerAdapter.notifyDataSetChanged();
                            upBring.setAdapter(careSpinnerAdapter);
                            if (session.getIsReductionState())
                                upBring.setSelection(positionSpinnerUpbringing(Integer.valueOf(allService.get(session.getPositionState()).getServiceTypeId()) >= 4 ? 2:1, upBring, allService, session.getPositionState()));
                            upBring.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            List upbringingList;
                            upbringingList = (UpbringingEntity.selectAll());
                            UpbringingAdapter upbringingAdapter = new UpbringingAdapter(getActivity(), upbringingList);
                            upbringingAdapter.notifyDataSetChanged();
                            upBring.setAdapter(upbringingAdapter);
                            if (session.getIsReductionState())
                                upBring.setSelection(positionSpinnerUpbringing(Integer.valueOf(allService.get(session.getPositionState()).getServiceTypeId()) >= 4 ? 2:1, upBring, allService, session.getPositionState()));
                            upBring.setVisibility(View.VISIBLE);
                            break;
                        case -1:
                            upBring.setVisibility(View.GONE);
                            break;
                    }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
