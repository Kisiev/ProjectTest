package com.example.a1.projecttest.zavedushaia;

import android.content.Intent;
import android.graphics.Typeface;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.a1.projecttest.LoginActivity;
import com.example.a1.projecttest.LoginActivity_;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.zavedushaia.fragments.AdsFragment;
import com.example.a1.projecttest.zavedushaia.fragments.ChildAndParentFragment;
import com.example.a1.projecttest.zavedushaia.fragments.EventFragmentZav;
import com.example.a1.projecttest.zavedushaia.fragments.FoodMenuFragment;
import com.example.a1.projecttest.zavedushaia.fragments.GroupsFragment;
import com.example.a1.projecttest.zavedushaia.fragments.RegisterChildFragment;
import com.example.a1.projecttest.zavedushaia.fragments.ServicesFragment;
import com.example.a1.projecttest.zavedushaia.fragments.TutorFragment;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import static ru.yandex.core.CoreApplication.getActivity;

@EActivity(R.layout.zav_det_sada)
public class MainZavDetSad extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    DrawerLayout drawer;
    ImageView backgroundImage;
    ImageView imageView;
    TextView nameTextNavView;
    TextView emailTextNavView;
    TextView idTextNavView;
    UserLoginSession session;
    TabHost tabHost;
    ViewPager viewPager;
    Typeface typeface;

    private void updateToolbarTitle(Fragment fragment) {
        String fragmentClassName = fragment.getClass().getName();

        if (fragmentClassName.equals(ChildAndParentFragment.class.getName())) {
            setTitle(getString(R.string.parent_and_child));
        } else if (fragmentClassName.equals(ServicesFragment.class.getName())) {
            setTitle(getString(R.string.activities));
        } else if (fragmentClassName.equals(GroupsFragment.class.getName())) {
            setTitle(getString(R.string.group_tab_header));
        } else if (fragmentClassName.equals(TutorFragment.class.getName())){
            setTitle(getString(R.string.tutor_tab_header));
        } else if (fragmentClassName.equals(RegisterChildFragment.class.getName())){
            setTitle(getString(R.string.registry_child_menu_item));
        }else if (fragmentClassName.equals(EventFragmentZav.class.getName())){
            setTitle(getString(R.string.calendar_tab_header));
        }else if (fragmentClassName.equals(FoodMenuFragment.class.getName())){
            setTitle(getString(R.string.foot_menu));
        }else if (fragmentClassName.equals(AdsFragment.class.getName())){
            setTitle(getString(R.string.ads_text));
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
        directoryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 15, getString(R.string.foot_menu));
        directoryMenu.getItem(4).setIcon(R.drawable.ic_local_pizza_black_24dp);
        directoryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 16, R.string.ads_text);
        directoryMenu.getItem(5).setIcon(R.drawable.ic_featured_play_list_black_24dp);

        Menu registryMenu = navigationView.getMenu();
        registryMenu = registryMenu.addSubMenu(R.string.registry_menu_group);
        registryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 5, R.string.registry_child_menu_item);
        registryMenu.getItem(0).setIcon(R.drawable.ic_child_care_black_24dp);
        registryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 6, R.string.registry_menu_item_tutor);
        registryMenu.getItem(1).setIcon(R.drawable.ic_supervisor_account_black_24dp);
        registryMenu.add(Menu.NONE, ConstantsManager.MENU_ID, 14, R.string.parent_and_child);
        registryMenu.getItem(2).setIcon(R.drawable.ic_person_add_black_24dp);

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
        typeface = Typeface.createFromAsset(getAssets(), "font/SF-UI-Text-Regular.ttf");

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

        View headerView = navigationView.getHeaderView(0);
        session = new UserLoginSession(getApplicationContext());
        imageView = (ImageView) headerView.findViewById(R.id.imageView);
        nameTextNavView = (TextView) headerView.findViewById(R.id.name_text_view);
       /* emailTextNavView = (TextView) headerView.findViewById(R.id.email_text_view);
        idTextNavView = (TextView) headerView.findViewById(R.id.id_user_text_view);*/
        setNavigationViewItem();
        backgroundImage = (ImageView) findViewById(R.id.background_image_view_zav);
        MainActivity.saveGlideParam(imageView, this, null, R.mipmap.avatar);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_main);
                if (f != null) {
                    updateToolbarTitle(f);
                } else finish();
            }
        });
        nameTextNavView.setTypeface(typeface);
        /*emailTextNavView.setTypeface(typeface);
        idTextNavView.setTypeface(typeface);*/
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

    private void setNavigationViewItem(){
        nameTextNavView.setText(session.getUserName() + " " + session.getUserSurname());
       /* emailTextNavView.setText(session.getLogin());
        idTextNavView.setText("Идентификатор: " + session.getID());*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        LoginActivity loginActivity = new LoginActivity();
        int id = item.getOrder();
        switch (id){
            case 1:
                GroupsFragment groupsFragment = new GroupsFragment();
                replaceFragment(groupsFragment, R.id.content_main);
                updateToolbarTitle(groupsFragment);
                loginActivity.createImage(R.color.cardview_light_background, backgroundImage);
                break;
            case 0:
                break;
            case 2:
                TutorFragment tutorFragment = new TutorFragment();
                replaceFragment(tutorFragment, R.id.content_main);
                updateToolbarTitle(tutorFragment);
                loginActivity.createImage(R.color.cardview_light_background, backgroundImage);
                break;
            case 3:
                ServicesFragment servicesFragment = new ServicesFragment();
                replaceFragment(servicesFragment, R.id.content_main);
                updateToolbarTitle(servicesFragment);
                loginActivity.createImage(R.color.cardview_light_background, backgroundImage);
                break;
            case 4:
                EventFragmentZav eventFragmentZav = new EventFragmentZav();
                replaceFragment(eventFragmentZav, R.id.content_main);
                updateToolbarTitle(eventFragmentZav);
                loginActivity.createImage(R.color.cardview_light_background, backgroundImage);
                break;
            case 5:
                RegisterChildFragment registerChildFragment = new RegisterChildFragment();
                replaceFragment(registerChildFragment, R.id.content_main);
                updateToolbarTitle(registerChildFragment);
                loginActivity.createImage(R.color.cardview_light_background, backgroundImage);
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
            case 13:
                UserLoginSession session = new UserLoginSession(this);
                session.clear();
                startActivity(new Intent(this, LoginActivity_.class));
                finish();
                break;
            case 14:
                ChildAndParentFragment cp = new ChildAndParentFragment();
                replaceFragment(cp, R.id.content_main);
                updateToolbarTitle(cp);
                loginActivity.createImage(R.mipmap.background, backgroundImage);
                break;
            case 15:
                FoodMenuFragment foodMenuFragment = new FoodMenuFragment();
                replaceFragment(foodMenuFragment, R.id.content_main);
                updateToolbarTitle(foodMenuFragment);
                loginActivity.createImage(R.color.cardview_light_background, backgroundImage);
                break;
            case 16:
                AdsFragment adsFragment = new AdsFragment();
                replaceFragment(adsFragment, R.id.content_main);
                updateToolbarTitle(adsFragment);
                loginActivity.createImage(R.color.cardview_light_background, backgroundImage);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
