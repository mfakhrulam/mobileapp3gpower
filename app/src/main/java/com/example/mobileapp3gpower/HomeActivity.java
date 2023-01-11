package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private static final String USER_ROLE_KEY = "key_user_role";
    private static final String AUTO_LOGIN_KEY = "key_auto_login";
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";
    private static final String LOGIN_USER_KEY = "key_id_user";


    private SharedPreferences sharedPrefs;
    private Button btnProduct, btnTransaction, btnOurContact, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPrefs = getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        btnLogout = findViewById(R.id.btn_logout);
        btnProduct = findViewById(R.id.btn_product);
        btnTransaction = findViewById(R.id.btn_transaction);
        btnOurContact = findViewById(R.id.btn_contact);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.remove(AUTO_LOGIN_KEY);
                editor.remove(LOGIN_USER_KEY);
                editor.remove(USER_ROLE_KEY);
                editor.apply();
                Intent i = new Intent(HomeActivity.this, OnboardingActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ListProduct.class);
                startActivity(i);
            }
        });

        btnOurContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactActivity.class);
                startActivity(i);
            }
        });

        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ViewTransaction.class);
                startActivity(i);
            }
        });


    }
}