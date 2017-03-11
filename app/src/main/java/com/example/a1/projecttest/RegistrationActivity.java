package com.example.a1.projecttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

public class RegistrationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        ImageView imageView = (ImageView) findViewById(R.id.reg_imageView);
        LoginActivity loginActivity = new LoginActivity();
        loginActivity.createImage(R.id.reg_imageView, R.mipmap.logo, imageView);

        final RadioButton radioButton = (RadioButton) findViewById(R.id.child_RB);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButton.isChecked()){
                    startActivity(new Intent(RegistrationActivity.this, ChildActivity_.class));
                }
            }
        });
    }

}
