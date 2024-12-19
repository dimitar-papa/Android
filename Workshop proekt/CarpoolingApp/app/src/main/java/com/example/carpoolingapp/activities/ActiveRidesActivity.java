package com.example.carpoolingapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolingapp.R;
import com.example.carpoolingapp.adapters.RideAdapter;
import com.example.carpoolingapp.database.DBHelper;
import com.example.carpoolingapp.models.RidePost;

import java.util.ArrayList;

public class ActiveRidesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RideAdapter rideAdapter;
    private ArrayList<RidePost> activeRidesList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_rides);

        recyclerView = findViewById(R.id.recyclerViewActiveRides);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);
        activeRidesList = new ArrayList<>();

        loadActiveRides();
    }

    private void loadActiveRides() {

        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM " + DBHelper.TABLE_RIDES, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {

                String startLocation = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_START_LOCATION));
                String destination = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DESTINATION));
                String departureTime = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DEPARTURE_TIME));
                String carModel = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CAR_MODEL));
                double cost = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_COST));

                RidePost ridePost = new RidePost(startLocation, destination, departureTime, carModel, cost);
                activeRidesList.add(ridePost);
            } while (cursor.moveToNext());
            cursor.close();


            rideAdapter = new RideAdapter(activeRidesList, new RideAdapter.OnRideClickListener() {
                @Override
                public void onRideClick(RidePost ridePost) {
                    Intent intent = new Intent(ActiveRidesActivity.this, RideDetailsActivity.class);
                    intent.putExtra("ride", ridePost);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(rideAdapter);
        } else {
            Toast.makeText(this, "No active rides found", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBackToDashboard(View view) {
        finish();
    }
}
