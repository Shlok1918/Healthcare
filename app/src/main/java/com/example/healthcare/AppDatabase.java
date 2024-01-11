package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

public class AppDatabase extends SQLiteOpenHelper {
    public static final String DBName = "appointment_db";
    public AppDatabase(@Nullable Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_query = "CREATE TABLE IF NOT EXISTS app(_id integer PRIMARY KEY AUTOINCREMENT, username text, doctorname text, day text, time text)";
        sqLiteDatabase.execSQL(create_query);

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int register_app(String username,String doctorname,String day,String time){
        ContentValues cv = new ContentValues();
        //cv.put("app_id",app_id);
        cv.put("username",username);
        cv.put("doctorname",doctorname);
        cv.put("day",day);
        cv.put("time",time);
        SQLiteDatabase db = getWritableDatabase();
        long check = db.insert("app",null,cv);
        db.close();
        int result = 0;
        if(check!= 0){
            result = 1;
        }
        db.close();
        return result;
    }

    public Cursor getAllApp(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM app", null);
        return cursor;
    }

    public Cursor getAppointmentsByUsername(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"_id", "username", "doctorname", "day", "time"};
        String selection = "username=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("app", projection, selection, selectionArgs, null, null, null);

        return cursor;
    }




}
