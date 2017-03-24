package com.example.a1.projecttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.a1.projecttest.vospitatel.VospitatelMainActivity;
import com.example.a1.projecttest.vospitatel.VospitatelMainActivity_;
import com.example.a1.projecttest.zavedushaia.MainZavDetSad;
import com.example.a1.projecttest.zavedushaia.MainZavDetSad_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.registration_activity)
public class RegistrationActivity extends Activity {

    @AfterViews
    private void main () {
        initImageOnBackground();
        onClickButton();
    }
    private void initImageOnBackground(){
        ImageView imageView = (ImageView) findViewById(R.id.reg_imageView);
        LoginActivity loginActivity = new LoginActivity();
        loginActivity.createImage(R.id.reg_imageView, R.mipmap.logo, imageView);
    }

    private void onClickButton(){
        final RadioButton radioButton = (RadioButton) findViewById(R.id.child_RB);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButton.isChecked()){
                    startActivity(new Intent(RegistrationActivity.this, ChildActivity_.class));
                }
            }
        });
        final RadioButton zavDetSadBurron = (RadioButton) findViewById(R.id.zavDetSadRB);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zavDetSadBurron.isChecked()){
                    startActivity(new Intent(RegistrationActivity.this, MainZavDetSad_.class));
                }
            }
        });
        final RadioButton vospitatel = (RadioButton) findViewById(R.id.vospitatel);
        vospitatel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vospitatel.isChecked()){
                    startActivity(new Intent(RegistrationActivity.this, VospitatelMainActivity_.class));
                }

            }
        });
    }

}
