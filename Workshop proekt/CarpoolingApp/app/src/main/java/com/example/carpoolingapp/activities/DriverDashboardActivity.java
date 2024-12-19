package com.example.carpoolingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carpoolingapp.R;

public class DriverDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);
    }

    public void createNewRide(View view) {
        Intent intent = new Intent(this, RideDetailsActivity.class);
        startActivity(intent);
    }

    public void viewExistingRides(View view) {
        Intent intent = new Intent(this, com.example.carpoolingapp.activities.ActiveRidesActivity.class);
        startActivity(intent);
    }
}
