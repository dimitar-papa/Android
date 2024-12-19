package com.example.carpoolingapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carpoolingapp.models.RidePost;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "carpooling.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_USERS = "users";
    public static final String TABLE_RIDES = "rides";

    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PAYMENT = "payment_info";
    public static final String COLUMN_USER_TYPE = "user_type";

    public static final String COLUMN_RIDE_ID = "ride_id";
    public static final String COLUMN_START_LOCATION = "start_location";
    public static final String COLUMN_DESTINATION = "destination";
    public static final String COLUMN_DEPARTURE_TIME = "departure_time";
    public static final String COLUMN_CAR_MODEL = "car_model";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_DRIVER_ID = "driver_id";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_IS_AVAILABLE = "is_available";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_PAYMENT + " TEXT,"
                + COLUMN_USER_TYPE + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_RIDES_TABLE = "CREATE TABLE " + TABLE_RIDES + "("
                + COLUMN_RIDE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_START_LOCATION + " TEXT,"
                + COLUMN_DESTINATION + " TEXT,"
                + COLUMN_DEPARTURE_TIME + " TEXT,"
                + COLUMN_CAR_MODEL + " TEXT,"
                + COLUMN_COST + " REAL,"
                + COLUMN_DRIVER_ID + " INTEGER,"
                + COLUMN_RATING + " REAL,"
                + COLUMN_IS_AVAILABLE + " INTEGER DEFAULT 1,"
                + "FOREIGN KEY(" + COLUMN_DRIVER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        db.execSQL(CREATE_RIDES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE rides ADD COLUMN " + COLUMN_IS_AVAILABLE + " INTEGER DEFAULT 1");
        }
    }

    public List<RidePost> getAvailableRides() {
        List<RidePost> rideList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM rides WHERE is_available = 1", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String startLocation = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_LOCATION));
                String destination = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATION));
                String departureTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTURE_TIME));
                String carModel = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAR_MODEL));
                double cost = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_COST));

                rideList.add(new RidePost(startLocation, destination, departureTime, carModel, cost));
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return rideList;
    }


}
