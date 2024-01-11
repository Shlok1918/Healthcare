package com.example.healthcare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class adminLogin extends AppCompatActivity {
    EditText ed_username,ed_password;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        ed_username = findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ed_username.getText().toString();
                String password = ed_password.getText().toString();
                if(username.length() == 0 || password.length() == 0){
                    Toast.makeText(adminLogin.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }else{
                    if(username.equals("admin") && password.equals("admin")){
                        Toast.makeText(adminLogin.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent home_intent = new Intent(adminLogin.this,AdminHome.class);
                        startActivity(home_intent);
                    }else{
                        Toast.makeText(adminLogin.this, "Invalid username and password!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}