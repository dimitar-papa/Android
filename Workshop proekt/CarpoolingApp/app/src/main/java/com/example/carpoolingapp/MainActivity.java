package com.example.carpoolingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carpoolingapp.R;
import com.example.carpoolingapp.activities.LoginActivity;
import com.example.carpoolingapp.activities.RegistrationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void openRegistration(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }
}
