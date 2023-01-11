package com.example.mobileapp3gpower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.Product;
import com.example.mobileapp3gpower.database.ProductDao;
import com.example.mobileapp3gpower.database.Transaction;
import com.example.mobileapp3gpower.database.TransactionDao;
import com.example.mobileapp3gpower.database.UserDao;

import java.util.List;
import java.util.Objects;

public class ViewTransaction extends AppCompatActivity {
    private static final String USER_ROLE_KEY = "key_user_role";
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";
    private static final String  LOGIN_USER_KEY = "key_id_user";

    private ListView myListView;
    private SharedPreferences sharedPrefs;
    private ProductDao productDao;
    private UserDao userDao;
    private TransactionDao transactionDao;

    private Cursor csr;
    private SimpleCursorAdapter sca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);

        sharedPrefs = getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);

        myListView = findViewById(R.id.myListView);
        productDao = AppDBProvider.getInstance(this).productDao();
        userDao = AppDBProvider.getInstance(this).userDao();
        transactionDao = AppDBProvider.getInstance(this).transactionDao();

        RefreshListView();
    }

    private void RefreshListView() {
        csr = getCursor();
        if (sca == null) {
            sca = new SimpleCursorAdapter(
                    this,
                    R.layout.view_transaction,csr,
                    new String[]{"_id", "userId","productId", "date", "total"},
                    new int[]{R.id.txtv_id_inp,R.id.txtv_id_user_inp,R.id.txtv_id_product_inp, R.id.txtv_date_inp, R.id.txtv_total_inp},
                    0
            );

            myListView.setAdapter(sca);

        } else {
            sca.swapCursor(csr);
        }

    }

    private Cursor getCursor() {
        List<Transaction> transactionList;

        if (Objects.equals(sharedPrefs.getString(USER_ROLE_KEY, "user"), "admin")) {
            transactionList = transactionDao.getAll();
        } else {
            transactionList = transactionDao.findByUser(sharedPrefs.getInt(LOGIN_USER_KEY, -1));
        }

        MatrixCursor mxcsr = new MatrixCursor(new String[]{
                BaseColumns._ID,
                "userId",
                "productId",
                "date",
                "total"},
                0
        );
        for (Transaction p: transactionList) {
            mxcsr.addRow(new Object[]{p.transactionId,p.userId,p.productId,p.date, p.total});
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