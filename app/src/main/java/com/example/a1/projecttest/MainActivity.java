package com.example.a1.projecttest;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.a1.projecttest.Entities.ChildrenRoleEntity;
import com.example.a1.projecttest.fragments.ShcolnilFragment;
import com.example.a1.projecttest.fragments.VospitannikFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }
    /*  private void updateToolbarTitle(Fragment fragment) {
          String fragmentClassName = fragment.getClass().getName();
          if (fragmentClassName.equals(ExpensesFragment.class.getName())) {
              setTitle(getString(R.string.expenses_header_nav));
              navigationView.setCheckedItem(R.id.spendItem);
          } else if (fragmentClassName.equals(CategoryFragment.class.getName())) {
              setTitle(getString(R.string.category_header_nav));
              navigationView.setCheckedItem(R.id.categoryItem);
          } else if (fragmentClassName.equals(StatisticFragment.class.getName())) {
              setTitle(getString(R.string.statistic_header_nav));
              navigationView.setCheckedItem(R.id.statItem);
          } else if (fragmentClassName.equals(SettingFragment.class.getName())) {
              setTitle(getString(R.string.setting_header_nav));
              navigationView.setCheckedItem(R.id.settingItem);
          }
      }*/
    private void replaceFragment(Fragment fragment, int id) {
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Menu menu = navigationView.getMenu();
            menu.removeGroup(R.id.nave_menu);
            List<ChildrenRoleEntity> list = new ArrayList<ChildrenRoleEntity>();
            List<String> names = new ArrayList<>();
            names.add("Настя");
            names.add("Коля");
            names.add("Петя");
            names.add("Андрей");
            names.add("Саня");
            ChildrenRoleEntity childrenRoleEntity = new ChildrenRoleEntity();

            for (int i = 0; i < 10; i ++) {
                childrenRoleEntity.setName(names.get((int) rnd(0, 4)));
                childrenRoleEntity.setRole((int) rnd(1, 2));
                menu.add(R.id.nave_menu, 123, childrenRoleEntity.getRole(), String.valueOf(childrenRoleEntity.getName()));
                menu.getItem(i).setIcon(R.drawable.ic_menu_camera);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public long rnd(long min, long max)
    {
        max -= min;
        final double random = Math.random();
        return Math.round((random * max) + min);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        int id = item.getOrder();
        switch (id){
            case 1:
                VospitannikFragment vs = new VospitannikFragment();
                replaceFragment(vs, R.id.content_main);
                break;
            case 2:
                ShcolnilFragment shcolnilFragment = new ShcolnilFragment();
                replaceFragment(shcolnilFragment, R.id.content_main);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
