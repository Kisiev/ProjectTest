package com.example.a1.projecttest.zavedushaia.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.example.a1.projecttest.PositionSaveSession;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.ServiceByServiceTypeAdapter;
import com.example.a1.projecttest.adapters.SpinnerDialogAdapter;
import com.example.a1.projecttest.adapters.SpinnerDialogDaysAdapter;
import com.example.a1.projecttest.adapters.UpbringingAdapter;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.rest.Models.GetAllDaysModel;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.Models.GetKinderGartenGroup;
import com.example.a1.projecttest.rest.Models.GetScheduleListModel;
import com.example.a1.projecttest.rest.Models.GetServiceListModel;
import com.example.a1.projecttest.rest.Models.GetServiceType;
import com.example.a1.projecttest.rest.Models.GetServicesByServiceTypeModel;
import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.RecyclerTouchListener;
import com.example.a1.projecttest.zavedushaia.adapters.AllGroupSpinner;
import com.example.a1.projecttest.zavedushaia.adapters.DayOfWeekAdapter;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static ru.yandex.core.CoreApplication.getActivity;


@EFragment(R.layout.services_redaction_fragment)
public class ServicesFragment extends Fragment implements Dialog.OnDismissListener, Spinner.OnItemSelectedListener {
    RecyclerView recyclerView;
    Button addButton;
    Dialog dialog;
    GetStatusCode setScheduleStatus;
    List<GetServicesByServiceTypeModel> getServicesByServiceTypeModels;
    List<GetServiceListModel> getServiceListModels;
    List<GetServiceType> getServiceTypes;
    GetStatusCode statusCodeUpdate;
    GetStatusCode statusCodeDelete;
    Thread getServiceListThread;
    List<GetKinderGartenGroup> getKinderGartenGroups;
    Typeface typeface;
    Spinner allGroupsSpinner;
    GetKinderGarten getKinderGarten;
    List<GetAllDaysModel> getAllDaysModels;
    List<GetScheduleListModel> getScheduleListModels;
    Spinner dayOfWeekSpinner;
    DateFormat dfDate_day_time= new SimpleDateFormat("HH:mm");
    private void notifyInputError(String error){
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    private int findNameServicePosition(int position){
        int positionService = 0;
        for (int i = 0; i < getServicesByServiceTypeModels.size(); i ++){
            if (getServicesByServiceTypeModels.get(i).getId().equals(getScheduleListModels.get(position).getServiceId())){
                positionService = i;
                break;
            }
        }
        return positionService;
    }
    private int findServiceListModels(int position){
        int positionService = 0;
        for (int i = 0; i < getServiceListModels.size(); i ++){
            if (getScheduleListModels.get(position).getServiceListId().equals(getServiceListModels.get(i).getId())){
                positionService = i;
                break;
            }
        }
        return positionService;
    }
    private int findServiceTypes(int position){
        int positionService = 0;

        for (int i = 0; i < getServiceTypes.size(); i ++) {
            if (getScheduleListModels.get(position).getServiceTypeId().equals(getServiceTypes.get(i).getId())) {
                positionService = i;
                break;
            }
        }

        return positionService;
    }
    private int findDayWeek(int position){
        int positionService = 0;
        for (int i = 0; i < getAllDaysModels.size(); i ++){
            if (((GetAllDaysModel)(dayOfWeekSpinner.getSelectedItem())).getName().equals(getAllDaysModels.get(i).getName())){
                positionService = i;
                break;
            }
        }
        return positionService;
    }

    public void threadGetGroup(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getGroupByKinderGart();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getGroupByKinderGart(){
        RestService restService = new RestService();
        UserLoginSession userLoginSession = new UserLoginSession(getActivity());
        try {
            getKinderGarten = restService.getKinderGartenZav(userLoginSession.getID());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            getKinderGartenGroups = restService.getKinderGartenGroups(getKinderGarten.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void threadGetAllDay(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getAllDaysFunc();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getAllDaysFunc(){
        RestService  restService =  new RestService();
        try {
            getAllDaysModels = restService.getAllDaysModels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void threadServiceList(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getServiceList();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getServiceList(){
        RestService restService = new RestService();

        try {
            getScheduleListModels = restService.getScheduleListModel(((GetKinderGartenGroup)allGroupsSpinner.getSelectedItem()).getGroupId(),   ((GetAllDaysModel) dayOfWeekSpinner.getSelectedItem()).getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            getServiceListModels = restService.getServiceList();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void getServiceTypeById(String id){
        RestService restService = new RestService();
        try {
            getServiceTypes = (restService.serviceType(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getServicesByServiceType(String id){
        RestService restService = new RestService();
        try {
            getServicesByServiceTypeModels = restService.getServicesByServiceTypeModels(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getStatusOfDeleteSchedule(String id){
        RestService restService = new RestService();
        try {
            statusCodeDelete = restService.deleteScheduleById(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getServiceList();
    }

    public void deleteSchedule(final String id){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getStatusOfDeleteSchedule(id);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.services_redaction_fragment, container, false);
        threadGetGroup();
        threadGetAllDay();
        dayOfWeekSpinner = (Spinner) view.findViewById(R.id.day_of_week_spinner);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_service);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DayOfWeekAdapter dayOfWeekAdapter = new DayOfWeekAdapter(getActivity(), getAllDaysModels);
        dayOfWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWeekSpinner.setAdapter(dayOfWeekAdapter);
        dayOfWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                threadServiceList();
                recyclerView.setAdapter(new VospitannikAdapter(getScheduleListModels, null, getActivity()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        allGroupsSpinner = (Spinner) view.findViewById(R.id.all_group_of_kindergarten);
        AllGroupSpinner groupsAdapter = new AllGroupSpinner(getActivity(), getKinderGartenGroups);
        groupsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allGroupsSpinner.setAdapter(groupsAdapter);
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

        addButton = (Button) view.findViewById(R.id.add_serviceBT);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/opensans.ttf");
        addButton.setTypeface(typeface);


        threadServiceList();

        allGroupsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                threadServiceList();
                recyclerView.setAdapter(new VospitannikAdapter(getScheduleListModels, null, getActivity()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recyclerView.setAdapter(new VospitannikAdapter(getScheduleListModels, null, getActivity()));
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
                        deleteSchedule(getScheduleListModels.get(position).getId());
                        recyclerView.setAdapter(new VospitannikAdapter(getScheduleListModels, null, getActivity()));
                        if (statusCodeDelete != null) {
                            if (statusCodeDelete.getCode().equals("200")) {
                                Toast.makeText(getActivity(), R.string.delete_item_status, Toast.LENGTH_SHORT).show();
                            }
                        }else Toast.makeText(getActivity(), R.string.status_error_delete_item, Toast.LENGTH_SHORT).show();
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



    @Background
    public void showDialog(final boolean isReduction, final int position) {

        PositionSaveSession positionSaveSession = new PositionSaveSession(getActivity());
        positionSaveSession.saveIsReductionState(isReduction);

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
        final Spinner nameServiceEditor = (Spinner) dialog.findViewById(R.id.name_service_editorET);
        final Spinner daysSpinner = (Spinner) dialog.findViewById(R.id.days_spinner);
        headerDialog.setTypeface(typeface);
        saveServiceButton.setTypeface(typeface);
        cancelServiceButton.setTypeface(typeface);
        timeIn.setTypeface(typeface);
        timeOut.setTypeface(typeface);
        List serviceTypeList = new ArrayList<>();
        serviceTypeList.addAll(getServiceListModels);

        SpinnerDialogDaysAdapter adapter = new SpinnerDialogDaysAdapter(getActivity(), getAllDaysModels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysSpinner.setAdapter(adapter);

        SpinnerDialogAdapter spinnerDialogAdapter = new SpinnerDialogAdapter(getActivity(),serviceTypeList);
        spinnerDialogAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDialogAdapter.notifyDataSetChanged();
        spinner.setAdapter(spinnerDialogAdapter);

        if (session.getStateDialogScreen()){
            timeIn.setText(session.getSaveEditText(ConstantsManager.TIME_IN));
            timeOut.setText(session.getSaveEditText(ConstantsManager.TIME_OUT));
        }

        textChange(timeIn);
        textChange(timeOut);
        if (isReduction){
            headerDialog.setText(R.string.edit);
            PositionSaveSession saveSession = new PositionSaveSession(getActivity());
            saveSession.savePositionSession(position);
            spinner.setSelection(findServiceListModels(position));
            daysSpinner.setSelection(findDayWeek(position));
            timeIn.setText(getScheduleListModels.get(position).getTimeFrom());
            timeOut.setText(getScheduleListModels.get(position).getTimeTo());
        }
        spinner.setOnItemSelectedListener(this);
        upbringingSp.setOnItemSelectedListener(this);

        saveServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetServiceListModel getServiceListModel = (GetServiceListModel) spinner.getSelectedItem();
                GetServiceType getServiceType = (GetServiceType) upbringingSp.getSelectedItem();
                GetAllDaysModel dayOfWeek = (GetAllDaysModel) daysSpinner.getSelectedItem();
                GetServicesByServiceTypeModel getServicesByServiceTypeModel = (GetServicesByServiceTypeModel) nameServiceEditor.getSelectedItem();
                if ((getServiceListModel != null)
                        && (getServiceType != null)
                        && (dayOfWeek.getName() != null)
                        && (getServicesByServiceTypeModel != null)
                        && ((!timeIn.getText().toString().contains("ч")) && (!timeIn.getText().toString().contains("м")) && (!timeIn.getText().toString().isEmpty()))
                        && ((!timeOut.getText().toString().contains("ч")) && (!timeOut.getText().toString().contains("м")) && (!timeOut.getText().toString().isEmpty()))){
                    if (Time.valueOf(VospitannikFragment.getDateString(
                            Integer.valueOf(timeIn.getText().toString().substring(0, 2)),
                            Integer.valueOf(timeIn.getText().toString().substring(3, 5)), 0)).before(
                            Time.valueOf(VospitannikFragment.getDateString(
                                    Integer.valueOf(timeOut.getText().toString().substring(0, 2)),
                                    Integer.valueOf(timeOut.getText().toString().substring(3, 5)), 0)))) {
                        if (!isReduction) {
                            createScheduleThread(getServicesByServiceTypeModel.getId(), dayOfWeek.getId(), timeIn.getText().toString(), timeOut.getText().toString(), ((GetKinderGartenGroup)allGroupsSpinner.getSelectedItem()).getGroupId());
                            recyclerView.setAdapter(new VospitannikAdapter(getScheduleListModels, null, getActivity()));
                            if (setScheduleStatus.getCode().equals("200"))
                                Toast.makeText(getActivity(), "Успешно", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getActivity(), "Ошибка добавления", Toast.LENGTH_SHORT).show();
                        } else if (isReduction){
                            updateSchedule(getScheduleListModels.get(position).getId(), getServicesByServiceTypeModel.getId(), dayOfWeek.getId(), timeIn.getText().toString(), timeOut.getText().toString(), ((GetKinderGartenGroup)allGroupsSpinner.getSelectedItem()).getGroupId());
                            recyclerView.setAdapter(new VospitannikAdapter(getScheduleListModels, null, getActivity()));
                            if (statusCodeUpdate.getCode().equals("200")){
                                Toast.makeText(getActivity(), "Успешно", Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(getActivity(), "Ошибка добавления", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
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
    public void updateScheduleBy(String id, String serviceId, String day, String timeFrom, String timeTo, String groupId){
        RestService restService = new RestService();
        try {
            statusCodeUpdate = restService.updateSchedule(id, serviceId, day, timeFrom, timeTo, groupId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getServiceList();
    }
    public void setSchedule(String serviceId, String day, String timeFrom, String timeTo, String groupId){
        RestService restService = new RestService();
        try {
            setScheduleStatus = restService.setSchedule(serviceId, day, timeFrom, timeTo, groupId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getServiceList();
    }

    public void updateSchedule(final String id, final String serviceId, final String day, final String timeFrom, final String timeTo, final String groupId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                updateScheduleBy(id, serviceId, day, timeFrom, timeTo, groupId);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createScheduleThread(final String serviceId, final String day, final String timeFrom, final String timeTo, final String groupId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                setSchedule(serviceId, day, timeFrom, timeTo, groupId);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        PositionSaveSession saveSession = new PositionSaveSession(getActivity());
        saveSession.saveIsReductionState(false);
        saveSession.savePositionSession(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            PositionSaveSession session = new PositionSaveSession(getActivity());
            Spinner spinner = (Spinner) dialog.findViewById(R.id.careSp);
            Spinner upBring = (Spinner) dialog.findViewById(R.id.upbringingSp);
            Spinner nameServiceSpinner = (Spinner) dialog.findViewById(R.id.name_service_editorET);
            GetServiceListModel serviceListEntity = (GetServiceListModel) spinner.getSelectedItem();
        final List<GetScheduleListModel> allService = new ArrayList<>();
        allService.addAll(getScheduleListModels);
        switch (parent.getId()){
            case R.id.careSp:
                    switch (serviceListEntity.getId()) {
                        case "1":
                            Thread threadByOne = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getServiceTypeById("1");
                                }
                            });
                            threadByOne.start();
                            try {
                                threadByOne.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            List serviceTypes = new ArrayList<>();
                            serviceTypes.addAll(getServiceTypes);
                            UpbringingAdapter careSpinnerAdapter = new UpbringingAdapter(getActivity(), serviceTypes);
                            careSpinnerAdapter.notifyDataSetChanged();
                            upBring.setAdapter(careSpinnerAdapter);
                            if (session.isReductionState())
                                upBring.setSelection(findServiceTypes(session.getPositionSession()));
                            upBring.setVisibility(View.VISIBLE);
                            break;
                        case "2":
                            Thread threadByTwo = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getServiceTypeById("2");
                                }
                            });
                            threadByTwo.start();
                            try {
                                threadByTwo.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            List serviceTypesByTwo = new ArrayList<>();
                            serviceTypesByTwo.addAll(getServiceTypes);
                            UpbringingAdapter upbringingAdapter = new UpbringingAdapter(getActivity(), serviceTypesByTwo);
                            upbringingAdapter.notifyDataSetChanged();
                            upBring.setAdapter(upbringingAdapter);
                            if (session.isReductionState())
                                upBring.setSelection(findServiceTypes(session.getPositionSession()));
                            upBring.setVisibility(View.VISIBLE);
                            break;

                    }

                break;
            case R.id.upbringingSp:
                GetServiceType getServiceType = (GetServiceType) upBring.getSelectedItem();
                switch (getServiceType.getId()){
                    case "1":
                        beginThreadGetServices("1");
                        nameServiceSpinner.setAdapter(new ServiceByServiceTypeAdapter(getActivity(), getServicesByServiceTypeModels));
                        if (session.isReductionState())
                            nameServiceSpinner.setSelection(findNameServicePosition(session.getPositionSession()));
                        break;
                    case "2":
                        beginThreadGetServices("2");
                        nameServiceSpinner.setAdapter(new ServiceByServiceTypeAdapter(getActivity(), getServicesByServiceTypeModels));
                        if (session.isReductionState())
                            nameServiceSpinner.setSelection(findNameServicePosition(session.getPositionSession()));
                        break;
                    case "3":
                        beginThreadGetServices("3");
                        nameServiceSpinner.setAdapter(new ServiceByServiceTypeAdapter(getActivity(), getServicesByServiceTypeModels));
                        if (session.isReductionState())
                            nameServiceSpinner.setSelection(findNameServicePosition(session.getPositionSession()));
                        break;
                    case "4":
                        beginThreadGetServices("4");
                        nameServiceSpinner.setAdapter(new ServiceByServiceTypeAdapter(getActivity(), getServicesByServiceTypeModels));
                        if (session.isReductionState())
                            nameServiceSpinner.setSelection(findNameServicePosition(session.getPositionSession()));
                        break;
                    case "5":
                        beginThreadGetServices("5");
                        nameServiceSpinner.setAdapter(new ServiceByServiceTypeAdapter(getActivity(), getServicesByServiceTypeModels));
                        if (session.isReductionState())
                            nameServiceSpinner.setSelection(findNameServicePosition(session.getPositionSession()));
                        break;
                    case "6":
                        beginThreadGetServices("6");
                        nameServiceSpinner.setAdapter(new ServiceByServiceTypeAdapter(getActivity(), getServicesByServiceTypeModels));
                        if (session.isReductionState())
                            nameServiceSpinner.setSelection(findNameServicePosition(session.getPositionSession()));
                        break;
                    case "7":
                        beginThreadGetServices("7");
                        nameServiceSpinner.setAdapter(new ServiceByServiceTypeAdapter(getActivity(), getServicesByServiceTypeModels));
                        if (session.isReductionState())
                            nameServiceSpinner.setSelection(findNameServicePosition(session.getPositionSession()));
                        break;
                }
                break;
        }

    }

    public void beginThreadGetServices(final String id){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getServicesByServiceType(id);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
