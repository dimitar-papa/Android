package com.example.carpoolingapp.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carpoolingapp.R;
import com.example.carpoolingapp.database.DBHelper;

public class RideDetailsActivity extends AppCompatActivity {

    private EditText etPickupLocation, etDropoffLocation, etCarModel, etRideTime, etCost;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);

        etPickupLocation = findViewById(R.id.etPickupLocation);
        etDropoffLocation = findViewById(R.id.etDropoffLocation);
        etRideTime = findViewById(R.id.etRideTime);
        etCarModel = findViewById(R.id.etCarModel);
        etCost = findViewById(R.id.etCost);

        dbHelper = new DBHelper(this);
    }

    public void saveRideDetails(View view) {
        String pickupLocation = etPickupLocation.getText().toString().trim();
        String dropoffLocation = etDropoffLocation.getText().toString().trim();
        String rideTime = etRideTime.getText().toString().trim();
        String carModel = etCarModel.getText().toString().trim();
        String Cost = etCost.getText().toString().trim();

        if (pickupLocation.isEmpty() || dropoffLocation.isEmpty() || rideTime.isEmpty() || carModel.isEmpty() || Cost.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_START_LOCATION, pickupLocation);
        values.put(DBHelper.COLUMN_DESTINATION, dropoffLocation);
        values.put(DBHelper.COLUMN_DEPARTURE_TIME, rideTime);
        values.put(DBHelper.COLUMN_CAR_MODEL, carModel);
        values.put(DBHelper.COLUMN_COST, Cost);

        long result = db.insert(DBHelper.TABLE_RIDES, null, values);

        if (result == -1) {
            Toast.makeText(this, "Failed to save ride details", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ride details saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        }

        db.close();
    }
}
