package com.example.a1.projecttest;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.a1.projecttest.entities.GetAllKidEntity;
import com.example.a1.projecttest.fragments.FeedFragment;
import com.example.a1.projecttest.fragments.FoodMenuParent;
import com.example.a1.projecttest.fragments.NewsFragment;
import com.example.a1.projecttest.fragments.VospitannikFragment;
import com.example.a1.projecttest.rest.Models.GetAllKidsModel;
import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.Models.GetScheduleByKidIdModel;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.sync.MyChildSyncJob;
import com.example.a1.projecttest.utils.CircleTransform;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.facebook.stetho.Stetho;
import com.google.gson.JsonSyntaxException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@EActivity (R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    DrawerLayout drawer;
    NavigationView navigationView;
    ImageView imageView;
    TextView nameTextNavView;
    TextView emailTextNavView;
    TextView idTextNavView;
    UserLoginSession session;
    List<GetAllKidsModel> getAllKidsModelsOn;
    Observable<List<GetAllKidsModel>> getAllKidsObserver;
    Observable<String> getStatusByKidAdd;
    GetScheduleByKidIdModel getScheduleByKidIdModel;
    GetListUsers getUserData;
    NotificationManager mNotificationManager;
    String refreshingFragment;
    SwipeRefreshLayout swipeRefreshLayout;
    MenuItem menuItem;
    Typeface typeface;
    int fragmentName = 0;
    Dialog dialog;
    TextView headerChildTextView;
    EditText idChildEditText;
    EditText idParentEditText;
    Button cancelButton;
    Button sendButton;
    public void getAllKid(){
        RestService restService = new RestService();
        UserLoginSession userLoginSession = new UserLoginSession(this);
        try {
            getAllKidsObserver = restService.getKidByParentId(userLoginSession.getID());
            getAllKidsObserver.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<GetAllKidsModel>>() {
                        @Override
                        public void onCompleted() {
                            setMenu(navigationView);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getApplication(), "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(List<GetAllKidsModel> getAllKidsModels) {
                                getAllKidsModelsOn = getAllKidsModels;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void threadGetGroup(final String id){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getKidGroup(id);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void getKidGroup(String id){
        RestService restService = new RestService();
        try {
            getScheduleByKidIdModel = restService.getScheduleByKidIdModels(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putSerializable(ConstantsManager.SAVE_INSTAANTS_GET_KIDS, (Serializable) getAllKidsModelsOn);
    }

    public void clearSync(){
        int idJob = MyChildSyncJob.schedulePeriodicJob();
                for (int i = 1; i < idJob; i++)
                    MyChildSyncJob.cancelJob(i);
    }

    @AfterViews
    public void main() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clearSync();
        typeface = Typeface.createFromAsset(getAssets(), "font/SF-UI-Text-Regular.ttf");
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        session = new UserLoginSession(getApplicationContext());
/*        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
                if (refreshingFragment.equals(FeedFragment.class.getName())) {
                    replaceFragment(new FeedFragment(), R.id.content_main);
                    setTitle(getString(R.string.life_feed));
                } else if (refreshingFragment.equals(VospitannikFragment.class.getName())){
                    takeKidFragment();
                }
            }
        });
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorGreen),
                getResources().getColor(R.color.colorYellow),
                getResources().getColor(R.color.colorBlue),
                getResources().getColor(R.color.colorOrange),
                getResources().getColor(R.color.colorViolet));*/
        imageView = (ImageView) headerView.findViewById(R.id.imageView);
        nameTextNavView = (TextView) headerView.findViewById(R.id.name_text_view);
        emailTextNavView = (TextView) headerView.findViewById(R.id.email_text_view);
        idTextNavView = (TextView) headerView.findViewById(R.id.id_user_text_view);
        nameTextNavView.setTypeface(typeface);
        emailTextNavView.setTypeface(typeface);
        idTextNavView.setTypeface(typeface);
        setNavigationViewItem();
        saveGlideParam(imageView, MainActivity.this, null, R.mipmap.avatar);


        initStetho();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_main);
                if (f != null) {
                    refreshingFragment = f.getClass().getName();
                    updateToolbarTitle(f);
                } else finish();
            }
        });
        replaceFragment(new NewsFragment(), R.id.content_main);
        setTitle(getString(R.string.life_feed));
        getAllKid();
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
            setTitle(getAllKidsModelsOn.get(fragmentName).getName());
        } else if (fragmentClassName.equals(FoodMenuParent.class.getName())) {
            setTitle(getString(R.string.foot_menu));
        }
    }


    public void setMenu(NavigationView navigationView){
        Menu menu = navigationView.getMenu();
        menu.clear();
        menu.add(Menu.NONE, 223, 0, "Новости");
        menu.getItem(0).setIcon(R.drawable.ic_event_note_black_24dp);
        menu.add(Menu.NONE, 223, 1, "Меню питания");
        menu.getItem(1).setIcon(R.drawable.ic_local_pizza_black_24dp);
        menu = menu.addSubMenu("Дети");
        for (int i = 0; i < getAllKidsModelsOn.size(); i ++){
            menu.add(Menu.NONE, Integer.parseInt(getAllKidsModelsOn.get(i).getId()), ConstantsManager.ID_MENU_ITEM, getAllKidsModelsOn.get(i).getName());
            menu.getItem(i).setIcon(R.drawable.ic_person_black_24dp);
        }
        Menu chatParentsMenu = navigationView.getMenu();
        chatParentsMenu = chatParentsMenu.addSubMenu("Родители");
        chatParentsMenu.add(Menu.NONE, 123, 3, "Иванов Иван");
        chatParentsMenu.getItem(0).setIcon(R.drawable.ic_receipt_black_24dp);
        Menu exitMenu = navigationView.getMenu();
        exitMenu.add(Menu.NONE, 123, 4, "Выход");
    }

    public void replaceFragment(Fragment fragment, int id) {
        String backStackName = fragment.getClass().getName();
        refreshingFragment = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            showDialogAddChild();
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
        menuItem = item;
        int id = item.getOrder();
        switch (id){
            case 0:
                NewsFragment newsFragment = new NewsFragment();
                replaceFragment(newsFragment, R.id.content_main);
                updateToolbarTitle(newsFragment);
                break;
            case ConstantsManager.ID_MENU_ITEM:
                takeKidFragment();
                break;
            case 2:
              //  ShcolnilFragment shcolnilFragment = new ShcolnilFragment();
              //  replaceFragment(shcolnilFragment, R.id.content_main);
                LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                boolean enabled = service
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (enabled) {
                   // Intent intent = new Intent(MainActivity.this, MapActivity_.class);
                    //startActivity(intent);
                }
                break;
            case 3:
                //startActivity(new Intent(this, ChatActivity_.class));
              break;
            case 4:
                UserLoginSession session = new UserLoginSession(this);
                session.clear();
                startActivity(new Intent(this, LoginActivity_.class));
                finish();break;
            case 1:
                FoodMenuParent foodMenuParent = new FoodMenuParent();
                replaceFragment(foodMenuParent, R.id.content_main);
                updateToolbarTitle(foodMenuParent);
                break;
        }

        switch (item.getItemId()){

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void takeKidFragment(){
        if (menuItem != null)
        for (int i = 0; i < getAllKidsModelsOn.size(); i ++) {
            if (menuItem.getItemId() == Integer.valueOf(getAllKidsModelsOn.get(i).getId())){
                threadGetUserData(getAllKidsModelsOn.get(i).getId());
                if (getUserData != null)
                    if (getUserData.getEmail() == null){
                        UserLoginSession userLoginSession = new UserLoginSession(this);
                        threadGetGroup(getAllKidsModelsOn.get(i).getId());
                        if (getScheduleByKidIdModel != null)
                            userLoginSession.saveKidId(getScheduleByKidIdModel.getGroupId(), getScheduleByKidIdModel.getUserId());
                        VospitannikFragment vs = new VospitannikFragment();
                        replaceFragment(vs, R.id.content_main);
                        fragmentName = i;
                        updateToolbarTitle(vs);
                    } else {
                        Intent intent = new Intent(this, YandexMapActivity_.class);
                        intent.putExtra(ConstantsManager.USER_ID_AND_COORDINATES, getUserData.getId());
                        intent.putExtra(ConstantsManager.NAME_CHILD, getUserData.getName());
                        startActivity(intent);
                    }
            }

        }
    }

    public void threadGetUserData(final String id){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getKidCoordinates(id);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getKidCoordinates(String id){
        RestService restService = new RestService();
        try {
            getUserData = restService.getUserById(id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e){
            e.printStackTrace();
        }
    }

    public void postRequestToAdd(){
        RestService restService = new RestService();
        try {
            getStatusByKidAdd = restService.addKidToParent(idChildEditText.getText().toString(), session.getID());
            getStatusByKidAdd.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDialogAddChild(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_child_dialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        headerChildTextView = (TextView) dialog.findViewById(R.id.text_add_kid_text_view);
        idChildEditText = (EditText) dialog.findViewById(R.id.kid_id_edit_text);
        cancelButton = (Button) dialog.findViewById(R.id.cancel_bt_dialog);
        sendButton = (Button) dialog.findViewById(R.id.send_bt_dialog);
        headerChildTextView.setTypeface(typeface);
        idChildEditText.setTypeface(typeface);
        cancelButton.setTypeface(typeface);
        sendButton.setTypeface(typeface);
        cancelButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_bt_dialog:
                dialog.dismiss();
                break;
            case R.id.send_bt_dialog:
                postRequestToAdd();
                break;
        }
    }
}
