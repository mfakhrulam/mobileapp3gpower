package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.User;
import com.example.mobileapp3gpower.database.UserDao;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final String USER_ROLE_KEY = "key_user_role";
    private static final String AUTO_LOGIN_KEY = "key_auto_login";
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";
    private static final String LOGIN_USER_KEY = "key_id_user";

    private SharedPreferences sharedPrefs;

    private Button btnLogin;
    private EditText inpEmail, inpPassword;
    private TextView daftar, txtv_1;
    private User currentUser;

    private static final String EMAIL_DUMMY_ADMIN = "admin@gmail.com";
    private static final String PASS_DUMMY_ADMIN = "admin";

    private static final String EMAIL_DUMMY_USER = "user@gmail.com";
    private static final String PASS_DUMMY_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefs = getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);

        btnLogin = findViewById(R.id.btn_login);
        daftar = findViewById(R.id.daftar);
        txtv_1 = findViewById(R.id.txtv_1);
        inpEmail = findViewById(R.id.inp_email);
        inpPassword = findViewById(R.id.inp_password);
//
//        User user = new User("amin@gmail.com", "Fakhrul Amin", "12345678", "admin");
//        UserDao userDao = AppDBProvider.getInstance(getApplicationContext()).userDao();
//        userDao.insertAll(user);
//        Log.d("print", String.valueOf(user.name));


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User valid = authUser();
                if (valid != null) {
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putInt(LOGIN_USER_KEY, valid.userId);
                    editor.apply();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                    makeAutoLogin();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Email dan/atau password tidak valid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        if (Objects.equals(sharedPrefs.getString(USER_ROLE_KEY, "user"), "admin")) {
            daftar.setText("");
            txtv_1.setText("");
        }

        autoLogin();
    }

    private User authUser() {
        String currentEmail = this.inpEmail.getText().toString().trim();
        String currentPassword = this.inpPassword.getText().toString().trim();

        UserDao userDao = AppDBProvider.getInstance(this).userDao();

        if (sharedPrefs.getString(USER_ROLE_KEY, "user").equals("admin")) {
            currentUser = userDao.findByEmailAndPasswordAdmin(currentEmail, currentPassword);
        } else {
            currentUser = userDao.findByEmailAndPasswordUser(currentEmail, currentPassword);
        }

        return currentUser;
    }

    private void autoLogin() {
        boolean auto = this.sharedPrefs.getBoolean(AUTO_LOGIN_KEY, false);
        if (auto) {
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void makeAutoLogin() {
        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        editor.putBoolean(AUTO_LOGIN_KEY, true);
        editor.apply();
    }
}