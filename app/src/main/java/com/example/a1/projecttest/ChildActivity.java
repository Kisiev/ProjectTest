package com.example.a1.projecttest;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.a1.projecttest.rest.RestService;
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

import java.io.IOException;

@EActivity (R.layout.child_activity)
public class ChildActivity extends Activity {
    private LocationManager locationManager;
    TextView lit;
    TextView lon;

    @AfterViews
    public void main () {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        lit = (TextView) findViewById(R.id.litTV);
        lon = (TextView) findViewById(R.id.longTV);
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
