package com.example.a1.projecttest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1.projecttest.R;

import org.androidannotations.annotations.EFragment;

@EFragment()
public class AdsFragmentParent extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ads_fragment_parent, container, false);
        return view;
    }
}
