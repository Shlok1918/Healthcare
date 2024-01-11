package com.example.healthcare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class AdminHome extends AppCompatActivity {
    CardView show_doctor,add_doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        add_doctor = findViewById(R.id.add_doctor);
        show_doctor = findViewById(R.id.show_doctor);

        add_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHome.this,RegisterDoctor.class));
            }
        });

        show_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHome.this,ShowAllDoctors.class));
            }
        });

        CardView app = findViewById(R.id.appointment);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHome.this,AdminAppointments.class));
            }
        });

        CardView log_out = findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearUserSession();

                // Navigate back to the login screen
                Intent intent = new Intent(AdminHome.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void clearUserSession() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}