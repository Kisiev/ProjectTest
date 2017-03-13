package com.example.a1.projecttest;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ChildService;
import com.example.a1.projecttest.utils.ChildService_;
import org.androidannotations.annotations.EActivity;

@EActivity (R.layout.child_activity)
public class ChildActivity extends Activity {

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(ChildActivity.this, ChildService_.class));
        Button button = (Button) findViewById(R.id.service_offBT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(ChildActivity.this, ChildService_.class));
                Toast.makeText(ChildActivity.this, "Сервис был уничтожен", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       //
    }
}
