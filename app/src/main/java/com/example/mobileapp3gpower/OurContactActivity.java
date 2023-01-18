package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.UserDao;

public class OurContactActivity extends AppCompatActivity {

    private Button btnSemarang, btnYogya, btnTiktok, btnInstagram, btnMail, btnWhatsapp, btnGithub;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_contact);

        btnBack = findViewById(R.id.back_btn);

        btnSemarang = findViewById(R.id.btn_semarang);
        btnYogya = findViewById(R.id.btn_yogya);
        btnTiktok = findViewById(R.id.btn_tiktok);
        btnInstagram = findViewById(R.id.btn_instagram);
        btnMail = findViewById(R.id.btn_mail);
        btnWhatsapp = findViewById(R.id.btn_whatsapp);
        btnGithub = findViewById(R.id.btn_github);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSemarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://goo.gl/maps/eR3qtRFG7dEQgaEv6");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        btnYogya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://goo.gl/maps/YRgMxJLZyNorUDfy7");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        btnTiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.tiktok.com/discover/3gpowercomputer");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.instagram.com/3gpowercomputer/");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("mailto:cs@3gpowercomputer.com");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://wa.me/62895616797777");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        btnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/mfakhrulam/");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

    }
}