package com.example.carpoolingapp.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carpoolingapp.R;
import com.example.carpoolingapp.database.DBHelper;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Spinner spinnerUserType = findViewById(R.id.spinnerUserType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);
    }

    public void registerUser(View view) {
        EditText etUsername = findViewById(R.id.edtUsername);
        EditText etPassword = findViewById(R.id.edtPassword);
        EditText etPayment = findViewById(R.id.edtPaymentInfo);
        Spinner spinnerUserType = findViewById(R.id.spinnerUserType);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String payment = etPayment.getText().toString().trim();
        String userType = spinnerUserType.getSelectedItem().toString();


        if (username.isEmpty() || password.isEmpty() || payment.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USERNAME, username);
        values.put(DBHelper.COLUMN_PASSWORD, password);
        values.put(DBHelper.COLUMN_PAYMENT, payment);
        values.put(DBHelper.COLUMN_USER_TYPE, userType);

        long result = db.insert(DBHelper.TABLE_USERS, null, values);
        if (result == -1) {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

}
