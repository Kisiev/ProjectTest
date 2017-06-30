package com.example.a1.projecttest.zavedushaia.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.a1.projecttest.R;

import org.androidannotations.annotations.EFragment;


@EFragment()
public class AdsFragment extends Fragment{
    FloatingActionButton addAdsFloading;
    Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ads_fragment, container, false);
        addAdsFloading = (FloatingActionButton) view.findViewById(R.id.floating_add_ads);
        addAdsFloading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAds();
            }
        });
        return view;
    }
    public void showDialogAds(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.add_ads_gialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;


        dialog.show();
    }
}
