package com.example.a1.projecttest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


import com.example.a1.projecttest.rest.Models.GetListUsers;
import com.example.a1.projecttest.rest.RestService;
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

import java.io.IOException;

@EActivity (R.layout.activity_maps)
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    private LocationManager locationManager;
    LatLng latLng;
    GoogleMap googleMap;
    GetListUsers getListUsers;
    @AfterViews
    protected void main() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getFragmentManager().getFragments();
        mapFragment.getMapAsync(this);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.draggable(true);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        getCoordinates();

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 15, 1, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 15, 1,
                locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
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
            showLocation(locationManager.getLastKnownLocation(provider));
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

    public void showLocation(final Location location) {
        if (location == null)
            return;
        Thread trThread = new Thread();
        trThread.start();
        getCoordinates();
        try {
            trThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(getListUsers.getCoordinateX()), Double.valueOf(getListUsers.getCoordinateY()))));

        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(getListUsers.getCoordinateX()), Double.valueOf(getListUsers.getCoordinateY()))));

        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(Double.valueOf(getListUsers.getCoordinateX()), Double.valueOf(getListUsers.getCoordinateY())))
                .zoom(400)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.animateCamera(cameraUpdate);
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