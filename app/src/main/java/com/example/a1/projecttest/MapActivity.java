package com.example.a1.projecttest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapActivity extends AppCompatActivity {
    GoogleMap googleMap;
    StreetViewPanorama m_StreetView;

    //координаты для маркера
    private static final double TARGET_LATITUDE = 17.893366;
    private static final double TARGET_LONGITUDE = 19.511868;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        createMapView();
        addMarker();
        createStreetView();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                /**
                 * Ensure the street view has been initialise correctly and
                 * pass it through the selected lat/long co-ordinates.
                 */
                if (m_StreetView != null) {

                    /**
                     * Hide the map view to expose the street view.
                     */
                    Fragment mapView = getFragmentManager().findFragmentById(R.id.mapView);
                    getFragmentManager().beginTransaction().hide(mapView).commit();

                    /** Passed the tapped location through to the Street View **/
                    m_StreetView.setPosition(latLng);
                }
            }
        });
        //добавляем на карту свое местоположение
        googleMap.setMyLocationEnabled(true);
    }
    private void createStreetView() {
        m_StreetView = ((StreetViewPanoramaFragment)
                getFragmentManager().findFragmentById(R.id.streetView))
                .getStreetViewPanorama();
    }
    //создаем карту
    private void createMapView(){

        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }

    }
    //добавляем маркер на карту
    private void addMarker(){

        double lat = TARGET_LATITUDE;
        double lng = TARGET_LONGITUDE;
        //устанавливаем позицию и масштаб отображения карты
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lng))
                .zoom(15)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        googleMap.animateCamera(cameraUpdate);
        if(null != googleMap){
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title("Mark")
                    .draggable(false)
            );

        }
    }
}

