package com.example.a1.projecttest.utils;

import android.view.View;

import com.example.a1.projecttest.Entities.ChildStatusEntity;

import java.util.List;

public interface ClickListener {
    public void onClick(List<ChildStatusEntity> service, View view, int position);
    public void onLongClick(View view, int position);
}
