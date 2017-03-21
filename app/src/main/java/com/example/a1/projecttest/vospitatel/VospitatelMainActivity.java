package com.example.a1.projecttest.vospitatel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.a1.projecttest.ChatActivity_;
import com.example.a1.projecttest.LoginActivity_;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.MapActivity_;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.fragments.ArrivingFragment;
import com.example.a1.projecttest.fragments.FeedFragment;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.vospitatel.fragments.RaspisanieFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.vospitatel_activity)
public class VospitatelMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    Application context;
    NavigationView navigationView;
    @AfterViews
    void main(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setMenu();
        ArrivingFragment arrivingFragment = new ArrivingFragment();
        replaceFragment(arrivingFragment, R.id.content_main);
    }

    private void setMenu(){
        Menu menu = navigationView.getMenu();
        menu.add(Menu.NONE, 223, 0, "Посещаемость");
        menu.add(Menu.NONE, 223, 1, "Расписание");
        menu.add(Menu.NONE, 223, 2, "Отчеты");
        menu.add(Menu.NONE, 123, 3, "Коммуникация");
        menu.add(Menu.NONE, 123, 4, "Выход");
    }

    public VospitatelMainActivity () {

    }

    public void replaceFragment(Fragment fragment, int id) {
        String backStackName = fragment.getClass().getName();
        FragmentManager manager = this.getSupportFragmentManager();
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
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        int id = item.getOrder();
        switch (id){
            case 0:
                ArrivingFragment arrivingFragment = new ArrivingFragment();
                replaceFragment(arrivingFragment, R.id.content_main);
                break;
            case 1:
                RaspisanieFragment raspisanieFragment = new RaspisanieFragment();
                replaceFragment(raspisanieFragment, R.id.content_main);
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
