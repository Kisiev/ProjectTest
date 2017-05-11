package com.example.a1.projecttest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.RestService;
import com.example.a1.projecttest.utils.ConstantsManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Touch;

import java.io.IOException;
import java.security.Permission;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@EActivity(R.layout.activity_maps)
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int PERMISSION_REQUEST_CODE = 1;
    SupportMapFragment mapFragment;
    private LocationManager locationManager;
    GoogleMap googleMap;
    GetListUsers getListUsers;
    public Handler handler;
    Runnable runnable;
    int calbacks = 0;
    int calls = 0;
    LinearLayout view;

    @AfterViews
    protected void main() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getFragmentManager().getFragments();
        mapFragment.getMapAsync(this);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.draggable(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (calls > 1) finish();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            return;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setPeriodicTime() {
        calbacks++;

        if (getListUsers != null) {
            view.setVisibility(View.GONE);
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(
                    getListUsers.getCoordinateX()), Double.valueOf(getListUsers.getCoordinateY()))));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(Double.valueOf(getListUsers.getCoordinateX()), Double.valueOf(getListUsers.getCoordinateY())))
                    .zoom(18)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }

        handler.postDelayed(runnable, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            if (calls > 1) onBackPressed();
            if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                showLocation(locationManager.getLastKnownLocation(provider));
            } else {
                calls++;
                ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
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
            getListUsers = (restService.getUserById(String.valueOf(12)));
        } catch (IOException e) {
            e.printStackTrace();
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

    public void showLocation(final Location location) {
        if (location == null)
            return;
       /* if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(getListUsers.getCoordinateX()), Double.valueOf(getListUsers.getCoordinateY()))));

        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(getListUsers.getCoordinateX()), Double.valueOf(getListUsers.getCoordinateY()))));

        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(Double.valueOf(getListUsers.getCoordinateX()), Double.valueOf(getListUsers.getCoordinateY())))
                .zoom(15)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.animateCamera(cameraUpdate);*/
    }


    @Override
    public void onMapReady(final GoogleMap map) {
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                map.addMarker(new MarkerOptions().position(latLng));
            }
        });

        this.googleMap = map;
        // Add a marker in Sydney, Australia, and move the camera.
    }

}