package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.Product;
import com.example.mobileapp3gpower.database.ProductDao;
import com.example.mobileapp3gpower.database.Transaction;
import com.example.mobileapp3gpower.database.TransactionDao;
import com.example.mobileapp3gpower.database.User;
import com.example.mobileapp3gpower.database.UserDao;

import java.text.SimpleDateFormat;

public class DetailsTransactionActivity extends AppCompatActivity {

    private ProductDao productDao;
    private UserDao userDao;
    private TransactionDao transactionDao;

    private ImageButton btnBack;
    private Button btnBuyAgain;

    private TextView inpId, inpDate;
    private TextView inpIdProduct, inpNameProduct, inpPriceProduct, inpAmount;
    private TextView inpIdUser, inpNameUser, inpTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_transaction);

        productDao = AppDBProvider.getInstance(this).productDao();
        userDao = AppDBProvider.getInstance(this).userDao();
        transactionDao = AppDBProvider.getInstance(this).transactionDao();

        Bundle extras = getIntent().getExtras();
        int idTransaction = extras.getInt("transactionId");
        Transaction transaction = transactionDao.findById(idTransaction);
        Product product = productDao.findById(transaction.productId);
        User user = userDao.findById(transaction.userId);

        // inisialisasi button
        btnBack = findViewById(R.id.back_btn);
        btnBuyAgain = findViewById(R.id.btn_buy_again);

        // inisisalisasi textview
        inpId = findViewById(R.id.txtv_id_inp);
        inpDate = findViewById(R.id.txtv_date_inp);

        inpIdProduct = findViewById(R.id.txtv_id_product_inp);
        inpNameProduct = findViewById(R.id.txtv_name_product_inp);
        inpPriceProduct = findViewById(R.id.txtv_price_product_inp);
        inpAmount = findViewById(R.id.txtv_amount_product_inp);

        inpIdUser = findViewById(R.id.txtv_id_user_inp);
        inpNameUser = findViewById(R.id.txtv_name_user_inp);
        inpTotal = findViewById(R.id.txtv_total_inp);

        // set text
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aaa z");
        String dateTime = simpleDateFormat.format(transaction.date).toString();

        inpId.setText(String.valueOf(transaction.transactionId));
        inpDate.setText(dateTime);

        inpIdProduct.setText(String.valueOf(transaction.productId));
        inpNameProduct.setText(product.name);
        inpPriceProduct.setText("Rp. " + product.price);
        inpAmount.setText(String.valueOf(transaction.amount));

        inpIdUser.setText(String.valueOf(transaction.userId));
        inpNameUser.setText(user.name);
        inpTotal.setText("Rp. " + transaction.total);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnBuyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UsrTransaction.class);
                Bundle extras = new Bundle();
                extras.putInt("productId", transaction.productId);
                extras.putInt("userId", transaction.userId);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
}