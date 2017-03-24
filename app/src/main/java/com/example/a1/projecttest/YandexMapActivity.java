package com.example.a1.projecttest;


import android.*;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

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
import ru.yandex.yandexmapkit.overlay.location.MyLocationOverlay;
import ru.yandex.yandexmapkit.utils.GeoPoint;

@EActivity(R.layout.activity_map_yandex)
public class YandexMapActivity extends Activity{

    @ViewById(R.id.map_yandex)
    public MapView webView;
    private LocationManager locationManager;
    private GetListUsers getListUsers;
    private Handler handler;
    private Runnable runnable;
    private int calbacks = 0;
    private int calls = 0;
    private LinearLayout view;
    private OverlayItem overlayItem;
    private Overlay overlay;
    @AfterViews
    void main () {


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (calls > 1) finish();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * 15, 1, locationListener);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * 15, 1,
                    locationListener);

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

        if (getListUsers != null) {
            if (overlay != null)
                overlay.clearOverlayItems();
            MapController mapController = webView.getMapController();
            if (calbacks <=1) {
                mapController.setPositionAnimationTo(new GeoPoint(Double.valueOf(getListUsers.getCoordinateX()), Double.valueOf(getListUsers.getCoordinateY())));
                mapController.setZoomCurrent(17);
            }

            OverlayManager overlayManager = mapController.getOverlayManager();
            OverlayManager balloon = mapController.getOverlayManager();
            overlay = new Overlay(mapController);
            Overlay overlay1 = new Overlay(mapController);
            Resources res = getResources();
            BalloonOverlay balloonOverlay = new BalloonOverlay(mapController);
            BalloonItem  balloonItem = new BalloonItem(this, new GeoPoint(44,44));
            balloonOverlay.setBalloonItem(balloonItem);
            Drawable bitmap = getResources().getDrawable(R.drawable.ic_place_black_24dp);
            overlayItem = new OverlayItem(new GeoPoint(Double.valueOf(getListUsers.getCoordinateX()), Double.valueOf( getListUsers.getCoordinateY())), bitmap);


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
            getListUsers = (restService.viewListInMainFragmenr(String.valueOf(12)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
