package com.example.a1.projecttest;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.rest.RestService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;

@EActivity(R.layout.notify_mail_registration)
public class SendMessageSignIn extends Activity implements View.OnClickListener{
    String status;
    Button sendMessageButton;
    Button backButton;
    EditText mailEdit;
    ImageView imageView;
    @AfterViews
    public void main(){
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/SF-UI-Text-Regular.ttf");
        LoginActivity loginActivity = new LoginActivity();
        TextView header = (TextView) findViewById(R.id.header_send_message_layout);
        imageView = (ImageView) findViewById(R.id.mail_image_view_layout);
        loginActivity.createImage(R.mipmap.backgroundlogin, imageView);
        mailEdit = (EditText) findViewById(R.id.mail_editET);
        sendMessageButton = (Button) findViewById(R.id.send_message_button);
        backButton = (Button) findViewById(R.id.back_press_button);
        sendMessageButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        mailEdit.setTypeface(typeface);
        sendMessageButton.setTypeface(typeface);
        backButton.setTypeface(typeface);
        header.setTypeface(typeface);

    }

    public void sendPasswordOnEmail(){
        RestService restService = new RestService();
        try {
            status = restService.getPasswordByEmail(mailEdit.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_message_button:
                Thread sendMessage = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendPasswordOnEmail();
                    }
                });
                sendMessage.start();
                try {
                    sendMessage.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, R.string.successful, Toast.LENGTH_LONG).show();
                onBackPressed();
                break;
            case R.id.back_press_button:
                onBackPressed();
                break;
        }
    }
}
