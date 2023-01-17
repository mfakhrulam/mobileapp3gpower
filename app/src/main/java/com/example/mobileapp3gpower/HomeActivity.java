package com.example.mobileapp3gpower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mobileapp3gpower.fragment.AccountFragment;
import com.example.mobileapp3gpower.fragment.ProductFragment;
import com.example.mobileapp3gpower.fragment.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private static final String USER_ROLE_KEY = "key_user_role";
    private static final String AUTO_LOGIN_KEY = "key_auto_login";
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";
    private static final String LOGIN_USER_KEY = "key_id_user";


    private SharedPreferences sharedPrefs;
    private Button btnProduct, btnTransaction, btnOurContact, btnLogout;

//    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BottomNavigationView bottomNavigationView;
//        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);


//        sharedPrefs = getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
//        btnLogout = findViewById(R.id.btn_logout);
//        btnProduct = findViewById(R.id.btn_product);
//        btnTransaction = findViewById(R.id.btn_transaction);
//        btnOurContact = findViewById(R.id.btn_contact);
//
//        Log.d("print", String.valueOf(sharedPrefs.getInt(LOGIN_USER_KEY, -1)));
//
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences.Editor editor = sharedPrefs.edit();
//                editor.remove(AUTO_LOGIN_KEY);
//                editor.remove(LOGIN_USER_KEY);
//                editor.remove(USER_ROLE_KEY);
//                editor.apply();
//                Intent i = new Intent(HomeActivity.this, OnboardingActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
//
//        btnProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(HomeActivity.this, ListProduct.class);
//                startActivity(i);
//            }
//        });
//
//        btnOurContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(HomeActivity.this, ContactActivity.class);
//                startActivity(i);
//            }
//        });
//
//        btnTransaction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(HomeActivity.this, ViewTransaction.class);
//                startActivity(i);
//            }
//        });




    }

    ProductFragment productFragment = new ProductFragment();
    TransactionFragment transactionFragment = new TransactionFragment();
    AccountFragment accountFragment = new AccountFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home:
                fragment = new ProductFragment();
                break;
            case R.id.transaction:
                fragment = new TransactionFragment();
                break;
            case R.id.account:
                fragment = new AccountFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}