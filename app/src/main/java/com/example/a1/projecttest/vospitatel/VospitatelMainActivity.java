package com.example.a1.projecttest.vospitatel;

import android.app.Application;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.a1.projecttest.LoginActivity_;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.fragments.AdsFragmentParent;
import com.example.a1.projecttest.rest.Models.GetGroupByTutorModel;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.vospitatel.fragments.AttendanceFragment;
import com.example.a1.projecttest.vospitatel.fragments.MyGroupsTutorFragment;
import com.example.a1.projecttest.vospitatel.fragments.RaspisanieFragment;
import com.example.a1.projecttest.zavedushaia.fragments.AdsFragment;
import com.example.a1.projecttest.zavedushaia.fragments.EventFragmentZav;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;

@EActivity(R.layout.vospitatel_activity)
public class VospitatelMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    Application context;
    NavigationView navigationView;
    ImageView imageView;
    TextView nameTextNavView;
 /*   TextView emailTextNavView;
    TextView idTextNavView;*/
    Typeface typeface;
    UserLoginSession session;
    Toolbar toolbar;
    SpinnerAdapter spinnerAdapter;
    GetGroupByTutorModel getGroupByTutorModel;
    Spinner navigationSpinner;


    private void updateToolbarTitle(Fragment fragment) {
        String fragmentClassName = fragment.getClass().getName();

        if (fragmentClassName.equals(RaspisanieFragment.class.getName())) {
            setTitle(getString(R.string.list_rasp));
        } else if (fragmentClassName.equals(AttendanceFragment.class.getName())){
            setTitle(getString(R.string.attendance_text));
        } else if (fragmentClassName.equals(AdsFragment.class.getName()))
            setTitle(getString(R.string.ads_text));
    }

    @AfterViews
    void main(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new UserLoginSession(this);
        beginThread();
        typeface = Typeface.createFromAsset(getAssets(), "font/SF-UI-Text-Regular.ttf");
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setMenu();
        View headerView = navigationView.getHeaderView(0);
        imageView = (ImageView) headerView.findViewById(R.id.imageView);
        nameTextNavView = (TextView) headerView.findViewById(R.id.name_text_view);
       /* emailTextNavView = (TextView) headerView.findViewById(R.id.email_text_view);
        idTextNavView = (TextView) headerView.findViewById(R.id.id_user_text_view);*/

        nameTextNavView.setTypeface(typeface);
      /*  emailTextNavView.setTypeface(typeface);
        idTextNavView.setTypeface(typeface);*/

        MainActivity.saveGlideParam(imageView, this, null, R.mipmap.avatar);
        setNavigationViewItem();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_main);
                if (f != null) {
                    updateToolbarTitle(f);
                } else finish();
            }
        });
        replaceFragment(new AttendanceFragment(), R.id.content_main);
        updateToolbarTitle(new AttendanceFragment());
        spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.group_spinner, android.R.layout.simple_spinner_dropdown_item);
        //ArrivingFragment arrivingFragment = new ArrivingFragment();
        //replaceFragment(arrivingFragment, R.id.content_main);
    }

    private void setNavigationViewItem() {
        nameTextNavView.setText(session.getUserName() + " " + session.getUserSurname());
        navigationView.setBackgroundColor(getResources().getColor(R.color.colorDrawer));
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.whiteColor)));
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navMenuView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));



        /*emailTextNavView.setText(session.getLogin());
        idTextNavView.setText("Идентификатор: " + session.getID());*/
    }

    private void setMenu(){
        Menu menu = navigationView.getMenu();
        menu.add(Menu.NONE, ConstantsManager.MENU_ID, 0, getString(R.string.messages));
       // menu.getItem(0).setIcon(R.drawable.ic_transfer_within_a_station_black_24dp);
        Menu menu_2 = navigationView.getMenu();
        menu_2.add(Menu.NONE, ConstantsManager.MENU_ID, 1, getString(R.string.life_feed_l));
       // menu_2.getItem(1).setIcon(R.drawable.ic_perm_contact_calendar_black_24dp);
        Menu menu_3 = navigationView.getMenu();
        menu_3.add(Menu.NONE, ConstantsManager.MENU_ID, 2, getString(R.string.my_group));
        //menu_3.getItem(2).setIcon(R.drawable.ic_format_list_numbered_black_24dp);
        Menu menu_4 = navigationView.getMenu();
        menu_4.add(Menu.NONE, ConstantsManager.MENU_ID, 3, getString(R.string.calendar));
        //menu_4.getItem(3).setIcon(R.drawable.ic_assignment_black_24dp);
        Menu menu_5 = navigationView.getMenu();
        menu_5.add(Menu.NONE, ConstantsManager.MENU_ID, 4, R.string.notification);
        //menu_5.getItem(4).setIcon(R.drawable.ic_mail_black_24dp);
        Menu menu_6 = navigationView.getMenu();
        menu_6.add(Menu.NONE, ConstantsManager.MENU_ID, 5, R.string.exit);

    }


    public VospitatelMainActivity () {

    }

    public void beginThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getTutorGroup();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getTutorGroup(){
        RestService restService = new RestService();
        try {
            getGroupByTutorModel = restService.getGroupByTutorModel(session.getID());
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.saveTutorGroup(getGroupByTutorModel.getId(), getGroupByTutorModel.getName(), getGroupByTutorModel.getKindergartenId());
    }

    public void replaceFragment(Fragment fragment, int id) {
        String backStackName = fragment.getClass().getName();
        FragmentManager manager = this.getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
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
                AttendanceFragment attendanceFragment = new AttendanceFragment();
                replaceFragment(attendanceFragment, R.id.content_main);
                updateToolbarTitle(attendanceFragment);
                toolbar.removeView(navigationSpinner);
                break;
            case 1:
                RaspisanieFragment raspisanieFragment = new RaspisanieFragment();
                replaceFragment(raspisanieFragment, R.id.content_main);
                updateToolbarTitle(raspisanieFragment);
                toolbar.removeView(navigationSpinner);
                break;
            case 2:
                MyGroupsTutorFragment mg = new MyGroupsTutorFragment();
                replaceFragment(mg, R.id.content_main);
                navigationSpinner = new Spinner(this);
                navigationSpinner.setAdapter(spinnerAdapter);
                toolbar.addView(navigationSpinner, 0);
              /*  AdsFragment adsFragment = new AdsFragment();
                replaceFragment(adsFragment, R.id.content_main);
                updateToolbarTitle(adsFragment);*/
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:
                UserLoginSession session = new UserLoginSession(this);
                session.clear();
                startActivity(new Intent(this, LoginActivity_.class));
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
