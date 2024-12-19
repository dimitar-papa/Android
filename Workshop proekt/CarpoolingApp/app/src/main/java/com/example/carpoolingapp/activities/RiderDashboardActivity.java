package com.example.carpoolingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolingapp.R;
import com.example.carpoolingapp.adapters.RideAdapter;
import com.example.carpoolingapp.database.DBHelper;
import com.example.carpoolingapp.models.RidePost;

import java.util.ArrayList;
import java.util.List;

public class RiderDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RideAdapter rideAdapter;
    private List<RidePost> availableRides;
    private List<RidePost> filteredRides;
    private EditText filterEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_dashboard);

        recyclerView = findViewById(R.id.recyclerViewAvailableRides);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filterEditText = findViewById(R.id.editTextFilter);

        fetchAvailableRides();

        rideAdapter = new RideAdapter(filteredRides, new RideAdapter.OnRideClickListener() {
            @Override
            public void onRideClick(RidePost ridePost) {
                Intent intent = new Intent(RiderDashboardActivity.this, MapActivity.class);
                intent.putExtra("ride", ridePost);  // Pass the RidePost object to the next activity
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(rideAdapter);

        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filterRides(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void fetchAvailableRides() {
        DBHelper dbHelper = new DBHelper(this);
        availableRides = dbHelper.getAvailableRides();
        filteredRides = new ArrayList<>(availableRides);
    }

    private void filterRides(String query) {
        List<RidePost> filteredList = new ArrayList<>();

        for (RidePost ride : availableRides) {
            if (ride.getStartLocation().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(ride);
            }
        }

        filteredRides.clear();
        filteredRides.addAll(filteredList);
        rideAdapter.notifyDataSetChanged();
    }
}
