package com.example.a1.projecttest;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.Entities.DayOfWeek;
import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.Models.GetUserData;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ChildService;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.vospitatel.VospitatelMainActivity;
import com.example.a1.projecttest.vospitatel.VospitatelMainActivity_;
import com.example.a1.projecttest.zavedushaia.MainZavDetSad;
import com.example.a1.projecttest.zavedushaia.MainZavDetSad_;
import com.google.gson.JsonSyntaxException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.IgnoreWhen;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;
import java.util.List;

@EActivity (R.layout.login_activity)
public class LoginActivity extends Activity implements View.OnClickListener{

    public UserLoginSession userLoginSession;
    public TextView loginTV, passwordTV;
    public TextView header;
    public GetListUsers validUser;
    public GetUserData getUserData;
    public Typeface typeface;

    private RadioButton vospitatel;
    private RadioButton roditel;
    private RadioButton zav;
    private RadioButton rebenok;


    @AfterViews
    protected void main() {
        if(DayOfWeek.selectDays().size() == 0) {
            DayOfWeek.insertDay("Понедельник");
            DayOfWeek.insertDay("Вторник");
            DayOfWeek.insertDay("Среда");
            DayOfWeek.insertDay("Четверг");
            DayOfWeek.insertDay("Пятница");
            DayOfWeek.insertDay("Суббота");
            DayOfWeek.insertDay("Воскресенье");
        }
        zav = (RadioButton) findViewById(R.id.zaveduushi);
        vospitatel = (RadioButton) findViewById(R.id.vospitatel);
        rebenok = (RadioButton) findViewById(R.id.rebenok);
        roditel = (RadioButton) findViewById(R.id.roditel);
        zav.setOnClickListener(this);
        vospitatel.setOnClickListener(this);
        rebenok.setOnClickListener(this);
        roditel.setOnClickListener(this);
        userLoginSession = new UserLoginSession(getApplicationContext());
        if ((!userLoginSession.getLogin().isEmpty())&&(!userLoginSession.getPassword().isEmpty())){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    getValidToken(userLoginSession.getLogin(), userLoginSession.getPassword());
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (getUserData != null) {
                switch (userLoginSession.getRoleId()) {
                    case 1:
                        startActivity(new Intent(LoginActivity.this, MainActivity_.class));
                        finish();
                        break;
                    case 2:
                        startActivity(new Intent(LoginActivity.this, MainZavDetSad_.class));
                        finish();
                        break;
                    case 3:
                        startActivity(new Intent(LoginActivity.this, ChildActivity_.class));
                        finish();
                        break;
                    case 4:

                        break;
                    case 5:
                        startActivity(new Intent(LoginActivity.this, VospitatelMainActivity_.class));
                        finish();
                        break;
                    case 0:

                        break;
                }
            } else {
                userLoginSession.clear();
                startActivity(new Intent(this, LoginActivity_.class));
                finish();
            }
        }

        ImageView imageView = (ImageView) findViewById(R.id.log_imageView);
        createImage(R.mipmap.background, imageView);

        Button registrationBT = (Button) findViewById(R.id.registrationBT);
        Button loginBT = (Button) findViewById(R.id.signIn);
        registrationBT.setOnClickListener(this);
        loginBT.setOnClickListener(this);

        header = (TextView) findViewById(R.id.header_login_layout);
        loginTV = (TextView) findViewById(R.id.login_edit);
        passwordTV = (TextView) findViewById(R.id.pass_edit);

        typeface = Typeface.createFromAsset(getAssets(), "font/opensans.ttf");
        header.setTypeface(typeface);
        loginTV.setTypeface(typeface);
        passwordTV.setTypeface(typeface);
        registrationBT.setTypeface(typeface);
        loginBT.setTypeface(typeface);

    }


    public void createImage(int imageResource, ImageView imageView){
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imageResource);
    }

    public void getValidToken (String login, String password) {
        RestService restService = new RestService();
        try {
            getUserData = restService.getUserData(login, password);
        } catch (JsonSyntaxException e) {
            getUserData = null;
            e.printStackTrace();
        } catch (IOException e) {
            getUserData = null;
            e.printStackTrace();
        } catch (RuntimeException e){
            getUserData = null;
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signIn:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getValidToken(loginTV.getText().toString(), passwordTV.getText().toString());
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getUserData == null){
                    Toast.makeText(getApplicationContext(), getString(R.string.invalid_login), Toast.LENGTH_LONG).show();
                } else {
                    userLoginSession.setUseName(getUserData.getEmail(),
                            passwordTV.getText().toString(),
                            getUserData.getId(),
                            getUserData.getName(),
                            getUserData.getSurname(),
                            getUserData.getPatronymic(),
                            Integer.valueOf(getUserData.getRoleId()),
                            Integer.valueOf(getUserData.getIsActivated()));
                    if (getUserData.getIsActivated().equals("0"))
                        startActivity(new Intent(LoginActivity.this, RegistrationActivity_.class));
                    else if (getUserData.getIsActivated().equals("1")) {
                        switch (getUserData.getRoleId()) {
                            case "1":
                                startActivity(new Intent(LoginActivity.this, MainActivity_.class));
                                break;
                            case "2":
                                startActivity(new Intent(LoginActivity.this, MainZavDetSad_.class));
                                break;
                            case "3":
                                startActivity(new Intent(LoginActivity.this, ChildActivity_.class));
                                break;
                            case "4":

                                break;
                            case "5":
                                startActivity(new Intent(LoginActivity.this, VospitatelMainActivity_.class));
                                break;
                        }
                    }
                    getUserData = null;
                    finish();
                }
                break;
            case R.id.registrationBT:
                Intent intent = new Intent(LoginActivity.this, SendMessageSignIn_.class);
                startActivity(intent);
                break;
            case R.id.zaveduushi:
                startActivity(new Intent(this, MainZavDetSad_.class));
                finish();
                break;
            case R.id.vospitatel:
                startActivity(new Intent(this, VospitatelMainActivity_.class));
                finish();
                break;
            case R.id.rebenok:
                startActivity(new Intent(this, ChildActivity_.class));
                finish();
                break;
            case R.id.roditel:
                startActivity(new Intent(this, MainActivity_.class));
                finish();
                break;
        }
    }
}
