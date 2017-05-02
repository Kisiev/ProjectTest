package com.example.a1.projecttest.vospitatel;

import android.app.Application;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1.projecttest.LoginActivity_;
import com.example.a1.projecttest.MainActivity;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.fragments.ArrivingFragment;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.example.a1.projecttest.vospitatel.fragments.RaspisanieFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import static ru.yandex.core.CoreApplication.getActivity;

@EActivity(R.layout.vospitatel_activity)
public class VospitatelMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    Application context;
    NavigationView navigationView;
    ImageView imageView;
    TextView nameTextNavView;
    TextView emailTextNavView;
    UserLoginSession session;
    TextView idTextNavView;
    Typeface typeface;
    @AfterViews
    void main(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/opensans.ttf");
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
        emailTextNavView = (TextView) headerView.findViewById(R.id.email_text_view);
        idTextNavView = (TextView) headerView.findViewById(R.id.id_user_text_view);
        session = new UserLoginSession(this);

        nameTextNavView.setTypeface(typeface);
        emailTextNavView.setTypeface(typeface);
        idTextNavView.setTypeface(typeface);

        MainActivity.saveGlideParam(imageView, this, null, R.mipmap.avatar);
        setNavigationViewItem();

        //ArrivingFragment arrivingFragment = new ArrivingFragment();
        //replaceFragment(arrivingFragment, R.id.content_main);
    }

    private void setNavigationViewItem() {
        nameTextNavView.setText(session.getUserName() + " " + session.getUserSurname());
        emailTextNavView.setText(session.getLogin());
        idTextNavView.setText("Идентификатор: " + session.getID());
    }

    private void setMenu(){
        Menu menu = navigationView.getMenu();
        menu.add(Menu.NONE, ConstantsManager.MENU_ID, 0, getString(R.string.attendance_text));
        menu.getItem(0).setIcon(R.drawable.ic_transfer_within_a_station_black_24dp);
        menu.add(Menu.NONE, ConstantsManager.MENU_ID, 1, getString(R.string.list_services_menu_item));
        menu.getItem(1).setIcon(R.drawable.ic_format_list_numbered_black_24dp);
        menu.add(Menu.NONE, ConstantsManager.MENU_ID, 2, getString(R.string.orders_menu_group));
        menu.getItem(2).setIcon(R.drawable.ic_assignment_black_24dp);
        menu.add(Menu.NONE, ConstantsManager.MENU_ID, 3, R.string.communic_text);
        menu.getItem(3).setIcon(R.drawable.ic_mail_black_24dp);
        menu.add(Menu.NONE, ConstantsManager.MENU_ID, 4, R.string.exit);
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
             //   ArrivingFragment arrivingFragment = new ArrivingFragment();
                //replaceFragment(arrivingFragment, R.id.content_main);
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
