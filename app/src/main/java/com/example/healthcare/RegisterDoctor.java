package com.example.healthcare;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

public class RegisterDoctor extends AppCompatActivity {

    Spinner spinner_special,spinner_appDays;
    String[] specialization = {"family doctor" ,"cardiology", "ENT", "neurology", "ayurveda"};
    String[] appDays = {"Sun-Fri","Mon-Sat","Mon-Fri","Tue-Sat"};

    CheckBox check_time1,check_time2,check_time3;

    Button add_doctor,upload_img,update,delete,serach;
    String special, days;

    EditText ed_first, ed_last, ed_phone, ed_docID;
    RadioGroup rd_gender;
    RadioButton selected_gender;
    ImageView imgProfile;

    private final int GALLERY_REQUEST_CODE = 1000;

    private Uri imgFilePath;
    private Bitmap imgBitmap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doctor);

        ed_first = findViewById(R.id.ed_first);
        ed_phone = findViewById(R.id.ed_phone);
        ed_docID = findViewById(R.id.ed_docID);

        rd_gender = findViewById(R.id.rd_gender);

        imgProfile = findViewById(R.id.imgProfile);

        spinner_special = findViewById(R.id.spinner_special);
        spinner_appDays = findViewById(R.id.spinner_appDays);

        check_time1 = findViewById(R.id.check_time1);
        check_time2 = findViewById(R.id.check_time2);
        check_time3 = findViewById(R.id.check_time3);

        add_doctor = findViewById(R.id.add_doctor);
        upload_img = findViewById(R.id.upload_img);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        serach = findViewById(R.id.serach);

        DoctorDatabaseHelper doctorDatabaseHelper = new DoctorDatabaseHelper(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterDoctor.this, android.R.layout.simple_spinner_item,specialization);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_special.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(RegisterDoctor.this, android.R.layout.simple_spinner_item,appDays);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_appDays.setAdapter(adapter2);

        upload_img.setOnClickListener(v -> mGetContent.launch("image/*"));

        serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(ed_docID.getText().toString());
                Cursor result = doctorDatabaseHelper.searchDoctor(id);
                while(result.moveToNext()){
                    ed_docID.setText(String.valueOf(result.getInt(0)));

                    ed_first.setText(result.getString(1));

                    // Set specialization
                    String specialization = result.getString(2); // Assuming column index 2 contains specialization
                    ArrayAdapter<String> specializationAdapter = (ArrayAdapter<String>) spinner_special.getAdapter();
                    int specializationPosition = specializationAdapter.getPosition(specialization);
                    spinner_special.setSelection(specializationPosition);


                    // Set appDays
                    String appDays = result.getString(3); // Assuming column index 3 contains appDays
                    ArrayAdapter<String> appDaysAdapter = (ArrayAdapter<String>) spinner_appDays.getAdapter();
                    int appDaysPosition = appDaysAdapter.getPosition(appDays);
                    spinner_appDays.setSelection(appDaysPosition);

                    // Set checkboxes
                    String appTime = result.getString(4); // Assuming column index 4 contains appTime
                    String[] selectedTimes = appTime.split(" ");
                    for (String time : selectedTimes) {
                        if (time.equals(check_time1.getText().toString())) {
                            check_time1.setChecked(true);
                        } else if (time.equals(check_time2.getText().toString())) {
                            check_time2.setChecked(true);
                        } else if (time.equals(check_time3.getText().toString())) {
                            check_time3.setChecked(true);
                        }
                    }

                    ed_phone.setText(result.getString(5));
                    byte[] imageBytes = result.getBlob(6); // Assuming column index 6 contains the image data
                    Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    imgProfile.setImageBitmap(imageBitmap);


                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(ed_docID.getText().toString());
                int result = doctorDatabaseHelper.deleteDoctor(id);
                if(result ==1){
                    Toast.makeText(RegisterDoctor.this, "deleted Successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterDoctor.this, "deletion Unsuccessful!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder selected_time = new StringBuilder();
                if(check_time1.isChecked()){
                    selected_time.append(check_time1.getText().toString() + " ");
                }
                if(check_time2.isChecked()){
                    selected_time.append(check_time2.getText().toString() + " ");
                }
                if(check_time3.isChecked()){
                    selected_time.append(check_time3.getText().toString() + " ");
                }
                special = spinner_special.getSelectedItem().toString();
                days = spinner_appDays.getSelectedItem().toString();
                //Toast.makeText(RegisterDoctor.this, selected_time, Toast.LENGTH_LONG).show();
                int id = Integer.parseInt(ed_docID.getText().toString());
                String phone = ed_phone.getText().toString();
                String time = selected_time.toString();
                String name = ed_first.getText().toString();
                //String name = ed_first.getText().toString() + " " + ed_last.getText().toString();
                boolean result = doctorDatabaseHelper.update_doctor(id,name,special,days,time,phone,imgBitmap);
                if(result ==true){
                    Toast.makeText(RegisterDoctor.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterDoctor.this, "Update Unsuccessful!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                spinner_special.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        special = adapterView.getItemAtPosition(i).toString();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//
//                spinner_appDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        days = adapterView.getItemAtPosition(i).toString();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });

                StringBuilder selected_time = new StringBuilder();
                if(check_time1.isChecked()){
                    selected_time.append(check_time1.getText().toString() + " ");
                }
                if(check_time2.isChecked()){
                    selected_time.append(check_time2.getText().toString() + " ");
                }
                if(check_time3.isChecked()){
                    selected_time.append(check_time3.getText().toString() + " ");
                }
                special = spinner_special.getSelectedItem().toString();
                days = spinner_appDays.getSelectedItem().toString();
                //Toast.makeText(RegisterDoctor.this, selected_time, Toast.LENGTH_LONG).show();
                int id = Integer.parseInt(ed_docID.getText().toString());
                String phone = ed_phone.getText().toString();
                String time = selected_time.toString();
                String name = ed_first.getText().toString();
                //String name = ed_first.getText().toString() + " " + ed_last.getText().toString();
                int result = doctorDatabaseHelper.register_doctor(id,name,special,days,time,phone,imgBitmap);
                if(result ==1){
                    Toast.makeText(RegisterDoctor.this, "Register Successful!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterDoctor.this, "Register Unsuccessful!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){
                        try {
                            imgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                        } catch (IOException e) {
                            Toast.makeText(RegisterDoctor.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        imgProfile.setImageURI(result);
                    }
                }
            });




}