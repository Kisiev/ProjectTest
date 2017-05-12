package com.example.a1.projecttest;

import android.app.Activity;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.example.a1.projecttest.utils.ChildService;
import com.example.a1.projecttest.utils.ChildService_;
import com.example.a1.projecttest.utils.ConstantsManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import ru.yandex.h;

@EActivity (R.layout.child_activity)
public class ChildActivity extends Activity {
    Handler handler;
    Runnable runnable;
    public void setCoordinates(){
        if (ContextCompat.checkSelfPermission(ChildActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(ChildActivity.this, ChildService_.class);
            startService(intent);
            handler.removeCallbacks(runnable);
        } else {
            handler.postDelayed(runnable, 1000);
            Log.d("Запущена задержка !!", "");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(ChildActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, ConstantsManager.PERMISSION_REQUEST_CODE);
        return;
    }

    @AfterViews
    public void main (){
        Button button = (Button) findViewById(R.id.service_offBT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(ChildActivity.this, ChildService_.class));
                Toast.makeText(ChildActivity.this, "Сервис был уничтожен", Toast.LENGTH_LONG).show();
                UserLoginSession session = new UserLoginSession(ChildActivity.this);
                session.clear();
                startActivity(new Intent(ChildActivity.this, LoginActivity_.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        setCoordinates();
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Запущен серткординат", "");
                setCoordinates();
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}
