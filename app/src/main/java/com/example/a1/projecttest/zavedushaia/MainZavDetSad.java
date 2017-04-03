package com.example.a1.projecttest.zavedushaia;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.example.a1.projecttest.ChatActivity_;
import com.example.a1.projecttest.Entities.ServicesEntity;
import com.example.a1.projecttest.LoginActivity_;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.MapActivity_;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.adapters.NewPagerFragmentAdapter;
import com.example.a1.projecttest.fragments.FeedFragment;
import com.example.a1.projecttest.fragments.ShcolnilFragment;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.utils.ContentFactory;
import com.example.a1.projecttest.vospitatel.fragments.RaspisanieFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.zav_det_sada)
public class MainZavDetSad extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener{
    NavigationView navigationView;
    DrawerLayout drawer;
    TabHost tabHost;
    ViewPager viewPager;

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
        setMenu();
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


    private void setMenu(){
        Menu menu = navigationView.getMenu();
        menu.add(Menu.NONE, ConstantsManager.MENU_ID, 0, getString(R.string.life_feed));
        menu.getItem(0).setIcon(R.drawable.ic_event_note_black_24dp);
        Menu directoryMenu = navigationView.getMenu();
        directoryMenu = directoryMenu.addSubMenu(R.string.directory_menu_group);
        directoryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 1, getString(R.string.group_tab_header));
        directoryMenu.getItem(0).setIcon(R.drawable.ic_group_black_24dp);

        directoryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 2, getString(R.string.tutor_tab_header));
        directoryMenu.getItem(1).setIcon(R.drawable.ic_person_black_24dp);
        directoryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 3, getString(R.string.services_tab_header));
        directoryMenu.getItem(2).setIcon(R.drawable.ic_assignment_black_24dp);
        directoryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 4, getString(R.string.calendar_tab_header));
        directoryMenu.getItem(3).setIcon(R.drawable.ic_perm_contact_calendar_black_24dp);

        Menu registryMenu = navigationView.getMenu();
        registryMenu = registryMenu.addSubMenu(R.string.registry_menu_group);
        registryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 5, R.string.registry_child_menu_item);
        registryMenu.getItem(0).setIcon(R.drawable.ic_child_care_black_24dp);
        registryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 6, R.string.registry_menu_item_tutor);
        registryMenu.getItem(1).setIcon(R.drawable.ic_supervisor_account_black_24dp);

        Menu documentsMenu = navigationView.getMenu();
        documentsMenu = documentsMenu.addSubMenu(R.string.documents_menu_group);
        documentsMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 7, R.string.list_services_menu_item);
        documentsMenu.getItem(0).setIcon(R.drawable.ic_receipt_black_24dp);
        documentsMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 8, R.string.list_orders_time_menu_item);
        documentsMenu.getItem(1).setIcon(R.drawable.ic_featured_play_list_black_24dp);

        Menu ordersMenu = navigationView.getMenu();
        ordersMenu = ordersMenu.addSubMenu(R.string.orders_menu_group);
        ordersMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 9, R.string.payment_item_menu);
        ordersMenu.getItem(0).setIcon(R.drawable.ic_attach_money_black_24dp);
        ordersMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 10, R.string.list_child_arr_item_menu);
        ordersMenu.getItem(1).setIcon(R.drawable.ic_transfer_within_a_station_black_24dp);
        ordersMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 11, R.string.list_enployee_item_menu);
        ordersMenu.getItem(2).setIcon(R.drawable.ic_assignment_turned_in_black_24dp);

        Menu communicationMenu = navigationView.getMenu();
        communicationMenu = communicationMenu.addSubMenu(R.string.communication_menu_group);
        communicationMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 12, R.string.chats_item_menu);
        communicationMenu.getItem(0).setIcon(R.drawable.ic_mail_black_24dp);

        Menu exitMenu = navigationView.getMenu();
        exitMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 13, R.string.exit);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        int id = item.getOrder();
        switch (id){
            case 0:
                initTab();
                initViewPager();
                break;
            case 1:

                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        NewPagerFragmentAdapter pagerFragmentAdapter;
        viewPager.setOffscreenPageLimit(4);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ServicesFragment());
        fragments.add(new ServicesFragment());
        fragments.add(new ServicesFragment());
        fragments.add(new ServicesFragment());
        pagerFragmentAdapter = new NewPagerFragmentAdapter(getSupportFragmentManager(), fragments);
        // pagerFragmentAdapter = new NewPagerFragmentAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(pagerFragmentAdapter);

        viewPager.setOnPageChangeListener(this);
    }


    private void initTab() {

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        String[] tabName = {getString(R.string.group_tab_header),
                getString(R.string.tutor_tab_header),
                getString(R.string.services_tab_header),
                getString(R.string.calendar_tab_header)};

        for (int i = 0; i < tabName.length; i++) {
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabName[i]);
            tabSpec.setIndicator(tabName[i]);
            tabSpec.setContent(new ContentFactory(getApplicationContext()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {
        int selectedItem = tabHost.getCurrentTab();
        viewPager.setCurrentItem(selectedItem);

    }

}
