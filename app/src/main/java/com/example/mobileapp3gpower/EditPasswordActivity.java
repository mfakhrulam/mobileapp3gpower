package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.User;
import com.example.mobileapp3gpower.database.UserDao;

public class EditPasswordActivity extends AppCompatActivity {
    private SharedPreferences sharedPrefs;
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";
    private static final String LOGIN_USER_KEY = "key_id_user";

    private Button btnSave;
    private EditText inpOldPass, inpNewPass, inpNewPassConf;
    private UserDao userDao;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        btnSave = findViewById(R.id.btn_save);
        btnBack = findViewById(R.id.back_btn);

        inpOldPass = findViewById(R.id.inp_old_pass);
        inpNewPass = findViewById(R.id.inp_new_pass);
        inpNewPassConf = findViewById(R.id.inp_new_pass_conf);

        userDao = AppDBProvider.getInstance(getApplicationContext()).userDao();
        sharedPrefs = getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        int userId = sharedPrefs.getInt(LOGIN_USER_KEY, -1);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = auth();
                if (valid) {
                    String newPass = inpNewPassConf.getText().toString().trim();
                    userDao.updatePassword(userId, newPass);
                    Toast.makeText(EditPasswordActivity.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean auth() {
        String oldPass = this.inpOldPass.getText().toString().trim();
        String newPass = this.inpNewPass.getText().toString().trim();
        String newPassConf = this.inpNewPassConf.getText().toString().trim();

        if(oldPass.isEmpty())
            Toast.makeText(this, "Kata sandi lama masih kosong", Toast.LENGTH_SHORT).show();
        else if (newPass.isEmpty())
            Toast.makeText(this, "Kata sandi baru masih kosong", Toast.LENGTH_SHORT).show();
        else if(newPass.length() < 8)
            Toast.makeText(this, "Password minimum memiliki 8 karakter", Toast.LENGTH_SHORT).show();
        else if (newPassConf.isEmpty())
            Toast.makeText(this, "Konfirmasi kata sandi harus diisi", Toast.LENGTH_SHORT).show();
        else if(!newPass.equals(newPassConf))
            Toast.makeText(this, "Kedua kata sandi baru harus sama", Toast.LENGTH_SHORT).show();
        else {
            return true;
        }
        return false;
    }
}