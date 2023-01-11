package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class OnboardingActivity extends AppCompatActivity {
    private static final String USER_ROLE_KEY = "key_user_role";
    private static final String AUTO_LOGIN_KEY = "key_auto_login";
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";


    private Button getStartedBtn;
    MaterialButtonToggleGroup materialButtonToggleGroup;

    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        sharedPrefs = getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);

        getStartedBtn =  findViewById(R.id.get_started_btn);
        materialButtonToggleGroup = findViewById(R.id.toggleGroup);

        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buttonId = materialButtonToggleGroup.getCheckedButtonId();
                if (buttonId != -1) {
                    Button btn = materialButtonToggleGroup.findViewById(buttonId);
                    String userRole = (String) btn.getText();

                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString(USER_ROLE_KEY, userRole);
                    editor.apply();

                    Intent i = new Intent(OnboardingActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(OnboardingActivity.this, "Pilih role sebelum melanjutkan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        autoLogin();
    }

    private void autoLogin() {
        boolean auto = this.sharedPrefs.getBoolean(AUTO_LOGIN_KEY, false);
        if (auto) {
            Intent i = new Intent(OnboardingActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }
}