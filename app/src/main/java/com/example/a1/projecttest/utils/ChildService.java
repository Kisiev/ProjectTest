package com.example.a1.projecttest.utils;


import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.a1.projecttest.rest.RestService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EService;

import java.io.IOException;
@EService
public class ChildService extends Service{
    LocationManager locationManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 5, 1, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 5, 1,
                locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
        Log.d("СЕРВИС", "Уничтожен");
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

    private void showLocation(final Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            Log.d("!!!!!!!!", String.valueOf(location.getLatitude()));
            Log.d("!!!!!!!!", String.valueOf(location.getLongitude()));
            postCoordinates(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            postCoordinates(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            Log.d("!!!!!!!!", String.valueOf(location.getLatitude()));
            Log.d("!!!!!!!!", String.valueOf(location.getLongitude()));
        }

    }

    @Background
    public void postCoordinates (String coordinateX, String coordinateY) {
        RestService restService = new RestService();
        String status;
        try {
            status = restService.setCoordinates(String.valueOf(12), coordinateX, coordinateY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
