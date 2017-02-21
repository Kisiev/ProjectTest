package com.example.a1.projecttest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class RegistrationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        ImageView imageView = (ImageView) findViewById(R.id.reg_imageView);
        LoginActivity loginActivity = new LoginActivity();
        loginActivity.createImage(R.id.reg_imageView, R.mipmap.logo, imageView);
    }

}
