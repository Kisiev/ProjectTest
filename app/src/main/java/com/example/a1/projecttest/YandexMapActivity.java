package com.example.a1.projecttest;


import android.*;
import android.app.Activity;
import android.content.ClipData;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.a1.projecttest.rest.Models.GetCoordinatesByUserIdModel;
import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.io.InputStream;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.map.GeoCode;
import ru.yandex.yandexmapkit.map.GeoCodeListener;
import ru.yandex.yandexmapkit.map.MapEvent;
import ru.yandex.yandexmapkit.map.OnMapListener;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonOverlay;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;
import ru.yandex.yandexmapkit.overlay.drag.DragAndDropItem;
import ru.yandex.yandexmapkit.overlay.drag.DragAndDropOverlay;
import ru.yandex.yandexmapkit.overlay.location.MyLocationOverlay;
import ru.yandex.yandexmapkit.utils.GeoPoint;


@EActivity(R.layout.activity_map_yandex)
public class YandexMapActivity extends Activity implements OnBalloonListener{

    @ViewById(R.id.map_yandex)
    public MapView webView;
    private LocationManager locationManager;
    private GetCoordinatesByUserIdModel getCoordinatesByUserIdModel;
    private Handler handler;
    private Runnable runnable;
    private int calbacks = 0;
    private int calls = 0;
    private LinearLayout view;
    private OverlayItem overlayItem;
    private Overlay overlay;
    private String userId;
    private String nameChildBalloon;
    @AfterViews
    void main () {


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ConstantsManager.SAVE_INSTAANTS_COORDINATES, getCoordinatesByUserIdModel);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getCoordinatesByUserIdModel = (GetCoordinatesByUserIdModel) savedInstanceState.getSerializable(ConstantsManager.SAVE_INSTAANTS_COORDINATES);
            getCoordinates();
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userId = bundle.getString(ConstantsManager.USER_ID_AND_COORDINATES);
            nameChildBalloon = bundle.getString(ConstantsManager.NAME_CHILD);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (calls > 1) finish();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * 15, 1, locationListener);
            if (Build.VERSION.SDK_INT > 20) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 1000 * 15, 1,
                        locationListener);
            }

            handler = new Handler();
            setPeriodicTime();
            runnable = new Runnable() {
                @Override
                public void run() {
                    getCoordinates();
                    setPeriodicTime();
                }
            };
            handler.postDelayed(runnable, 5000);
        } else {
            calls++;
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, ConstantsManager.PERMISSION_REQUEST_CODE);
            return;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        view = (LinearLayout) findViewById(R.id.view_loading);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            handler.removeCallbacks(runnable);
            locationManager.removeUpdates(locationListener);
        } catch (RuntimeException e){
            e.printStackTrace();
        }

    }

    private void setPeriodicTime() {
        calbacks++;

        if (getCoordinatesByUserIdModel != null) {
            if (overlay != null)
                overlay.clearOverlayItems();
            MapController mapController = webView.getMapController();
            if (calbacks <=3) {
                mapController.setPositionAnimationTo(new GeoPoint(Double.valueOf(getCoordinatesByUserIdModel.getCoordinateX()), Double.valueOf(getCoordinatesByUserIdModel.getCoordinateY())));
                mapController.setZoomCurrent(17);
            }

            OverlayManager overlayManager = mapController.getOverlayManager();
            overlay = new Overlay(mapController);

            Drawable bitmap = AppCompatDrawableManager.get().getDrawable(getApplication(), R.drawable.ic_person_pin_circle_black_24dp);

            overlayItem = new OverlayItem(new GeoPoint(Double.valueOf(getCoordinatesByUserIdModel.getCoordinateX()), Double.valueOf( getCoordinatesByUserIdModel.getCoordinateY())), bitmap);
            BalloonItem balloonItem = new BalloonItem(this, overlayItem.getGeoPoint());
            balloonItem.setOnBalloonListener(this);
            overlayItem.setBalloonItem(balloonItem);
            overlay.addOverlayItem(overlayItem);
            overlayManager.addOverlay(overlay);
        }

        handler.postDelayed(runnable, 5000);
    }


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            if (calls > 1) onBackPressed();
            if (ContextCompat.checkSelfPermission(YandexMapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            } else {
                calls++;
                ActivityCompat.requestPermissions(YandexMapActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, ConstantsManager.PERMISSION_REQUEST_CODE);
                return;
            }
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Background
    public void getCoordinates () {
        final RestService restService = new RestService();
        try {
            getCoordinatesByUserIdModel = (restService.getCoordinatesByUserIdModel(userId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBalloonViewClick(BalloonItem balloonItem, View view) {

    }

    @Override
    public void onBalloonShow(BalloonItem balloonItem) {
        balloonItem.setText(nameChildBalloon);
    }

    @Override
    public void onBalloonHide(BalloonItem balloonItem) {

    }

    @Override
    public void onBalloonAnimationStart(BalloonItem balloonItem) {

    }

    @Override
    public void onBalloonAnimationEnd(BalloonItem balloonItem) {

    }
}
