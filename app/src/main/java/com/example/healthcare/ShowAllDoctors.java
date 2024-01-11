package com.example.healthcare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ShowAllDoctors extends AppCompatActivity {
    RecyclerView RecordsRv;

    DoctorDatabaseHelper doctorDatabaseHelper;

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_doctors);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.all_doctors);

        doctorDatabaseHelper = new DoctorDatabaseHelper(this);
        RecordsRv = findViewById(R.id.RecordsRv);

        loadRecords();
    }

    private void loadRecords() {
        DoctorAdapter doctorAdapter = new DoctorAdapter(ShowAllDoctors.this,doctorDatabaseHelper.getAllDoctors());

        RecordsRv.setAdapter(doctorAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }
}