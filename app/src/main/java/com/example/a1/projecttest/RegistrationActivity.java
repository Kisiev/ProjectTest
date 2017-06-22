package com.example.a1.projecttest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.a1.projecttest.rest.Models.GetAllRegionsModel;
import com.example.a1.projecttest.rest.Models.GetKinderGarten;
import com.example.a1.projecttest.rest.Models.GetKinderGartensByCityCode;
import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.vospitatel.VospitatelMainActivity;
import com.example.a1.projecttest.vospitatel.VospitatelMainActivity_;
import com.example.a1.projecttest.zavedushaia.MainZavDetSad;
import com.example.a1.projecttest.zavedushaia.MainZavDetSad_;
import com.example.a1.projecttest.zavedushaia.adapters.SpinnerKinderGartenadapter;
import com.example.a1.projecttest.zavedushaia.adapters.SpinnerRegionsAndCitiesAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;
import java.util.List;

@EActivity(R.layout.registration_activity)
public class RegistrationActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    RadioButton childRb;
    RadioButton zavRb;
    RadioButton vospitRb;
    RadioButton parentRb;
    RadioButton contactRb;
    Button registrationButton;
    GetStatusCode getStatusCode;
    UserLoginSession userLoginSession;
    EditText userName;
    EditText userSurname;
    EditText patronumicUser;
    Spinner regionSpinner;
    Spinner citySpinner;
    Spinner kinderGartenSpinner;
    List<GetAllRegionsModel> getAllRegions;
    List<GetAllRegionsModel> getAllCities;
    SpinnerRegionsAndCitiesAdapter adapter;
    List<GetKinderGartensByCityCode> getKinderGartensByCityCodes;
    int roleId = 0;
    private void setSession(){
        userLoginSession.setUseName(userLoginSession.getLogin(),
                userLoginSession.getPassword(),
                userLoginSession.getID(),
                userName.getText().toString(),
                userSurname.getText().toString(),
                patronumicUser.getText().toString(),
                roleId, 1);
    }

    public void getCitiesByRegionId(){
        RestService restService = new RestService();
        try {
            getAllCities = restService.getAllcitiesByRegion(((GetAllRegionsModel)(regionSpinner.getSelectedItem())).getCode().substring(0, 2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getRegions(){
        RestService restService = new RestService();
        try {
            getAllRegions = restService.getAllRegionsModels();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getKinderGartenByManager(){
        RestService restService = new RestService();
        try {
            getKinderGartensByCityCodes = restService.getKinderGartensByCityCodes(((GetAllRegionsModel)(citySpinner.getSelectedItem())).getCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUserData(){
        RestService restService = new RestService();
        if (childRb.isChecked()){
            roleId = 3;
        } else if (parentRb.isChecked()){
            roleId = 1;
        } else if (zavRb.isChecked()){
            roleId = 2;
        } else if (contactRb.isChecked()){
            roleId = 4;
        } else if (vospitRb.isChecked()){
            roleId = 5;
        }
        try {
            getStatusCode = restService.getStatusCode(userLoginSession.getID(),
                    patronumicUser.getText().toString(),
                    userSurname.getText().toString(),
                    userName.getText().toString(),
                    String.valueOf(roleId),
                    roleId == 2 ?((GetKinderGartensByCityCode)kinderGartenSpinner.getSelectedItem()).getId():"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void threadBegin(final String get){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                switch (get){
                    case "region":
                        getRegions();
                        break;
                    case "city":
                        getCitiesByRegionId();
                        break;
                    case "kinder":
                        getKinderGartenByManager();
                        break;
                }

            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterViews
    public void main () {
        LoginActivity loginActivity = new LoginActivity();
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/opensans.ttf");
        ImageView imageView = (ImageView) findViewById(R.id.reg_imageView);
        loginActivity.createImage(R.mipmap.backgroundlogin, imageView);
        TextView header = (TextView) findViewById(R.id.header_registration);
        regionSpinner = (Spinner) findViewById(R.id.region_spinner_registration);
        citySpinner = (Spinner) findViewById(R.id.city_spinner_registration);
        kinderGartenSpinner = (Spinner) findViewById(R.id.kinder_garten_spinner_registration);

        regionSpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);
        kinderGartenSpinner.setOnItemSelectedListener(this);
        threadBegin("region");
        adapter = new SpinnerRegionsAndCitiesAdapter(this, getAllRegions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter);
        userLoginSession = new UserLoginSession(getApplicationContext());

        userName = (EditText) findViewById(R.id.name_user_edit);
        userSurname = (EditText) findViewById(R.id.surname_user_edit);
        patronumicUser = (EditText) findViewById(R.id.lastname_user_edit);

        childRb = (RadioButton) findViewById(R.id.child_RB);
        zavRb = (RadioButton) findViewById(R.id.zavDetSadRB);
        vospitRb = (RadioButton) findViewById(R.id.vospitatel);
        parentRb = (RadioButton) findViewById(R.id.parent_RB);
        contactRb = (RadioButton) findViewById(R.id.contact_face_RB);

        registrationButton = (Button) findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(this);
        userName.setTypeface(typeface);
        userSurname.setTypeface(typeface);
        patronumicUser.setTypeface(typeface);
        childRb.setTypeface(typeface);
        zavRb.setTypeface(typeface);
        vospitRb.setTypeface(typeface);
        parentRb.setTypeface(typeface);
        contactRb.setTypeface(typeface);
        registrationButton.setTypeface(typeface);
        header.setTypeface(typeface);

        zavRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (zavRb.isChecked()){
                    regionSpinner.setVisibility(View.VISIBLE);
                    citySpinner.setVisibility(View.VISIBLE);
                    kinderGartenSpinner.setVisibility(View.VISIBLE);
                } else {
                    regionSpinner.setVisibility(View.GONE);
                    citySpinner.setVisibility(View.GONE);
                    kinderGartenSpinner.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.registration_button:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setUserData();
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getStatusCode.getCode().equals("200")) {
                    setSession();
                    startActivity(new Intent(this, LoginActivity_.class));
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.region_spinner_registration:
                threadBegin("city");
                SpinnerRegionsAndCitiesAdapter spinnerRegionsAndCitiesAdapter = new SpinnerRegionsAndCitiesAdapter(this, getAllCities);
                spinnerRegionsAndCitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(spinnerRegionsAndCitiesAdapter);
                citySpinner.setVisibility(View.VISIBLE);
                break;
            case R.id.city_spinner_registration:
                threadBegin("kinder");
                SpinnerKinderGartenadapter spinnerKinderGartenadapter = new SpinnerKinderGartenadapter(this, getKinderGartensByCityCodes);
                spinnerKinderGartenadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                kinderGartenSpinner.setAdapter(spinnerKinderGartenadapter);
                kinderGartenSpinner.setVisibility(View.VISIBLE);
                break;
            case R.id.kinder_garten_spinner_registration:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
