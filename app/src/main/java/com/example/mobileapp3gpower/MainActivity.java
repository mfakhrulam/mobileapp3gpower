package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.Product;
import com.example.mobileapp3gpower.database.ProductDao;
import com.example.mobileapp3gpower.database.User;
import com.example.mobileapp3gpower.database.UserDao;
import com.example.mobileapp3gpower.fragment.ProductFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        UserDao userDao = AppDBProvider.getInstance(getApplicationContext()).userDao();
        User admin = userDao.findAdmin();
        if  (admin == null) {
            User newAdmin = new User("admin@gmail.com", "Fakhrul Amin", "12345678", "admin");
            userDao.insertAll(newAdmin);
        }

        User user = userDao.findUser();
        if  (user == null) {
            User newUser = new User("user@gmail.com", "Fakhrul Amin", "12345678", "user");
            userDao.insertAll(newUser);
        }

        ProductDao productDao = AppDBProvider.getInstance(getApplicationContext()).productDao();
        Product product = productDao.findProduct();
        if (product == null) {
            Product product_1 = new Product("Legion 5", 5, 5000000, 3);
            Product product_2 = new Product("Legion 6", 4, 6000000, 2);
            Product product_3 = new Product("Fantech HELLSCREAM GS205", 10, 99000, 0);

            productDao.insertAll(product_1, product_2, product_3);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, OnboardingActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);
    }
}