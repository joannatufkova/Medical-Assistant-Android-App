package com.example.mymdassistantf110645.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mymdassistantf110645.database.model.Appointment;
import com.example.mymdassistantf110645.database.model.User;
import com.example.mymdassistantf110645.database.model.Vital;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myMDAssistant_db";
    private static final int DATABASE_VERSION = 4;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_EMAIL + " TEXT PRIMARY KEY,"
            + COLUMN_USER_USERNAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String COLUMN_APPOINTMENT_DOCTOR_NAME = "doctor_name";
    private static final String COLUMN_APPOINTMENT_SPECIALITY = "speciality";
    private static final String COLUMN_APPOINTMENT_DATE = "date";
    private static final String COLUMN_APPOINTMENT_TIME = "time";
    private static final String COLUMN_APPOINTMENT_NOTE = "note";
    private static final String COLUMN_APPOINTMENT_TYPE = "type";

    private static final String CREATE_APPOINTMENT_TABLE = "CREATE TABLE " + TABLE_APPOINTMENTS + "("
            + COLUMN_APPOINTMENT_DOCTOR_NAME + " TEXT,"
            + COLUMN_APPOINTMENT_SPECIALITY + " TEXT,"
            + COLUMN_APPOINTMENT_DATE + " TEXT,"
            + COLUMN_APPOINTMENT_TIME + " TEXT,"
            + COLUMN_APPOINTMENT_NOTE + " TEXT,"
            + COLUMN_APPOINTMENT_TYPE + " TEXT" + ")";


    private static final String TABLE_VITALS = "vitals";
    private static final String COLUMN_VITAL_ID = "id";
    private static final String COLUMN_TEMPERATURE = "temperature";
    private static final String COLUMN_PULSE = "pulse";
    private static final String COLUMN_BLOOD_PRESSURE = "bloodPressure";
    private static final String COLUMN_BLOOD_GLUCOSE = "bloodGlucose";
    private static final String COLUMN_RESPIRATORY_RATE = "respiratoryRate";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_HEIGHT = "height";

    private static final String CREATE_VITALS_TABLE = "CREATE TABLE " + TABLE_VITALS + "("
            + COLUMN_VITAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TEMPERATURE + " REAL,"
            + COLUMN_PULSE + " INTEGER,"
            + COLUMN_BLOOD_PRESSURE + " TEXT,"
            + COLUMN_BLOOD_GLUCOSE + " INTEGER,"
            + COLUMN_RESPIRATORY_RATE + " INTEGER,"
            + COLUMN_WEIGHT + " REAL,"
            + COLUMN_HEIGHT + " REAL" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_APPOINTMENT_TABLE);
        db.execSQL(CREATE_VITALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VITALS);
        onCreate(db);
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //hash the password using BCrypt
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()); //hashes the password with a generated salt

        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, hashedPassword);

        try {
            long result = db.insertOrThrow(TABLE_USERS, null, values);
            db.close();
            return result != -1; // returns true if insertion is successful
        } catch (SQLiteConstraintException e) {
            db.close();
            return false;
        }
    }


    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String storedHashedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD));
            cursor.close();
            db.close();
            return BCrypt.checkpw(password, storedHashedPassword);
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }


    /**
     * Checks if a user with the specified email already exists in the database.
     *
     * @param email The email to check.
     * @return true if a user with this email exists, false otherwise.
     */
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return exists;
    }

    /**
     * Checks if a user with the specified username already exists in the database.
     *
     * @param username The username to check.
     * @return true if a user with this username exists, false otherwise.
     */
    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return exists;
    }

    @SuppressLint("Range")
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USERNAME)));
                // Add other user fields as needed
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    public boolean addAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_APPOINTMENT_DOCTOR_NAME, appointment.getDoctorName());
        values.put(COLUMN_APPOINTMENT_SPECIALITY, appointment.getSpeciality());
        values.put(COLUMN_APPOINTMENT_DATE, appointment.getDate());
        values.put(COLUMN_APPOINTMENT_TIME, appointment.getTime());
        values.put(COLUMN_APPOINTMENT_NOTE, appointment.getNote());
        values.put(COLUMN_APPOINTMENT_TYPE, appointment.getType());

        long result = db.insert(TABLE_APPOINTMENTS, null, values);
        db.close();
        return result != -1;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_APPOINTMENTS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Appointment appointment = new Appointment(
                        cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_DOCTOR_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_SPECIALITY)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_TIME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_NOTE))
                );
                appointmentList.add(appointment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return appointmentList;
    }

    public boolean addVital(Vital vital) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TEMPERATURE, vital.getTemperature());
        values.put(COLUMN_PULSE, vital.getPulse());
        values.put(COLUMN_BLOOD_PRESSURE, vital.getBloodPressure());
        values.put(COLUMN_BLOOD_GLUCOSE, vital.getBloodGlucose());
        values.put(COLUMN_RESPIRATORY_RATE, vital.getRespiratoryRate());
        values.put(COLUMN_WEIGHT, vital.getWeight());
        values.put(COLUMN_HEIGHT, vital.getHeight());

        long result = db.insert(TABLE_VITALS, null, values);
        db.close();
        return result != -1;
    }

    @SuppressLint("Range")
    public Vital getLastVital() {
        SQLiteDatabase db = this.getReadableDatabase();
        Vital vital = null;

        Cursor cursor = db.query(TABLE_VITALS, null, null, null, null, null, COLUMN_VITAL_ID + " DESC", "1");
        if (cursor.moveToFirst()) {
            vital = new Vital();
            vital.setTemperature(cursor.getDouble(cursor.getColumnIndex(COLUMN_TEMPERATURE)));
            vital.setPulse(cursor.getInt(cursor.getColumnIndex(COLUMN_PULSE)));
            vital.setBloodPressure(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_BLOOD_PRESSURE))));
            vital.setBloodGlucose(cursor.getInt(cursor.getColumnIndex(COLUMN_BLOOD_GLUCOSE)));
            vital.setRespiratoryRate(cursor.getInt(cursor.getColumnIndex(COLUMN_RESPIRATORY_RATE)));
            vital.setWeight(cursor.getDouble(cursor.getColumnIndex(COLUMN_WEIGHT)));
            vital.setHeight(cursor.getDouble(cursor.getColumnIndex(COLUMN_HEIGHT)));
        }
        cursor.close();
        db.close();
        return vital;
    }

}
