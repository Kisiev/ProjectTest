package com.example.a1.projecttest.zavedushaia.fragments;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.projecttest.Entities.ChildEntity;
import com.example.a1.projecttest.LoginActivity;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;

import org.androidannotations.annotations.EFragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static ru.yandex.core.CoreApplication.getActivity;


@EFragment
public class ChildAndParentFragment extends Fragment implements View.OnClickListener{
    FloatingActionButton addParentFab;
    Dialog dialog;
    LinearLayout linearLayout;
    Button saveChildButton;
    int idParentCount = 0;
    List<EditText> listEdit;
    ImageView backgroundImage;
    Typeface typeface;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parent_and_child_fragment, container, false);
        listEdit = new ArrayList<>();
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/opensans.ttf");
        saveChildButton = (Button) view.findViewById(R.id.save_child_and_parent_button);
        addParentFab = (FloatingActionButton) view.findViewById(R.id.parent_add_action_button);
        addParentFab.setOnClickListener(this);
        saveChildButton.setOnClickListener(this);
        linearLayout = (LinearLayout) view.findViewById(R.id.parent_list_liner);
        linearLayout.addView(onCreateEditText());
        idParentCount ++;
        saveChildButton.setTypeface(typeface);
       /* LoginActivity loginActivity = new LoginActivity();
        ImageView backgroundImage = (ImageView) view.findViewById(R.id.background_image_parent_and_child);
        loginActivity.createImage(R.mipmap.background, backgroundImage);*/
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.parent_add_action_button:
                if (idParentCount <= 10)
                    linearLayout.addView(onCreateEditText());
                else Toast.makeText(getActivity(), R.string.error_count_item_text, Toast.LENGTH_SHORT).show();
                break;
            case R.id.save_child_and_parent_button:
                for (EditText i : listEdit){
                    Log.d("!!!!!!!!!", i.getText().toString());
                }
                break;
        }
    }

    public View onCreateEditText (){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int sizeInDP = 16;
        int marginInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, sizeInDP, getResources()
                        .getDisplayMetrics());
        layoutParams.setMargins(0, 0, 0, marginInDp);
        EditText editText = new EditText(getActivity());
        editText.setLayoutParams(layoutParams);
        editText.setId(idParentCount);
        editText.setHint(R.string.id_parent_text);
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);
        editText.getBackground().mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setTypeface(typeface);
        listEdit.add(editText);
        idParentCount ++;
        return editText;
    }
}
