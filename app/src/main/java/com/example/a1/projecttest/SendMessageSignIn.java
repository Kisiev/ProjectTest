package com.example.a1.projecttest;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.notify_mail_registration)
public class SendMessageSignIn extends Activity implements View.OnClickListener{

    @AfterViews
    public void main(){
        ImageView imageView = (ImageView) findViewById(R.id.mail_image_view_layout);
        EditText mailEdit = (EditText) findViewById(R.id.mail_editET);
        Button sendMessageButton = (Button) findViewById(R.id.send_message_button);

        LoginActivity loginActivity = new LoginActivity();
        loginActivity.createImage(R.mipmap.fon, imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_message_button:
                break;
        }
    }
}
