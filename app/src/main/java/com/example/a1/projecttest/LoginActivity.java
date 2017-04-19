package com.example.a1.projecttest;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.Models.GetUserData;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.google.gson.JsonSyntaxException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;
import java.util.List;

@EActivity (R.layout.login_activity)
public class LoginActivity extends Activity implements View.OnClickListener{

    public UserLoginSession userLoginSession;
    public TextView loginTV, passwordTV;
    public GetListUsers validUser;
    public GetUserData getUserData;
    @AfterViews
    protected void main() {
        userLoginSession = new UserLoginSession(getApplicationContext());
        if ((!userLoginSession.getLogin().isEmpty())&&(!userLoginSession.getPassword().isEmpty()))
            startActivity();

        ImageView imageView = (ImageView) findViewById(R.id.log_imageView);
       // createImage(R.mipmap.fon, imageView);

        Button registrationBT = (Button) findViewById(R.id.registrationBT);
        Button loginBT = (Button) findViewById(R.id.signIn);
        registrationBT.setOnClickListener(this);
        loginBT.setOnClickListener(this);

        loginTV = (TextView) findViewById(R.id.login_edit);
        passwordTV = (TextView) findViewById(R.id.pass_edit);
    }

    private void startActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity_.class);
        startActivity(intent);
        finish();
    }

    public void createImage(int imageResource, ImageView imageView){
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imageResource);
    }

    public void getValidToken () {
        RestService restService = new RestService();
        try {
            getUserData = restService.getUserData(loginTV.getText().toString(), passwordTV.getText().toString());
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
                        getValidToken();
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
                    userLoginSession.setUseName(validUser.getEmail(), validUser.getPassword(), Integer.parseInt(validUser.getId()));
                    startActivity();
                    getUserData = null;
                }
                break;
            case R.id.registrationBT:
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity_.class);
                startActivity(intent);
                break;
        }
    }
}
