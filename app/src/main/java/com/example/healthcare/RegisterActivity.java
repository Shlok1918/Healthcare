package com.example.healthcare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {
    EditText ed_username,ed_password,ed_first,ed_last,ed_birth,ed_phone,ed_address;
    RadioGroup rd_gender;
    RadioButton selected_gender;
    Button btn_login;

    TextView textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        UserDatabaseHelper databaseHelper = new UserDatabaseHelper(this);

        ed_username = findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_password);
        ed_first = findViewById(R.id.ed_first);
        ed_last = findViewById(R.id.ed_last);
        ed_birth = findViewById(R.id.ed_birth);
        ed_phone = findViewById(R.id.ed_phone);
        ed_address = findViewById(R.id.ed_address);
        textLogin = findViewById(R.id.textLogin);

        rd_gender = findViewById(R.id.rd_gender);
        btn_login = findViewById(R.id.btn_login);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ed_username.getText().toString();
                String password = ed_password.getText().toString();
                String first = ed_first.getText().toString();
                String last = ed_last.getText().toString();
                String birth = ed_birth.getText().toString();
                String phone = ed_phone.getText().toString();
                String address = ed_address.getText().toString();
                selected_gender = rd_gender.findViewById(rd_gender.getCheckedRadioButtonId());
//                String output = username + password
//                        + first + last + birth + phone + address;
//                textLogin.setText(output);
                if (!username.isEmpty()
                        && !password.isEmpty()
                        && !first.isEmpty()
                        && !last.isEmpty()
                        && !birth.isEmpty()
                        && !phone.isEmpty()
                        && !address.isEmpty()
                        && selected_gender != null){
                    databaseHelper.register_user(username,password,first,last,phone,Integer.parseInt(birth),selected_gender.toString(),address);
                    Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Please fill out all details!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }

}