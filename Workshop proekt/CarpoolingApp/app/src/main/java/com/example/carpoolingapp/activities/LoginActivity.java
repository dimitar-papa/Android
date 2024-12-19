package com.example.carpoolingapp.activities;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.carpoolingapp.database.DBHelper;
import com.example.carpoolingapp.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginUser(View view) {
        EditText etUsername = findViewById(R.id.edtUsername);
        EditText etPassword = findViewById(R.id.edtPassword);

        if (etUsername == null || etPassword == null) {
            Toast.makeText(this, "Error: Missing fields in layout", Toast.LENGTH_SHORT).show();
            return;
        }

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT * FROM " + DBHelper.TABLE_USERS +
                            " WHERE " + DBHelper.COLUMN_USERNAME + " = ? AND " + DBHelper.COLUMN_PASSWORD + " = ?",
                    new String[]{username, password}
            );

            if (cursor != null && cursor.moveToFirst()) {
                String userType = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_USER_TYPE));
                Toast.makeText(this, "Login successful as " + userType, Toast.LENGTH_SHORT).show();

                Intent intent;
                if ("driver".equalsIgnoreCase(userType)) {
                    intent = new Intent(this, DriverDashboardActivity.class);
                } else if ("rider".equalsIgnoreCase(userType)) {
                    intent = new Intent(this, RiderDashboardActivity.class);
                } else {
                    Toast.makeText(this, "Unknown user type", Toast.LENGTH_SHORT).show();
                    return;
                }

                intent.putExtra("username", username);
                startActivity(intent);

                finish();
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error during login", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }
}
