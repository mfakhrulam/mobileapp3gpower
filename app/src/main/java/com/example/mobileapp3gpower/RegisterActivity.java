package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.User;
import com.example.mobileapp3gpower.database.UserDao;

public class RegisterActivity extends AppCompatActivity {
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";
    private static final String USER_ROLE_KEY = "key_user_role";

    private SharedPreferences sharedPrefs;

    private Button btnRegister;
    private EditText inpEmail, inpName, inpPassword, inpCPassword;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPrefs = getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);

        btnRegister = findViewById(R.id.btn_register);
        login = findViewById(R.id.login);

        inpName = findViewById(R.id.inp_name);
        inpEmail = findViewById(R.id.inp_email);
        inpPassword = findViewById(R.id.inp_password);
        inpCPassword = findViewById(R.id.inp_c_password);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean valid = auth();
                if (valid) {
                    UserDao userDao = AppDBProvider.getInstance(getApplicationContext()).userDao();
                    userDao.insertAll(makeUser());

                    Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private User makeUser() {
        String email = this.inpEmail.getText().toString().trim();
        String name = this.inpName.getText().toString().trim();
        String password = this.inpPassword.getText().toString().trim();
        String role = sharedPrefs.getString(USER_ROLE_KEY, "user");

        User newUser = new User(email, name, password, role);

        return newUser;
    }

    private boolean auth() {
        String currentEmail = this.inpEmail.getText().toString().trim();
        String currentName = this.inpName.getText().toString().trim();
        String currentPassword = this.inpPassword.getText().toString().trim();
        String currentCPassword = this.inpCPassword.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(currentName.isEmpty())
            Toast.makeText(this, "Nama lengkap harus diisi", Toast.LENGTH_SHORT).show();
        else if(currentEmail.isEmpty())
            Toast.makeText(this, "Email harus diisi", Toast.LENGTH_SHORT).show();
        else if(!currentEmail.matches(emailPattern))
            Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show();
        else if(currentPassword.isEmpty())
            Toast.makeText(this, "Password harus diisi", Toast.LENGTH_SHORT).show();
        else if(currentPassword.length() < 8)
            Toast.makeText(this, "Password minimum memiliki 8 karakter", Toast.LENGTH_SHORT).show();
        else if(!currentCPassword.equals(currentPassword))
            Toast.makeText(this, "Kedua password harus sama", Toast.LENGTH_SHORT).show();
        else {
            UserDao daoUser = AppDBProvider.getInstance(this).userDao();
            User currentUser = daoUser.findByEmail(currentEmail);
            if (currentUser != null)
                Toast.makeText(this, "User dengan email sama sudah terdaftar", Toast.LENGTH_SHORT).show();
            else
                return true;
        }
        return false;
    }

}