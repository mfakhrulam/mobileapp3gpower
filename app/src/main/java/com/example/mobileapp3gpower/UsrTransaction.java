package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.Product;
import com.example.mobileapp3gpower.database.ProductDao;
import com.example.mobileapp3gpower.database.Transaction;
import com.example.mobileapp3gpower.database.TransactionDao;
import com.example.mobileapp3gpower.database.User;
import com.example.mobileapp3gpower.database.UserDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UsrTransaction extends AppCompatActivity {
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";
    private static final String LOGIN_USER_KEY = "key_id_user";

    private SharedPreferences sharedPrefs;

    private ProductDao productDao;
    private UserDao userDao;
    private TransactionDao transactionDao;

    private Button btnCheckout;
    private ImageButton btnBack;


    private String dateTime;
    private Date date;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

    private EditText inpAmounts;

    private TextView inpIdProduct, inpNameProduct, inpPriceProduct, inpWarrantyProduct;
    private TextView inpIdUser, inpNameUser, txtv_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usr_transaction);
        sharedPrefs = getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);

        productDao = AppDBProvider.getInstance(this).productDao();
        userDao = AppDBProvider.getInstance(this).userDao();
        transactionDao = AppDBProvider.getInstance(this).transactionDao();

        Bundle extras = getIntent().getExtras();
        int idProduct = extras.getInt("productId");
        int idUser = extras.getInt("userId");
        Product currentProduct = productDao.findById(idProduct);
        User currentUser = userDao.findById(idUser);

        int stok = currentProduct.stock;

        inpAmounts = findViewById(R.id.amounts);

        inpIdProduct = findViewById(R.id.txtv_id_inp);
        inpIdProduct.setText(String.valueOf(currentProduct.productId));
        inpNameProduct = findViewById(R.id.txtv_name_inp);
        inpNameProduct.setText(currentProduct.name);
        inpPriceProduct = findViewById(R.id.txtv_price_inp);
        inpPriceProduct.setText("Rp." + String.valueOf(currentProduct.price));
        inpWarrantyProduct = findViewById(R.id.txtv_warranty_inp);
        inpWarrantyProduct.setText(String.valueOf(currentProduct.warranty) + " tahun");

        inpIdUser = findViewById(R.id.txtv_id_inp_2);
        inpIdUser.setText(String.valueOf(currentUser.userId));
        inpNameUser = findViewById(R.id.txtv_name_inp_2);
        inpNameUser.setText(currentUser.name);
        txtv_total = findViewById(R.id.txtv_total);

        btnCheckout = findViewById(R.id.btn_checkout);
        btnBack = findViewById(R.id.back_btn);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = auth(stok);
                if (valid) {
                    int amounts = Integer.parseInt(inpAmounts.getText().toString().trim());
                    int total = currentProduct.price * amounts;
                    Toast.makeText(UsrTransaction.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    calendar = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aaa z");
                    dateTime = simpleDateFormat.format(calendar.getTime()).toString();
                    date = calendar.getTime();
                    Transaction currentTransaction = new Transaction(
                            currentUser.userId,
                            currentProduct.productId,
                            date,
                            total
                            );
                    transactionDao.insertAll(currentTransaction);
                    finish();
                }
            }
        });

        inpAmounts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int amount;
                if (inpAmounts.getText().toString().trim().isEmpty()) {
                    amount = 0;
                } else {
                    amount = Integer.parseInt(inpAmounts.getText().toString().trim());
                }
                int total = amount * currentProduct.price;
                String txtv_total_value = "Rp."+ String.valueOf(total);
                txtv_total.setText(txtv_total_value);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private boolean auth(int stok) {
        String amounts = this.inpAmounts.getText().toString().trim();

        if(amounts.isEmpty())
            Toast.makeText(this, "Jumlah beli harus diisi", Toast.LENGTH_SHORT).show();
        else if (Integer.parseInt(amounts) > stok) {
            Toast.makeText(this, "Stok tidak menyukupi", Toast.LENGTH_SHORT).show();
        } else if(Integer.parseInt(amounts) <= 0) {
            Toast.makeText(this, "Minimal pembelian 1", Toast.LENGTH_SHORT).show();
        }
        else {
            return true;
        }
        return false;
    }
}