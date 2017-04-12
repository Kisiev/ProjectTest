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
public class RegistrationActivity extends Activity implements View.OnClickListener{
    RadioButton childRb;
    RadioButton zavRb;
    RadioButton vospitRb;
    @AfterViews
    public void main () {
        ImageView imageView = (ImageView) findViewById(R.id.reg_imageView);
        LoginActivity loginActivity = new LoginActivity();
        childRb = (RadioButton) findViewById(R.id.child_RB);
        zavRb = (RadioButton) findViewById(R.id.zavDetSadRB);
        vospitRb = (RadioButton) findViewById(R.id.vospitatel);
        childRb.setOnClickListener(this);
        zavRb.setOnClickListener(this);
        vospitRb.setOnClickListener(this);
        loginActivity.createImage(R.mipmap.fon, imageView);
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
        }
    }
}
