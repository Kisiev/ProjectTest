package com.example.a1.projecttest;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.a1.projecttest.fragments.FeedFragment;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.utils.CircleTransform;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.facebook.stetho.Stetho;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity (R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    NavigationView navigationView;
    ImageView imageView;

    @AfterViews
    public void main() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //replaceFragment(new FeedFragment(), R.id.content_main);
        setTitle(getString(R.string.life_feed));
        View headerView = navigationView.getHeaderView(0);
        imageView = (ImageView) headerView.findViewById(R.id.imageView);
        saveGlideParam(imageView, MainActivity.this, R.mipmap.mom);
        setMenu();
        initStetho();
    }

    private void initStetho(){
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build());
    }
    private void updateToolbarTitle(Fragment fragment, String title) {
          String fragmentClassName = fragment.getClass().getName();
          if (fragmentClassName.equals(fragment.getClass().getName())) {
              setTitle(title);
          }
    }


    private void setMenu(){
        Menu menu = navigationView.getMenu();
        menu.add(Menu.NONE, 223, 0, "Живая лента");
        menu = menu.addSubMenu("Дети");
        menu.add(Menu.NONE, 223, 1, "Андрей");
        menu.add(Menu.NONE, 223, 2, "Виктор");
        Menu menu1 = navigationView.getMenu();
        menu1 = menu1.addSubMenu("Родители");
        menu1.add(Menu.NONE, 123, 3, "Валерий Павлович");
        Menu menu2 = navigationView.getMenu();
        menu2.add(Menu.NONE, 123, 4, "Выход");
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

    public static void saveGlideParam(ImageView imageView, Context context, int imagePath) {

        Glide.with(context)
                .load(imagePath)
                .bitmapTransform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new BitmapImageViewTarget(imageView).getView());
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
            startActivity(new Intent(MainActivity.this, YandexMapActivity_.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        int id = item.getOrder();
        switch (id){
            case 0:
               FeedFragment feedFragment = new FeedFragment();
                replaceFragment(feedFragment, R.id.content_main);
                updateToolbarTitle(feedFragment, "Живая лента");
                break;
            case 1:
                VospitannikFragment vs = new VospitannikFragment();
                replaceFragment(vs, R.id.content_main);
                updateToolbarTitle(vs, getString(R.string.status_child));
                break;
            case 2:
              //  ShcolnilFragment shcolnilFragment = new ShcolnilFragment();
              //  replaceFragment(shcolnilFragment, R.id.content_main);
                LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                boolean enabled = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (enabled) {
                    Intent intent = new Intent(MainActivity.this, MapActivity_.class);
                    startActivity(intent);
                }
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, ChatActivity_.class));
              break;
            case 4:
                UserLoginSession session = new UserLoginSession(this);
                session.clear();
                startActivity(new Intent(this, LoginActivity_.class));
                finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
