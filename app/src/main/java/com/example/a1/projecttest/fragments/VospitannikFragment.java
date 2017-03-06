package com.example.a1.projecttest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.adapters.VospitannikAdapter;
import com.example.a1.projecttest.utils.CircleTransform;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class VospitannikFragment extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vospitanik_fragment, container, false);
        List<String> listService = new ArrayList<>();
        listService.add("Отдых");
        listService.add("Сон");
        listService.add("Хавчик");
        recyclerView = (RecyclerView) view.findViewById(R.id.vospit_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new VospitannikAdapter(listService));

        return view;
    }

    private void saveGlideParam(ImageView imageView) {

        Glide.with(getActivity())
                .load(R.drawable.common_ic_googleplayservices).asBitmap().into(imageView);
              //  .bitmapTransform(new CircleTransform(getActivity()))
               // .diskCacheStrategy(DiskCacheStrategy.SOURCE)
               // .into(new BitmapImageViewTarget(imageView).getView());
    }
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
