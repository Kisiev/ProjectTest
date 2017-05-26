package com.example.a1.projecttest;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.Models.GetUserData;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ChildService;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.StandardWindowDialog;
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
public class LoginActivity extends Activity implements View.OnClickListener {

    public UserLoginSession userLoginSession;
    public TextView loginTV, passwordTV;
    public TextView header;
    public GetListUsers validUser;
    public GetUserData getUserData;
    public Typeface typeface;
    public ProgressBar progressBar;
    private RadioButton vospitatel;
    private RadioButton roditel;
    private RadioButton zav;
    private RadioButton rebenok;
    private Handler handler = new Handler();




    private void startActivityOnRole (){
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

    @AfterViews
    protected void main() {

        zav = (RadioButton) findViewById(R.id.zaveduushi);
        vospitatel = (RadioButton) findViewById(R.id.vospitatel);
        rebenok = (RadioButton) findViewById(R.id.rebenok);
        roditel = (RadioButton) findViewById(R.id.roditel);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        zav.setOnClickListener(this);
        vospitatel.setOnClickListener(this);
        rebenok.setOnClickListener(this);
        roditel.setOnClickListener(this);
        userLoginSession = new UserLoginSession(getApplicationContext());


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

        loginTV.addTextChangedListener(textWatcher);
        if ((!userLoginSession.getSaveLogin().isEmpty())&&(!userLoginSession.getSavePassword().isEmpty()))
            getValidToken(userLoginSession.getSaveLogin(), userLoginSession.getSavePassword());
        else {
            userLoginSession.clear();
            //startActivity(new Intent(this, LoginActivity_.class));
            //finish();
        }

    }


    public TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            UserLoginSession session = new UserLoginSession(getApplicationContext());
            if (loginTV.getText().toString().equals(session.getSaveLogin())){
                passwordTV.setText(session.getSavePassword());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void createImage(int imageResource, ImageView imageView){
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imageResource);
    }

    @UiThread
    public void gg(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Background()
    public void getValidToken (String login, String password) {
        RestService restService = new RestService();
        gg();
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
                UserLoginSession session = new UserLoginSession(this);
                if (!session.getSaveLogin().equals(login) || (!session.getSavePassword().equals(password))){
                    StandardWindowDialog dialog = new StandardWindowDialog(login, password, getUserData);
                    dialog.show(getFragmentManager(), "dialog");
                } else {
                    startActivityOnRole();
                }
            }
            getUserData = null;
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signIn:
                getValidToken(loginTV.getText().toString(), passwordTV.getText().toString());
                break;
            case R.id.registrationBT:
                Intent intent = new Intent(LoginActivity.this, SendMessageSignIn_.class);
                startActivity(intent);
                break;
            case R.id.zaveduushi:
                getValidToken("kisivaleri@gmail.com", "CAaiTj0Jl4");
                break;
            case R.id.vospitatel:
                getValidToken("khaidurovdisk@yandex.ru", "dOAwkypR1J");
                break;
            case R.id.rebenok:
                startActivity(new Intent(this, ChildActivity_.class));
                finish();
                break;
            case R.id.roditel:
                getValidToken("v.kisiev@asa.guru", "Wg9xsP8flq");
                break;
        }
    }
}
