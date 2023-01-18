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
import com.example.mobileapp3gpower.database.Product;
import com.example.mobileapp3gpower.database.ProductDao;
import com.example.mobileapp3gpower.database.User;
import com.example.mobileapp3gpower.database.UserDao;

import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {
    private SharedPreferences sharedPrefs;
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";
    private static final String LOGIN_USER_KEY = "key_id_user";

    private Button btnSave;
    private EditText inpId, inpName, inpEmail;
    private UserDao userDao;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        btnSave = findViewById(R.id.btn_save);
        btnBack = findViewById(R.id.back_btn);

        inpId = findViewById(R.id.inp_id);
        inpName = findViewById(R.id.inp_name);
        inpEmail = findViewById(R.id.inp_email);

        inpId.setFocusable(false);
        inpId.setEnabled(false);
        inpId.setCursorVisible(false);

        userDao = AppDBProvider.getInstance(getApplicationContext()).userDao();
        sharedPrefs = getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        int userId = sharedPrefs.getInt(LOGIN_USER_KEY, -1);
        User user = userDao.findById(userId);

        inpId.setText((String.valueOf(userId)));
        inpName.setText(user.name);
        inpEmail.setText(user.email);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = auth();
                if (valid) {
                    String name = inpName.getText().toString().trim();
                    String email = inpEmail.getText().toString().trim();
                    userDao.update(userId, name, email);
                    Toast.makeText(EditProfileActivity.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
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
        int id = Integer.parseInt(this.inpId.getText().toString().trim());
        String name = this.inpName.getText().toString().trim();
        String email = this.inpEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(name.isEmpty())
            Toast.makeText(this, "Nama Produk harus diisi", Toast.LENGTH_SHORT).show();
        else if (email.isEmpty())
            Toast.makeText(this, "Email harus diisi", Toast.LENGTH_SHORT).show();
        else if(!email.matches(emailPattern))
            Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show();
        else {
            User currentUser = userDao.findByEmail(email);
            if (currentUser.userId != id)
                Toast.makeText(this, "Email sudah digunakan user lain", Toast.LENGTH_SHORT).show();
            else
                return true;
        }
        return false;
    }
}