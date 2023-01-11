package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.Product;
import com.example.mobileapp3gpower.database.ProductDao;

public class UpdateProduct extends AppCompatActivity {

    private Button btnSave;
    private EditText inpName, inpStock, inpPrice, inpWarranty;
    private ProductDao productDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);


        btnSave = findViewById(R.id.btn_save);

        inpName = findViewById(R.id.inp_name);
        inpStock = findViewById(R.id.inp_stock);
        inpPrice = findViewById(R.id.inp_price);
        inpWarranty = findViewById(R.id.inp_warranty);

        productDao = AppDBProvider.getInstance(getApplicationContext()).productDao();

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("productId");
        Product currentProduct = productDao.findById(id);
        Log.d("print", String.valueOf(currentProduct.productId));
        inpName.setText(currentProduct.name);
        inpStock.setText(String.valueOf(currentProduct.stock));
        inpPrice.setText(String.valueOf(currentProduct.price));
        inpWarranty.setText(String.valueOf(currentProduct.warranty));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = auth();
                if (valid) {
                    productDao.update(updateProduct(id));

                    Toast.makeText(UpdateProduct.this, "Berhasil Disimpan", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });
    }

    private Product updateProduct(int id) {
        String name = this.inpName.getText().toString().trim();
        int stok = Integer.parseInt(this.inpStock.getText().toString().trim());
        int price = Integer.parseInt(this.inpPrice.getText().toString().trim());
        int warranty = Integer.parseInt(this.inpWarranty.getText().toString().trim());

        Product updatedProduct = new Product(id, name, stok, price, warranty);
        // kurang updatenya

        return updatedProduct;
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