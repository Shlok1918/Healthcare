package com.example.healthcare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends BaseActivity {

    EditText ed_username,ed_password,ed_first,ed_last,ed_birth,ed_phone,ed_address;
    RadioGroup rd_gender;
    RadioButton radioButtonMale,radioButtonFemale;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        ed_username = findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_password);
        ed_first = findViewById(R.id.ed_first);
        ed_last = findViewById(R.id.ed_last);
        ed_birth = findViewById(R.id.ed_birth);
        ed_phone = findViewById(R.id.ed_phone);
        ed_address = findViewById(R.id.ed_address);

        rd_gender = findViewById(R.id.rd_gender);
        update = findViewById(R.id.btn_login);

        radioButtonMale = findViewById(R.id.radioButton3);
        radioButtonFemale = findViewById(R.id.radioButton4);


        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs",MODE_PRIVATE);
        String username = sharedPreferences.getString("username","").toString();

        // Retrieve the user data from the database
        UserDatabaseHelper db = new UserDatabaseHelper(this);
        Cursor result = db.getUser(username);

        if (result != null && result.moveToFirst()) {
            // Retrieve the user data from the cursor
            String password = result.getString(1);
            String firstName = result.getString(2);
            String lastName = result.getString(result.getColumnIndexOrThrow("lastName"));
            String birth = result.getString(result.getColumnIndexOrThrow("age"));
            String phone = result.getString(result.getColumnIndexOrThrow("phone"));
            String address = result.getString(result.getColumnIndexOrThrow("address"));
            String gender = result.getString(result.getColumnIndexOrThrow("gender"));

            // Set the user data to the corresponding views
            ed_username.setText(username);
            ed_password.setText(password);
            ed_first.setText(firstName);
            ed_last.setText(lastName);
            ed_birth.setText(birth);
            ed_phone.setText(phone);
            ed_address.setText(address);


            if (gender.equals("Male")) {
                radioButtonMale.setChecked(true);
            } else if (gender.equals("Female")) {
                radioButtonFemale.setChecked(true);
            } else {
                rd_gender.clearCheck();
            }
        }

    }
}