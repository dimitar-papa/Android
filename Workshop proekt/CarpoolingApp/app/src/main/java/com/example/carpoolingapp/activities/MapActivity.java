package com.example.carpoolingapp.activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carpoolingapp.R;
import com.example.carpoolingapp.models.RidePost;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private RidePost ridePost;
    private GoogleMap mMap;
    private RatingBar ratingBar;
    private TextView tvStartLocation, tvDestination, tvDepartureTime, tvCarModel, tvCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ridePost = (RidePost) getIntent().getSerializableExtra("ride");

        tvStartLocation = findViewById(R.id.tvStartLocation);
        tvDestination = findViewById(R.id.tvDestination);
        tvDepartureTime = findViewById(R.id.tvDepartureTime);
        tvCarModel = findViewById(R.id.tvCarModel);
        tvCost = findViewById(R.id.tvCost);
        ratingBar = findViewById(R.id.ratingBar);
        tvStartLocation.setText(ridePost.getStartLocation());
        tvDestination.setText(ridePost.getDestination());
        tvDepartureTime.setText(ridePost.getDepartureTime());
        tvCarModel.setText(ridePost.getCarModel());
        tvCost.setText("$" + ridePost.getCost());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(ridePost.getStartLocation(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng startLocationLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(startLocationLatLng).title("Start Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocationLatLng, 12));
            } else {
                Toast.makeText(this, "Unable to find start location", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error fetching location", Toast.LENGTH_SHORT).show();
        }
    }
}
