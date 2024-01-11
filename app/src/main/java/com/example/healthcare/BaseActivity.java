package com.example.healthcare;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocale();
    }


    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My Lang", "");
        setLocale(language);
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My Lang", langCode);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle menu item selection
        switch (item.getItemId()) {
            case R.id.language:
                showChangeLanguageDialogue();
                return true;

            case R.id.query:
                String recipientEmail = "sakshi.jagtap@somaiya.edu";
                String subject = "Query from Customer";

                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs",MODE_PRIVATE);
                String username = sharedPreferences.getString("username","").toString();

                String from = username;

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + recipientEmail));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{from});

                startActivity(emailIntent);
                return true;
            case R.id.contact:
                startActivity(new Intent(BaseActivity.this,Contact.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showChangeLanguageDialogue() {
        final String[] lang = {"English","हिंदी","मराठी"};
        AlertDialog.Builder mBuilder =  new AlertDialog.Builder(BaseActivity.this);
        mBuilder.setTitle("Change Language: ");
        mBuilder.setSingleChoiceItems(lang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    setLocale("en");
                    recreate();
                }else if(i == 1){
                    setLocale("hi");
                    recreate();
                }else if(i == 2){
                    setLocale("mr");
                    recreate();
                }

                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialogue = mBuilder.create();
        mDialogue.show();
    }
}
