package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private ProductDao productDao;
    private UserDao userDao;
    private TransactionDao transactionDao;

    private Button btnCheckout;

    private String dateTime;
    private Date date;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

    private EditText inpAmounts;

    private TextView inpIdProduct, inpNameProduct, inpPriceProduct, inpWarrantyProduct;
    private TextView inpIdUser, inpNameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usr_transaction);

        productDao = AppDBProvider.getInstance(this).productDao();
        userDao = AppDBProvider.getInstance(this).userDao();
        transactionDao = AppDBProvider.getInstance(this).transactionDao();

        Bundle extras = getIntent().getExtras();
        int idProduct = extras.getInt("productId");
        int idUser = extras.getInt("userId");
        Product currentProduct = productDao.findById(idProduct);
        User currentUser = userDao.findById(2);
        Log.d("print", String.valueOf(currentUser.userId));

        int stok = currentProduct.stock;

        inpAmounts = findViewById(R.id.amounts);

        inpIdProduct = findViewById(R.id.txtv_id_inp);
        inpIdProduct.setText(String.valueOf(currentProduct.productId));
        inpNameProduct = findViewById(R.id.txtv_name_inp);
        inpNameProduct.setText(currentProduct.name);
        inpPriceProduct = findViewById(R.id.txtv_price_inp);
        inpPriceProduct.setText(String.valueOf(currentProduct.price));
        inpWarrantyProduct = findViewById(R.id.txtv_warranty_inp);
        inpWarrantyProduct.setText(String.valueOf(currentProduct.warranty));

        inpIdUser = findViewById(R.id.txtv_id_inp_2);
        inpIdUser.setText(String.valueOf(currentUser.userId));
        inpNameUser = findViewById(R.id.txtv_name_inp_2);
        inpNameUser.setText(currentUser.name);

        btnCheckout = findViewById(R.id.btn_checkout);

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


    }

    private boolean auth(int stok) {
        String amounts = this.inpAmounts.getText().toString().trim();

        if(amounts.isEmpty())
            Toast.makeText(this, "Jumlah beli harus diisi", Toast.LENGTH_SHORT).show();
        else if (Integer.parseInt(amounts) > stok) {
            Toast.makeText(this, "Stok tidak menyukupi", Toast.LENGTH_SHORT).show();
        }
        else {
            return true;
        }
        return false;
    }
}