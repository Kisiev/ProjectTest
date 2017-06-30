package com.example.a1.projecttest.zavedushaia.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.adapters.ServiceByServiceTypeAdapter;
import com.example.a1.projecttest.adapters.SpinnerDialogAdapter;
import com.example.a1.projecttest.rest.Models.GetServiceListModel;
import com.example.a1.projecttest.rest.Models.GetServicesByServiceTypeModel;
import com.example.a1.projecttest.rest.RestService;
import com.mindorks.placeholderview.annotations.Layout;

import org.androidannotations.annotations.EFragment;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment()
public class FoodMenuFragment extends Fragment{
    DatePicker datePicker;
    Dialog dialog;
    Spinner serviceListSpinner;
    ServiceByServiceTypeAdapter serviceByServiceTypeAdapter;
    Observable<List<GetServicesByServiceTypeModel>> getServiceTypesObservable;
    List<GetServicesByServiceTypeModel> getServicesByServiceTypeModelsOn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_menu_fragment, container, false);
        datePicker = (DatePicker) view.findViewById(R.id.calendar_food);
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showDialogFoodMenu();
            }
        });
        return view;
    }

    public void getServiceList(){
        RestService restService = new RestService();
        try {
            getServiceTypesObservable = restService.getServicesByServiceTypeObservableModels("1");
            getServiceTypesObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<GetServicesByServiceTypeModel>>() {
                        @Override
                        public void onCompleted() {
                            serviceListSpinner.setAdapter(serviceByServiceTypeAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<GetServicesByServiceTypeModel> getServicesByServiceTypeModels) {
                            getServicesByServiceTypeModelsOn = getServicesByServiceTypeModels;
                            serviceByServiceTypeAdapter = new ServiceByServiceTypeAdapter(getActivity(), getServicesByServiceTypeModelsOn);
                            serviceByServiceTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDialogFoodMenu(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.food_menu_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        serviceListSpinner = (Spinner) dialog.findViewById(R.id.service_type_spinner_food);
        getServiceList();

        dialog.show();
    }
}
