package com.example.a1.projecttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.a1.projecttest.rest.Models.GetStatusCode;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.vospitatel.VospitatelMainActivity;
import com.example.a1.projecttest.vospitatel.VospitatelMainActivity_;
import com.example.a1.projecttest.zavedushaia.MainZavDetSad;
import com.example.a1.projecttest.zavedushaia.MainZavDetSad_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;

@EActivity(R.layout.registration_activity)
public class RegistrationActivity extends Activity implements View.OnClickListener {
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
                    String.valueOf(roleId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterViews
    public void main () {
        ImageView imageView = (ImageView) findViewById(R.id.reg_imageView);

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
        childRb.setOnClickListener(this);
        zavRb.setOnClickListener(this);
        vospitRb.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.child_RB:
                startActivity(new Intent(RegistrationActivity.this, ChildActivity_.class));
                break;
            case R.id.zavDetSadRB:
                startActivity(new Intent(RegistrationActivity.this, MainZavDetSad_.class));
                break;
            case R.id.vospitatel:
                startActivity(new Intent(RegistrationActivity.this, VospitatelMainActivity_.class));
                break;
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

}
