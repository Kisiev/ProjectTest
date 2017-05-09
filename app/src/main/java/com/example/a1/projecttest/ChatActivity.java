package com.example.a1.projecttest;

import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.print.PrintAttributes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.utils.ConstantsManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.END;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.START;

@EActivity(R.layout.chat_layout_fragment)
public class ChatActivity extends AppCompatActivity {
    Button sendBtn;
    EditText smsEdit;
    int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            Log.d("asfasf", "Нет");
        } else  Log.d("asfasf", "Нет");
    }

    @AfterViews
    public void main () {
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_text_type);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.chat_liner);
        final RelativeLayout relative_chat = (RelativeLayout) findViewById(R.id.relative_chat);
        sendBtn = (Button) findViewById(R.id.chat_button);
        smsEdit = (EditText) findViewById(R.id.chat_text);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView cardView = new CardView(ChatActivity.this);
                TextView textView = new TextView(ChatActivity.this);
                LinearLayout contentSms = new LinearLayout(ChatActivity.this);
                contentSms.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                if (i % 2 == 0)
                contentSms.setGravity(END);
                else  contentSms.setGravity(START);
                cardView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                cardView.setPadding(64, 16, 64, 16);
                textView.setPadding(64, 16, 64, 16);
                textView.setTextSize(25);
                cardView.setUseCompatPadding(true);
                textView.setText(smsEdit.getText().toString());
                cardView.addView(textView);
                contentSms.addView(cardView);
                linearLayout.addView(contentSms);
                i ++;
            }
        });

    }
}
