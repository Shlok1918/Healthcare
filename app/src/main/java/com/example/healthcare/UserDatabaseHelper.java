package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public static final String DBName = "healthcare_db";

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, DBName, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_query = "create table IF NOT EXISTS patients(username text, password text, " +
                "firstName text, lastName text, phone text, age integer, gender text," +
                "address text) ";
        sqLiteDatabase.execSQL(create_query);
    }
    public void register_user(String username,String password,String firstName,String lastName,String phone,Integer age,String gender,String address){
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("password",password);
        cv.put("firstName",firstName);
        cv.put("lastName",lastName);
        cv.put("phone",phone);
        cv.put("age",age);
        cv.put("gender",gender);
        cv.put("address",address);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("patients",null,cv);
        db.close();

    }
    public int login(String username,String password){
        int result = 0;
        String[] str = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from patients where username=?  and password=?",str);
        if(c.moveToFirst()){
            result = 1;
        }
        return result;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor getUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select * from patients where username = ?",new String[]{username});
        return result;
    }

    public void updateUser(String username, String newFirstName, String newLastName, String newPhone, Integer newAge, String newGender, String newAddress) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("firstName", newFirstName);
        cv.put("lastName", newLastName);
        cv.put("phone", newPhone);
        cv.put("age", newAge);
        cv.put("gender", newGender);
        cv.put("address", newAddress);

        db.update("patients", cv, "username = ?", new String[]{username});
        db.close();
    }

}
