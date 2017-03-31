package com.example.a1.projecttest.zavedushaia;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TabHost;

import com.example.a1.projecttest.Entities.ServicesEntity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.adapters.NewPagerFragmentAdapter;
import com.example.a1.projecttest.fragments.ShcolnilFragment;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.utils.ContentFactory;
import com.example.a1.projecttest.vospitatel.fragments.RaspisanieFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.zav_det_sada)
public class MainZavDetSad extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    DrawerLayout drawer;

    public FragmentManager getFragmentMan() {
        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        return fragmentManager;
    }

    public MainZavDetSad () {

    }

    @AfterViews
    void main () {

    if (ServicesEntity.select().size() == 0) {
        ServicesEntity.insertService("Уход");
        ServicesEntity.insertService("Воспитание");
    }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        replaceFragment(new DirectoryFragment(), R.id.content_main);
    }
    public void replaceFragment(Fragment fragment, int id) {
        String backStackName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped && manager.findFragmentByTag(backStackName) == null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(id, fragment, backStackName);
            ft.addToBackStack(backStackName);
            ft.commit();
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


}
