package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Contact extends BaseActivity {

    EditText ed_locate,ed_url;
    Button btn_locate,btn_visit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ed_locate = findViewById(R.id.ed_locate);
        btn_locate = findViewById(R.id.btn_locate);
        ed_url = findViewById(R.id.ed_url);
        btn_visit = findViewById(R.id.btn_visit);

        btn_locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("geo:0,0?q="+ed_locate.getText().toString()));
                startActivity(i);

            }
        });

        btn_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(ed_url.getText().toString()));
                startActivity(i);

            }
        });

    }
}