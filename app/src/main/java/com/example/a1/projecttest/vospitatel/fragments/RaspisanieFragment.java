package com.example.a1.projecttest.vospitatel.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a1.projecttest.Entities.ChildStatusEntity;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.DialogTutorListChildAdapter;
import com.example.a1.projecttest.adapters.RaspisanieAdapter;
import com.example.a1.projecttest.adapters.RaspisanieGroupItemAdapter;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.rest.Models.GetUserData;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ClickListener;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.RecyclerTouchListener;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@EFragment
public class RaspisanieFragment extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;
    Dialog dialog;
    List<GetUserData> getUserRoleChild;
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            loadUsersByRole();
        }
    });
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.raspisanie_fragment, container, false);
        dialog = new Dialog(getActivity());
        int colorApacity = getResources().getColor(R.color.colorForApacityNull);
        final Calendar calendar = Calendar.getInstance();
        TextView date = (TextView) view.findViewById(R.id.date_in_raspisanieTV);
        TextView times = (TextView) view.findViewById(R.id.time_in_raspisanieTV);
        SimpleDateFormat dfDate_day= new SimpleDateFormat("E, dd.MM.yyyy");
        SimpleDateFormat dfDate_day_time= new SimpleDateFormat("HH:mm");

        thread.start();
        date.setText(dfDate_day.format(calendar.getTime()));
        //times.setText("Время: " + dfDate_day_time.format(calendar.getTime()));
        times.setText("Время: 08:03");
        recyclerView = (RecyclerView) view.findViewById(R.id.raspisanie_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadServicesForTutor();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (!dialog.isShowing())
                    showDialog();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    public void loadUsersByRole(){
        RestService restService = new RestService();
        try {
            getUserRoleChild = restService.getUsersByRole(String.valueOf(3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Background
    public void loadServicesForTutor () {
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

    public void showDialog() {

        dialog.setContentView(R.layout.list_child_for_tutor_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_list_child_for_tutor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new DialogTutorListChildAdapter(getUserRoleChild));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                view.findViewById(R.id.low_smile_image_dialog).setOnClickListener(RaspisanieFragment.this);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.low_smile_image_dialog:

                break;
            case R.id.medium_smile_image_dialog:

                break;
            case R.id.high_smile_image_dialog:

                break;
        }
    }
}
