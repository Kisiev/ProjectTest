package com.example.a1.projecttest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.a1.projecttest.Entities.ChildEntity;
import com.example.a1.projecttest.fragments.FeedFragment;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.utils.CircleTransform;
import com.facebook.stetho.Stetho;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.List;

@EActivity (R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    NavigationView navigationView;
    ImageView imageView;
    TextView nameTextNavView;
    TextView emailTextNavView;
    TextView idTextNavView;
    UserLoginSession session;

    @AfterViews
    public void main() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/opensans.ttf");
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        replaceFragment(new FeedFragment(), R.id.content_main);
        setTitle(getString(R.string.life_feed));

        View headerView = navigationView.getHeaderView(0);
        session = new UserLoginSession(getApplicationContext());
        imageView = (ImageView) headerView.findViewById(R.id.imageView);
        nameTextNavView = (TextView) headerView.findViewById(R.id.name_text_view);
        emailTextNavView = (TextView) headerView.findViewById(R.id.email_text_view);
        idTextNavView = (TextView) headerView.findViewById(R.id.id_user_text_view);
        nameTextNavView.setTypeface(typeface);
        emailTextNavView.setTypeface(typeface);
        idTextNavView.setTypeface(typeface);
        setNavigationViewItem();
        saveGlideParam(imageView, MainActivity.this, null, R.mipmap.avatar);
        setMenu(navigationView);

        initStetho();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_main);
                if (f != null) {
                    updateToolbarTitle(f);
                } else finish();
            }
        });
    }

    private void setNavigationViewItem(){
        nameTextNavView.setText(session.getUserName() + " " + session.getUserSurname());
        emailTextNavView.setText(session.getLogin());
        idTextNavView.setText("Идентификатор: " + session.getID());
    }

    private void initStetho(){
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build());
    }
    private void updateToolbarTitle(Fragment fragment) {
        String fragmentClassName = fragment.getClass().getName();

        if (fragmentClassName.equals(FeedFragment.class.getName())) {
            setTitle(getString(R.string.life_feed));
        } else if (fragmentClassName.equals(VospitannikFragment.class.getName())) {
            setTitle(getString(R.string.list_rasp));
        }
    }


    public static void setMenu(NavigationView navigationView){
        Menu menu = navigationView.getMenu();
        menu.clear();
        menu.add(Menu.NONE, 223, 0, "Живая лента");
        menu.getItem(0).setIcon(R.drawable.ic_event_note_black_24dp);
        menu = menu.addSubMenu("Дети");
        List<ChildEntity> list = ChildEntity.selectChild();
        for (int i = 0; i < list.size(); i ++){
            menu.add(Menu.NONE, 223, i + 1, list.get(i).getName());
            menu.getItem(i).setIcon(R.drawable.ic_person_black_24dp);
        }
        Menu chatParentsMenu = navigationView.getMenu();
        chatParentsMenu = chatParentsMenu.addSubMenu("Родители");
        chatParentsMenu.add(Menu.NONE, 123, 3, "Валерий Павлович");
        chatParentsMenu.getItem(0).setIcon(R.drawable.ic_receipt_black_24dp);
        Menu exitMenu = navigationView.getMenu();
        exitMenu.add(Menu.NONE, 123, 4, "Выход");
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

    public static void saveGlideParam(ImageView imageView, Context context, Uri imagePath, int image) {

        Glide.with(context)
                .load(imagePath == null? image : imagePath)
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
                updateToolbarTitle(feedFragment);
                break;
            case 1:
                VospitannikFragment vs = new VospitannikFragment();
                replaceFragment(vs, R.id.content_main);
                updateToolbarTitle(vs);
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
