package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;



public class DoctorDatabaseHelper extends SQLiteOpenHelper {
    public static final String DBName = "Doctors_db";
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imgInByte;
    public DoctorDatabaseHelper(@Nullable Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_query = "CREATE TABLE IF NOT EXISTS doctors(doctor_id integer, name text, specialization text, days text, app_time text, phone text, img BLOB) ";
        sqLiteDatabase.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean update_doctor(Integer doctor_id,String name,String specialization,String days,String app_time, String phone, Bitmap img){
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] imgInByte=null;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            imgInByte = byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            Log.e("updateDoctor",e.getMessage());

        }
        ContentValues cv = new ContentValues();
        cv.put("doctor_id",doctor_id);
        cv.put("name",name);
        cv.put("specialization",specialization);
        cv.put("days",days);
        cv.put("app_time",app_time);
        cv.put("phone",phone);
        cv.put("img", imgInByte);
        SQLiteDatabase db = this.getWritableDatabase();
        String id = String.valueOf(doctor_id);
        long res=db.update("doctors",cv,"doctor_id=?",new String[]{id});
        if (res==1)
            return true;
        else
            return false;

    }

    public int deleteDoctor(int doctorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("doctors", "doctor_id = ?", new String[]{String.valueOf(doctorId)});
        db.close();
        return result;
    }

    public Cursor searchDoctor(int doctorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from doctors where doctor_id = ?",new String[]{String.valueOf(doctorId)});
        return result;
    }
    public int register_doctor(Integer doctor_id,String name,String specialization,String days,String app_time, String phone, Bitmap img){
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            imgInByte = byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            Log.e("insertDoctor",e.getMessage());

        }
        ContentValues cv = new ContentValues();
        cv.put("doctor_id",doctor_id);
        cv.put("name",name);
        cv.put("specialization",specialization);
        cv.put("days",days);
        cv.put("app_time",app_time);
        cv.put("phone",phone);
        cv.put("img", imgInByte);
        SQLiteDatabase db = this.getWritableDatabase();
        long check = db.insert("doctors",null,cv);
        int result = 0;
        if(check!= 0){
            result = 1;
        }
        db.close();
        return result;
    }


    public ArrayList<Doctor> getAllDoctors(){

            ArrayList<Doctor> list = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM doctors", null);
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String specialization = cursor.getString(cursor.getColumnIndex("specialization"));
                @SuppressLint("Range") String days = cursor.getString(cursor.getColumnIndex("days"));
                @SuppressLint("Range") String app_time = cursor.getString(cursor.getColumnIndex("app_time"));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
                @SuppressLint("Range") byte[] imgBytes = cursor.getBlob(cursor.getColumnIndex("img"));
                Bitmap img = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                Doctor doctor = new Doctor(name, specialization, days, app_time, phone, img);
                list.add(doctor);
            }
            cursor.close();

        }catch (Exception e){
            Log.e("getALL",e.getMessage());
        }
        return list;
    }
}
