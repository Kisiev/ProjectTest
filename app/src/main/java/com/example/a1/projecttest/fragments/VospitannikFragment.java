package com.example.a1.projecttest.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a1.projecttest.Entities.ChildStatusEntity;
import com.example.a1.projecttest.Entities.ChildStatusEntity_Table;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.RecyclerTouchListener;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;

import java.lang.reflect.Field;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@EFragment(R.layout.vospitanik_fragment)
public class VospitannikFragment extends Fragment {
    RecyclerView recyclerView;
    SimpleDateFormat dfDate_day_time= new SimpleDateFormat("HH:mm");
    public static String getDateString(int hours, int mins, int sec){
        Time time = new Time(hours, mins, sec);
        return String.valueOf(time);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.vospitanik_fragment, container, false);

        SimpleDateFormat dfDate_day= new SimpleDateFormat("E, dd.MM.yyyy, hh:mm");

        final Calendar calendar = Calendar.getInstance();
        ChildStatusEntity.updateVisibility(View.GONE);

        List<String> time = new ArrayList<>();

            time.add(getDateString(7, 0, 0));
            time.add(getDateString(8, 0, 0));
            time.add(getDateString(9, 0, 0));
            time.add(getDateString(10, 0, 0));
            time.add(getDateString(11, 0, 0));
            time.add(getDateString(12, 0, 0));
            time.add(getDateString(13, 0, 0));
            time.add(getDateString(14, 0, 0));
            time.add(getDateString(15, 0, 0));
            time.add(getDateString(16, 0, 0));
            time.add(getDateString(17, 0, 0));
            time.add(getDateString(18, 0, 0));




        List<String> time1 = new ArrayList<>();

            time1.add(getDateString(8, 0, 0));
            time1.add(getDateString(9, 0, 0));
            time1.add(getDateString(10, 0, 0));
            time1.add(getDateString(11, 0, 0));
            time1.add(getDateString(12, 0, 0));
            time1.add(getDateString(13, 0, 0));
            time1.add(getDateString(14, 0, 0));
            time1.add(getDateString(15, 0, 0));
            time1.add(getDateString(16, 0, 0));
            time1.add(getDateString(17, 0, 0));
            time1.add(getDateString(18, 0, 0));
            time1.add(getDateString(19, 0, 0));



        final List<String> listService = new ArrayList<>();
        listService.add("Утренний прием детей на улице");
        listService.add("Утреняя гимнастика");
        listService.add("Свободная деятельность");
        listService.add("Завтрак");
        listService.add("Подготовка к завтраку, завтрак");
        listService.add("Подготовка к прогулке, прогулка");
        listService.add("Гигиенические процедуры");
        listService.add("Подготовка к обеду, обед");
        listService.add("Подготовка ко сну, дневной сон");
        listService.add("Подъем, закаливающие процедуры");
        listService.add("Подготовка к полднику, полдник");
        listService.add("Полдник и домой");

        List<String> coments = new ArrayList<>();
        coments.add("");
        coments.add("");
        coments.add("");
        coments.add("");
        coments.add("");
        coments.add("");
        coments.add("");
        coments.add("");
        coments.add("");
        coments.add("");
        coments.add("");
        coments.add("");

        for (int i = 0; i< 10; i ++) {
            Log.d("COLOR", String.valueOf(generatedColor()));
        }
        TextView date = (TextView) view.findViewById(R.id.date_in_childTV);
        TextView times = (TextView) view.findViewById(R.id.time_in_child);


        if (ChildStatusEntity.selectChilds().size() == 0) {
            for (int i = 0; i < listService.size(); i ++){
                ChildStatusEntity.insert(listService.get(i), time.get(i), time1.get(i), 1, 1, coments.get(i), generatedColor(), View.GONE);
            }
        }



        date.setText(dfDate_day.format(calendar.getTime()));
        times.setText("Время: " + dfDate_day_time.format(calendar.getTime()));
        recyclerView = (RecyclerView) view.findViewById(R.id.vospit_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        loadEntity();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick( View view, final int position) {
                List<ChildStatusEntity> service = ChildStatusEntity.selectChilds();
                view.findViewById(R.id.false_tv).setVisibility(View.VISIBLE);
                SQLite.update(ChildStatusEntity.class)
                        .set(ChildStatusEntity_Table.visible.eq(View.VISIBLE))
                        .where(ChildStatusEntity_Table.serviceName.eq(service.get(position).getServiceName()))
                        .execute();
                service.get(position).setVisible(View.VISIBLE);
                VospitannikAdapter vospitannikAdapter = new VospitannikAdapter(service, getActivity());
                vospitannikAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Button button = (Button) view.findViewById(R.id.child_not_arriveBT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    public static int generatedColor(){
        Random random = new Random();
        int red = 0;
        int green = 0;
        int blue = 0;
        while ((red <= 230) && (green <= 230) && (blue <= 230)) {
            red = random.nextInt(254);
            green = random.nextInt(254);
            blue = random.nextInt(254);
        }

        if (red >= 230){
            green = random.nextInt(230);
            blue = random.nextInt(10);
        }

        if (green >= 230){
            red = random.nextInt(230);
            blue = random.nextInt(10);
        }

        if (blue >= 230){
            red = random.nextInt(200);
            green = random.nextInt(10);
        }
        Integer colors = Color.argb(40 ,red, green, blue);
        return colors;
    }

    @Background
    public void loadEntity() {
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

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public void showDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.reson_child_not_arrive);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        final EditText editText = (EditText) dialog.findViewById(R.id.reson_text_ed);
        Button okButton = (Button) dialog.findViewById(R.id.send_bt);
        final Button cancelButton = (Button) dialog.findViewById(R.id.cancel_bt);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Editable text = editText.getText();

                if (!TextUtils.isEmpty(text)){
                    dialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}

