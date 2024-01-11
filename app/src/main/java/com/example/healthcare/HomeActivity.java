package com.example.healthcare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class HomeActivity extends BaseActivity {
    TextView user;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        user = findViewById(R.id.user);


        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs",MODE_PRIVATE);
        String username = sharedPreferences.getString("username","").toString();

        UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(this);
        Cursor result = userDatabaseHelper.getUser(username);
        while(result.moveToNext()){
            name = result.getString(2);
        }

        user.setText(name);



        CardView find_doctor = findViewById(R.id.find_doctor);
        find_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ShowAllDoctors.class));
            }
        });

        CardView app = findViewById(R.id.find_doctor);
        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,ShowAllApp.class));
            }
        });

        CardView profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,UserProfile.class));
            }
        });

        CardView log_out = findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearUserSession();

                // Navigate back to the login screen
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
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