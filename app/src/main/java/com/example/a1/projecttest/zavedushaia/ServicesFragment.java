package com.example.a1.projecttest.zavedushaia;

import android.app.Dialog;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.Entities.CareEntity;
import com.example.a1.projecttest.Entities.CareEntity_Table;
import com.example.a1.projecttest.Entities.ChildStatusEntity;
import com.example.a1.projecttest.Entities.UpbringingEntity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.SpinnerDialogAdapter;
import com.example.a1.projecttest.adapters.UpbringingAdapter;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.RecyclerTouchListener;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Nonnull;


@EFragment(R.layout.services_redaction_fragment)
public class ServicesFragment extends Fragment{
    RecyclerView recyclerView;
    Button addButton;
    ViewPager viewPager;
    TabHost tabHost;
    DateFormat dfDate_day_time= new SimpleDateFormat("HH:mm");

    private void notifyInputError(String error){
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    private List<ChildStatusEntity> updateServiceList(List<ChildStatusEntity> service){
        service.clear();
        service.addAll(ChildStatusEntity.selectChilds());
        return service;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.services_redaction_fragment, container, false);
        ChildStatusEntity.updateVisibility(View.GONE);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_service);
        addButton = (Button) view.findViewById(R.id.add_serviceBT);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadServices();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(false, 0);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(final List<ChildStatusEntity> service, View view, final int position) {
                view.findViewById(R.id.edit_button_raspisanie).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(true, position);
                        Toast.makeText(getActivity(), "Кнопка Реадактор " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                view.findViewById(R.id.delete_button_raspisanie).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        service.addAll(updateServiceList(service));
                        ChildStatusEntity.deleteItem(service.get(position).getId());
                        service.addAll(updateServiceList(service));
                        loadServices();
                        Toast.makeText(getActivity(), "Кнопка Удалить " + position, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }, ChildStatusEntity.selectChilds()));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserLoginSession session = new UserLoginSession(getActivity());
        if (session.getStateDialogScreen()){
            showDialog(session.getIsReductionState(), session.getPositionState());
        }
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
                VospitannikAdapter adapter = new VospitannikAdapter(data, getActivity());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<ChildStatusEntity>> loader) {

            }
        });
    }

    public void showDialog(final boolean isReduction, final int position) {

        final List<ChildStatusEntity> allService = new ArrayList<>();
        allService.addAll(ChildStatusEntity.selectChilds());
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_service_layout);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        final UserLoginSession session = new UserLoginSession(getActivity());
        session.saveStateDialogScreen(true, isReduction, position);
        final TextView headerDialog = (TextView) dialog.findViewById(R.id.header_dialog_menu);
        final Button saveServiceButton = (Button) dialog.findViewById(R.id.save_serviceBT);
        final Button cancelServiceButton = (Button) dialog.findViewById(R.id.cancel_serviceBT);
        final Spinner spinner = (Spinner) dialog.findViewById(R.id.careSp);

        final Spinner upbringingSp = (Spinner) dialog.findViewById(R.id.upbringingSp);
        final EditText timeIn = (EditText) dialog.findViewById(R.id.since_edit_ET);
        final EditText timeOut = (EditText) dialog.findViewById(R.id.till_edit_ET);

        textChange(timeIn);
        textChange(timeOut);

        final EditText nameServiceEditor = (EditText) dialog.findViewById(R.id.name_service_editorET);
        final List childStatusEntities  = new ArrayList<>();
        childStatusEntities.addAll(CareEntity.select());

        final List upBringingEntity = new ArrayList<>();
        upBringingEntity.addAll(UpbringingEntity.select());

        SpinnerDialogAdapter spinnerDialogAdapter = new SpinnerDialogAdapter(getActivity(),childStatusEntities);
        final UpbringingAdapter upbringingAdapter = new UpbringingAdapter(getActivity(), upBringingEntity);

        spinnerDialogAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        upbringingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDialogAdapter.notifyDataSetChanged();
        upbringingAdapter.notifyDataSetChanged();

        spinner.setAdapter(spinnerDialogAdapter);
        upbringingSp.setAdapter(upbringingAdapter);

        if (isReduction) {
            headerDialog.setText(R.string.edit_header);
            nameServiceEditor.setText(allService.get(position).getServiceName());
            timeIn.setText(String.valueOf(allService.get(position).getTimeIn() + String.valueOf(allService.get(position).getTimeIn())));
            timeOut.setText(String.valueOf(allService.get(position).getTimeOut() + String.valueOf(allService.get(position).getTimeOut())));
            spinner.setSelection(allService.get(position).getTypeService());
            for (int i = 0; i < spinner.getCount(); i ++){
                CareEntity selectionItem = (CareEntity) spinner.getItemAtPosition(i);
                if (selectionItem.getId() == allService.get(position).getTypeService()) {
                    spinner.setSelection(i);
                    break;
                }
            }
            for (int i = 0; i < upbringingSp.getCount(); i ++){
                UpbringingEntity selectionUpbringing =  (UpbringingEntity) upbringingSp.getItemAtPosition(i);
                if (selectionUpbringing.getId() == allService.get(position).getTypeUpbringing()) {
                    upbringingSp.setSelection(i);
                    break;
                }
            }

        } else headerDialog.setText(R.string.addActivityTV);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CareEntity careEntity = (CareEntity) parent.getSelectedItem();
                upbringingSp.setVisibility(careEntity.getId() == (CareEntity.selectCreationCare(getActivity()).getId())
                        ? View.VISIBLE
                        :View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CareEntity typeCare = (CareEntity) spinner.getSelectedItem();
                UpbringingEntity upbringingEntity = (UpbringingEntity) upbringingSp.getSelectedItem();
                    if (!nameServiceEditor.getText().toString().isEmpty()
                            && !timeIn.getText().toString().contains("ч")
                            && !timeIn.getText().toString().contains("м")
                            && !timeOut.getText().toString().contains("ч")
                            && !timeOut.getText().toString().contains("м")
                            && !typeCare.getNameCare().isEmpty()) {
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
                                                typeCare.getId() == CareEntity.selectCreationCare(getActivity()).getId() ? upbringingEntity.getId() : -1,
                                                "",
                                                VospitannikFragment.generatedColor(),
                                                View.VISIBLE);
                                    } else notifyInputError(getString(R.string.invalidateNameService));
                                } else {
                                    ChildStatusEntity.updateItem(allService.get(position).getId(), nameServiceEditor.getText().toString(),
                                            VospitannikFragment.getDateString(
                                                    Integer.valueOf(timeIn.getText().toString().substring(0, 2)),
                                                    Integer.valueOf(timeIn.getText().toString().substring(3, 5)), 0),
                                            VospitannikFragment.getDateString(
                                                    Integer.valueOf(timeOut.getText().toString().substring(0, 2)),
                                                    Integer.valueOf(timeOut.getText().toString().substring(3, 5)), 0),
                                            typeCare.getId(),
                                            typeCare.getId() == CareEntity.selectCreationCare(getActivity()).getId() ? upbringingEntity.getId() : -1,
                                            "",
                                            VospitannikFragment.generatedColor(),
                                            View.VISIBLE);
                                }
                            } else notifyInputError(getString(R.string.invalidateTime));
                        } else notifyInputError(getString(R.string.invalidateNameService));

                loadServices();
               // dialog.dismiss();
            }
        });

        cancelServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                session.saveStateDialogScreen(false, false, 0);
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

                    if (clean.length() < 4){
                        clean = clean + hhmm.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        hour  = Integer.parseInt(clean.substring(0,2));
                        mins  = Integer.parseInt(clean.substring(2,4));
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

                        clean = String.format("%02d%02d",hour, mins);
                    }

                    clean = String.format("%s:%s", clean.substring(0, 2),
                            clean.substring(2, 4));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editText.setText(current);
                    editText.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(textWatcher);
    }

}
