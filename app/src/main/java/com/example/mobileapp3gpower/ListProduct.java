package com.example.mobileapp3gpower;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.Product;
import com.example.mobileapp3gpower.database.ProductDao;
import com.example.mobileapp3gpower.database.UserDao;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.mobileapp3gpower.databinding.ActivityListProductBinding;

import java.util.List;
import java.util.Objects;

public class ListProduct extends AppCompatActivity {
    private static final String USER_ROLE_KEY = "key_user_role";
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";
    private static final String  LOGIN_USER_KEY = "key_id_user";

    private Toolbar toolbar;

    private ActivityListProductBinding binding;
    private ListView myListView;
    private SharedPreferences sharedPrefs;
    private ProductDao productDao;

    private Cursor csr;
    private SimpleCursorAdapter sca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        sharedPrefs = getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);

        myListView = findViewById(R.id.myListView);
        productDao = AppDBProvider.getInstance(this).productDao();
        toolbar = findViewById(R.id.toolbar);

//        Product product_1 = new Product("Legion 5", 5, 5000000, 3);
//        Product product_2 = new Product("Legion 6", 4, 6000000, 2);

//        productDao.insertAll(product_2, product_1);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListProduct.this, AddProduct.class);
                startActivity(i);
            }
        });

        if (Objects.equals(sharedPrefs.getString(USER_ROLE_KEY, "user"), "user")) {
            Log.d("print", sharedPrefs.getString(USER_ROLE_KEY, "user"));
            toolbar.setTitle("Jelajahi Produk");
            binding.fab.setVisibility(View.GONE);
        }

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        RefreshListView();
    }

    private void RefreshListView() {
        csr = getCursor();
        if (sca == null) {
            sca = new SimpleCursorAdapter(
                    this,
                    R.layout.custom_layout_view,csr,
                    new String[]{"_id", "name","stock", "price", "warranty"},
                    new int[]{R.id.txtv_id_inp,R.id.txtv_name_inp,R.id.txtv_stock_inp, R.id.txtv_price_inp, R.id.txtv_warranty_inp},
                    0
            );

            myListView.setAdapter(sca);

            // jika admin maka ada CRUD
            if (Objects.equals(sharedPrefs.getString(USER_ROLE_KEY, "user"), "admin")) {
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @SuppressLint("Range")
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AlertDialog.Builder builder = new
                                AlertDialog.Builder(ListProduct.this);
                        builder.setTitle("Pilihan");

                        final CharSequence[] dialogitem = {"Update Data", "Hapus Data"};
                        builder.setItems(dialogitem, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int
                                            item) {
                                        switch(item){
                                            case 0 :
                                                Intent intent = new Intent(getApplicationContext(), UpdateProduct.class);
                                                Bundle extras = new Bundle();
                                                extras.putInt("productId",(int)l);
                                                extras.putInt("userId",sharedPrefs.getInt(LOGIN_USER_KEY, -1));
                                                intent.putExtras(extras);
                                                startActivity(intent);
                                                break;
                                            case 1 :
                                                int id = (int) l;
                                                Product deletedProduct = productDao.findById(id);
                                                Log.d("print", String.valueOf(id));
                                                productDao.delete(deletedProduct);
                                                Toast.makeText(getApplicationContext(),
                                                        "Berhasil Hapus", Toast.LENGTH_LONG).show();
                                                RefreshListView();
                                                break;
                                        }
                                    }
                                });
                        builder.create().show();
                    }
                });
            } else {
                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @SuppressLint("Range")
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), UsrTransaction.class);
                        Bundle extras = new Bundle();
                        extras.putInt("productId",(int)l);
                        extras.putInt("userId", sharedPrefs.getInt(LOGIN_USER_KEY, -1));
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                });
            }
        } else {
            sca.swapCursor(csr);
        }

    }

    private Cursor getCursor() {
        List<Product> products = productDao.getAll();
        MatrixCursor mxcsr = new MatrixCursor(new String[]{
                BaseColumns._ID,
                "name",
                "stock",
                "price",
                "warranty"},
                0
        );
        for (Product p: productDao.getAll()) {
            mxcsr.addRow(new Object[]{p.productId,p.name,p.stock,p.price, p.warranty});
        }
        return mxcsr;
    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshListView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        csr.close();
    }

}