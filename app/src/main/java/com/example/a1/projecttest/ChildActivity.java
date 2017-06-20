package com.example.a1.projecttest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.rest.Models.GetNotificationAddToParent;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ChildService;
import com.example.a1.projecttest.utils.ChildService_;
import com.example.a1.projecttest.utils.ConstantsManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;
import java.util.List;

import ru.yandex.h;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity (R.layout.child_activity)
public class ChildActivity extends AppCompatActivity implements View.OnClickListener{
    Handler handler;
    Runnable runnable;
    Dialog dialog;
    TextView headerDialog;
    TextView requestText;
    Button addButton;
    Button cancelButton;
    Typeface typeface;
    Observable<List<GetNotificationAddToParent>> getNotificationAddToParentObservable;
    List<GetNotificationAddToParent> getNotificationAddToParentOn;
    Observable<String> getRequest;
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
        typeface = Typeface.createFromAsset(getAssets(), "font/opensans.ttf");
        UserLoginSession session = new UserLoginSession(getApplication());
        Button button = (Button) findViewById(R.id.service_offBT);
        TextView idChild = (TextView) findViewById(R.id.id_child_text_view);
        TextView name = (TextView) findViewById(R.id.name_child_text_view);
        idChild.setText("Идентификатор " + session.getID());
        name.setText("Имя " + session.getUserName());
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
        getNotification();
    }

    public void getNotification(){
        RestService restService = new RestService();
        UserLoginSession session = new UserLoginSession(getApplication());
        try {
            getNotificationAddToParentObservable = restService.getNotificationAddToParent(session.getID());
            getNotificationAddToParentObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<GetNotificationAddToParent>>() {
                        @Override
                        public void onCompleted() {
                            if (getNotificationAddToParentOn != null)
                                if (getNotificationAddToParentOn.size() >= 1)
                                    showDialog();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<GetNotificationAddToParent> getNotificationAddToParent) {
                            getNotificationAddToParentOn = getNotificationAddToParent;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void acceptRequest(){
        RestService restService = new RestService();
        try {
            getRequest = restService.acceptToAddKid(getNotificationAddToParentOn.get(0).getUrlParameter());
            getRequest.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {
                            Toast.makeText(getApplication(), "Запрос принят", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {

                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.parent_request_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        headerDialog = (TextView) dialog.findViewById(R.id.id_header_parent_request);
        requestText = (TextView) dialog.findViewById(R.id.request_text_on_add);
        addButton = (Button) dialog.findViewById(R.id.button_ok_request);
        cancelButton = (Button) dialog.findViewById(R.id.button_cancel_request);

        requestText.setText("Пользователь "+ getNotificationAddToParentOn.get(0).getParentId() + " отправил вам запрос на добавление в список детей");
        addButton.setOnClickListener(this);
        headerDialog.setTypeface(typeface);
        requestText.setTypeface(typeface);
        addButton.setTypeface(typeface);
        cancelButton.setTypeface(typeface);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_ok_request:
                acceptRequest();
                dialog.dismiss();
                break;
        }
    }
}
