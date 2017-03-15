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
public class LoginActivity extends Activity {

    UserLoginSession userLoginSession;
    TextView loginTV, passwordTV;
    GetListUsers validUser;
    @AfterViews
    protected void main() {
        userLoginSession = new UserLoginSession(getApplicationContext());
        if ((!userLoginSession.getLogin().isEmpty())&&(!userLoginSession.getPassword().isEmpty()))
            startActivity();
        onButtonClick();
        ImageView imageView = (ImageView) findViewById(R.id.log_imageView);
        createImage(R.id.log_imageView, R.mipmap.logo, imageView);

        loginTV = (TextView) findViewById(R.id.login_edit);
        passwordTV = (TextView) findViewById(R.id.pass_edit);
    }

    private void onButtonClick (){
        final Button registrationBT = (Button) findViewById(R.id.registrationBT);
        Button loginBT = (Button) findViewById(R.id.signIn);
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if (validUser == null){
                    Toast.makeText(getApplicationContext(), getString(R.string.invalid_login), Toast.LENGTH_LONG).show();
                } else {
                    userLoginSession.setUseName(validUser.getLogin(), validUser.getPassword(), Integer.parseInt(validUser.getId()));
                    startActivity();
                    validUser = null;
                }
            }
        });
        registrationBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity_.class);
        startActivity(intent);
        finish();
    }

    public void createImage(int imageId, int imageResource, ImageView imageView){
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imageResource);
    }

    public void getValidToken () {
        RestService restService = new RestService();
        try {
            validUser = restService.validUser(loginTV.getText().toString(), passwordTV.getText().toString());
        } catch (JsonSyntaxException e) {
            validUser = null;
            e.printStackTrace();
        } catch (IOException e) {
            validUser = null;
            e.printStackTrace();
        } catch (RuntimeException e){
            validUser = null;
            e.printStackTrace();
        }
    }


}
