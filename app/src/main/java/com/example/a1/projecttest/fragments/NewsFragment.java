package com.example.a1.projecttest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1.projecttest.R;
import com.example.a1.projecttest.zavedushaia.fragments.AdsFragment;

import org.androidannotations.annotations.EFragment;

@EFragment()
public class NewsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout_parent, container, false);
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        replaceFragment(new FeedFragment(), R.id.content_news);
        return view;
    }
    public void replaceFragment(Fragment fragment, int id) {
        String backStackName = fragment.getClass().getName();
        FragmentManager manager = getActivity().getSupportFragmentManager();
       // boolean fragmentPopped = manager.popBackStackImmediate(backStackName, 0);

            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(id, fragment, backStackName);
            ft.addToBackStack(backStackName);
            ft.commit();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.feed_item_nav_bottom:
                FeedFragment feedFragment = new FeedFragment();
                replaceFragment(feedFragment, R.id.content_news);
                break;
            case R.id.event_calendar_nav_bottom:
                EventFragmentParent eventFragmentParent = new EventFragmentParent();
                replaceFragment(eventFragmentParent, R.id.content_news);
                break;
            case R.id.event_nav_bottom:
                AdsFragmentParent adsFragment = new AdsFragmentParent();
                replaceFragment(adsFragment, R.id.content_news);
                break;
        }
        return false;
    }
}
