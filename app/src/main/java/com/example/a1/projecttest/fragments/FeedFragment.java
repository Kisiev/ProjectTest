package com.example.a1.projecttest.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.CircleImageAdapter;
import com.example.a1.projecttest.adapters.FeedAdapter;
import com.example.a1.projecttest.utils.ConstantsManager;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);
        List<String> listService = new ArrayList<>();
        listService.add("Иванов А.В");
        listService.add("Иванов В.П");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_circle_item);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setAdapter(new CircleImageAdapter(listService, getActivity()));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        RecyclerView recyclerViewFeed = (RecyclerView) view.findViewById(R.id.recycler_feed_item);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFeed.setLayoutManager(verticalLayoutManager);
        recyclerViewFeed.setAdapter(new FeedAdapter(listService));

        Toast.makeText(getActivity(), "Вы зашли как пользователь: " + sharedPreferences.getString(ConstantsManager.LOGIN, ""), Toast.LENGTH_LONG).show();

        return view;
    }
}
