package com.example.mobileapp3gpower.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mobileapp3gpower.DetailsTransactionActivity;
import com.example.mobileapp3gpower.ListProduct;
import com.example.mobileapp3gpower.R;
import com.example.mobileapp3gpower.UpdateProduct;
import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.Product;
import com.example.mobileapp3gpower.database.ProductDao;
import com.example.mobileapp3gpower.database.Transaction;
import com.example.mobileapp3gpower.database.TransactionDao;
import com.example.mobileapp3gpower.database.UserDao;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {
    private static final String USER_ROLE_KEY = "key_user_role";
    private static final String PREFERENCE_KEY = "mobileapp3gpower_sharedprefs";
    private static final String  LOGIN_USER_KEY = "key_id_user";

    private ListView myListView;
    private SharedPreferences sharedPrefs;
    private ProductDao productDao;
    private UserDao userDao;
    private TransactionDao transactionDao;
    private ImageButton btnBack;

    private Cursor csr;
    private SimpleCursorAdapter sca;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionFragment newInstance(String param1, String param2) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPrefs = this.getActivity().getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);

        productDao = AppDBProvider.getInstance(getActivity()).productDao();
        userDao = AppDBProvider.getInstance(getActivity()).userDao();
        transactionDao = AppDBProvider.getInstance(getActivity()).transactionDao();

        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myListView = getView().findViewById(R.id.myListView);

        RefreshListView();

    }

    private void RefreshListView() {
        csr = getCursor();
        if (sca == null) {
            sca = new SimpleCursorAdapter(
                    getActivity(),
                    R.layout.view_transaction,csr,
                    new String[]{"name","detail", "total"},
                    new int[]{R.id.txtv_name_product_inp,R.id.txtv_detail, R.id.txtv_total_inp},
                    0
            );

            myListView.setAdapter(sca);
            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @SuppressLint("Range")
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), DetailsTransactionActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt("transactionId",(int)l);
                    intent.putExtras(extras);
                    startActivity(intent);
//                    Toast.makeText(getActivity(), String.valueOf(transactionDao.findById((int)l).total), Toast.LENGTH_SHORT).show();
                }
            });
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
                "name",
                "detail",
                "total"},
                0
        );
        // outputnya di sini
        for (Transaction p: transactionList) {
            Product currentProduct = productDao.findById(p.productId);
            String detail = p.amount + " X Rp. " + currentProduct.price;

            mxcsr.addRow(new Object[]{p.transactionId, currentProduct.name, detail, "Rp. "+p.total});
        }
        return mxcsr;
    }

    @Override
    public void onResume() {
        super.onResume();
        RefreshListView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        csr.close();
    }
}