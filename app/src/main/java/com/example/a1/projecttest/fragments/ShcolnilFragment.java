package com.example.a1.projecttest.fragments;
        import android.app.Activity;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;

        import com.example.a1.projecttest.MainActivity;
        import com.google.android.gms.maps.CameraUpdate;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.StreetViewPanorama;
        import com.google.android.gms.maps.StreetViewPanoramaFragment;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.example.a1.projecttest.R;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;


public class ShcolnilFragment extends Fragment {
    GoogleMap googleMap;
    StreetViewPanorama m_StreetView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);
        return view;
    }
}
