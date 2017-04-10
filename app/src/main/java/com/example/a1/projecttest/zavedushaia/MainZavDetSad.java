package com.example.a1.projecttest.zavedushaia;

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

import com.example.a1.projecttest.Entities.CareEntity;
import com.example.a1.projecttest.Entities.UpbringingEntity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.zavedushaia.fragments.ServicesFragment;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.zav_det_sada)
public class MainZavDetSad extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    DrawerLayout drawer;
    TabHost tabHost;
    ViewPager viewPager;

    private void updateToolbarTitle(Fragment fragment, String title) {
        String fragmentClassName = fragment.getClass().getName();
        if (fragmentClassName.equals(fragment.getClass().getName())) {
            setTitle(title);
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
    @AfterViews
    void main () {

        UserLoginSession userLvlToken = new UserLoginSession(getApplicationContext());
        userLvlToken.clear();
        userLvlToken.setUseName("Nataly", "123", 44);

    if (CareEntity.select().size() == 0) {
        SQLite.insert(CareEntity.class).columns("id", "nameCare").values(-1, "Вид ухода..").execute();
        CareEntity.insertCare(getString(R.string.food_care));
        CareEntity.insertCare(getString(R.string.hygiene_care));
        CareEntity.insertCare(getString(R.string.sleep_care));
        CareEntity.insertCare(getString(R.string.creation_care));
    }
    if (UpbringingEntity.select().size() == 0){
        SQLite.insert(UpbringingEntity.class).columns("id", "name").values(-1, "Направление..").execute();
        UpbringingEntity.insertUpbring(getString(R.string.socially_development));
        UpbringingEntity.insertUpbring(getString(R.string.cognitive_development));
        UpbringingEntity.insertUpbring(getString(R.string.verbal_development));
        UpbringingEntity.insertUpbring(getString(R.string.artistic_development));
        UpbringingEntity.insertUpbring(getString(R.string.physical_development));
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



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        int id = item.getOrder();
        switch (id){
            case 1:
                ServicesFragment servicesFragment = new ServicesFragment();
                replaceFragment(servicesFragment, R.id.content_main);
                updateToolbarTitle(servicesFragment, getString(R.string.group_tab_header));
                break;
            case 0:
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:

                break;
            case 11:

                break;
            case 12:

                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
