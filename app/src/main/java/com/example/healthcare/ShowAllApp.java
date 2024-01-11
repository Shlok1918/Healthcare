package com.example.healthcare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowAllApp extends AppCompatActivity {
    ListView all_app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_app);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        all_app = findViewById(R.id.all_app);

        ListView allAppListView = findViewById(R.id.all_app);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs",MODE_PRIVATE);
        String username = sharedPreferences.getString("username","").toString();

        AppDatabase db = new AppDatabase(this);
        Cursor result = db.getAppointmentsByUsername(username);
//        if (result != null && result.moveToFirst()) {
//            StringBuffer stringBuffer = new StringBuffer();
//            do {
//                String appId = result.getString(result.getColumnIndexOrThrow("_id"));
//                String username = result.getString(result.getColumnIndexOrThrow("username"));
//                String doctorname = result.getString(result.getColumnIndexOrThrow("doctorname"));
//                String day = result.getString(result.getColumnIndexOrThrow("day"));
//                String time = result.getString(result.getColumnIndexOrThrow("time"));
//
//                stringBuffer.append("App ID: ").append(appId).append("\n");
//                stringBuffer.append("Username: ").append(username).append("\n");
//                stringBuffer.append("Doctor Name: ").append(doctorname).append("\n");
//                stringBuffer.append("Day: ").append(day).append("\n");
//                stringBuffer.append("Time: ").append(time).append("\n\n");
//            } while (result.moveToNext());
//
//            Toast.makeText(ShowAllApp.this, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(ShowAllApp.this, "No data found.", Toast.LENGTH_SHORT).show();
//        }

        List<HashMap<String, String>> appointments = new ArrayList<>();

        if (result != null && result.moveToFirst()) {
            do {
                HashMap<String, String> appointment = new HashMap<>();
                appointment.put("appId", result.getString(result.getColumnIndexOrThrow("_id")));
                appointment.put("username", result.getString(result.getColumnIndexOrThrow("username")));
                appointment.put("doctorname", result.getString(result.getColumnIndexOrThrow("doctorname")));
                appointment.put("day", result.getString(result.getColumnIndexOrThrow("day")));
                appointment.put("time", result.getString(result.getColumnIndexOrThrow("time")));

                appointments.add(appointment);
            } while (result.moveToNext());
        } else {
            Toast.makeText(ShowAllApp.this, "No data found.", Toast.LENGTH_SHORT).show();
        }

        String[] fromColumns = {"appId", "username", "doctorname", "day", "time"};
        int[] toViews = {R.id.app_id, R.id.user_name, R.id.doc_name, R.id.day, R.id.time};

        SimpleAdapter adapter = new SimpleAdapter(this, appointments, R.layout.one_app_row, fromColumns, toViews);
        all_app.setAdapter(adapter);



    }
}