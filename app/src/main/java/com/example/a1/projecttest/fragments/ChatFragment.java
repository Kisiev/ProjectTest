package com.example.a1.projecttest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.a1.projecttest.R;

public class ChatFragment extends Fragment {
    Button sendBtn;
    EditText smsEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_layout, container, false);
        init(view);
        return view;
    }

    void init (View view) {
       // final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.chat_screen_frame);
        sendBtn = (Button) view.findViewById(R.id.chat_button);
        smsEdit = (EditText) view.findViewById(R.id.chat_text);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView cardView = new CardView(getActivity());
                TextView textView = new TextView(getActivity());
                textView.setText(smsEdit.getText());
                cardView.addView(textView);
               // frameLayout.addView(cardView);
            }
        });
    }
}
