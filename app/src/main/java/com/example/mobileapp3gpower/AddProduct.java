package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.Product;
import com.example.mobileapp3gpower.database.ProductDao;
import com.example.mobileapp3gpower.database.User;
import com.example.mobileapp3gpower.database.UserDao;

public class AddProduct extends AppCompatActivity {

    private Button btnAdd;
    private EditText inpName, inpStock, inpPrice, inpWarranty;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        btnAdd = findViewById(R.id.btn_add);
        btnBack = findViewById(R.id.back_btn);

        inpName = findViewById(R.id.inp_name);
        inpStock = findViewById(R.id.inp_stock);
        inpPrice = findViewById(R.id.inp_price);
        inpWarranty = findViewById(R.id.inp_warranty);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = auth();
                if (valid) {
                    ProductDao productDao = AppDBProvider.getInstance(getApplicationContext()).productDao();
                    productDao.insertAll(makeProduct());

                    Toast.makeText(AddProduct.this, "Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();

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

    private Product makeProduct() {
        String name = this.inpName.getText().toString().trim();
        int stok = Integer.parseInt(this.inpStock.getText().toString().trim());
        int price = Integer.parseInt(this.inpPrice.getText().toString().trim());
        int warranty = Integer.parseInt(this.inpWarranty.getText().toString().trim());

        Product newProduct = new Product(name, stok, price, warranty);

        return newProduct;
    }

    private boolean auth() {
        String name = this.inpName.getText().toString().trim();
        String stok = this.inpStock.getText().toString().trim();
        String price = this.inpPrice.getText().toString().trim();
        String warranty = this.inpWarranty.getText().toString().trim();

        if(name.isEmpty())
            Toast.makeText(this, "Nama Produk harus diisi", Toast.LENGTH_SHORT).show();
        else if(stok.isEmpty())
            Toast.makeText(this, "Stok awal harus diisi", Toast.LENGTH_SHORT).show();
        else if(price.isEmpty())
            Toast.makeText(this, "Harga harus diisi", Toast.LENGTH_SHORT).show();
        else if(warranty.isEmpty())
            Toast.makeText(this, "Garansi harus diisi", Toast.LENGTH_SHORT).show();
        else {
            return true;
        }
        return false;
    }
}