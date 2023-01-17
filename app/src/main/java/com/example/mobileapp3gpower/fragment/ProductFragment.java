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
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mobileapp3gpower.AddProduct;
import com.example.mobileapp3gpower.ListProduct;
import com.example.mobileapp3gpower.R;
import com.example.mobileapp3gpower.UpdateProduct;
import com.example.mobileapp3gpower.UsrTransaction;
import com.example.mobileapp3gpower.database.AppDBProvider;
import com.example.mobileapp3gpower.database.Product;
import com.example.mobileapp3gpower.database.ProductDao;
import com.example.mobileapp3gpower.databinding.ActivityListProductBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
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
//        binding = ActivityListProductBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();

        sharedPrefs = this.getActivity().getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        productDao = AppDBProvider.getInstance(getActivity()).productDao();

//        return view;
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        RefreshListView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myListView = getView().findViewById(R.id.myListView);
        FloatingActionButton fab = getView().findViewById(R.id.fab);

        if (Objects.equals(sharedPrefs.getString(USER_ROLE_KEY, "user"), "user")) {
            Log.d("print", sharedPrefs.getString(USER_ROLE_KEY, "user"));
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddProduct.class);
                startActivity(i);
            }
        });
        RefreshListView();
    }
    private void RefreshListView() {
        csr = getCursor();
        if (sca == null) {
            sca = new SimpleCursorAdapter(
                    getActivity(),
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
                                AlertDialog.Builder(getActivity());
                        builder.setTitle("Pilihan");

                        final CharSequence[] dialogitem = {"Update Data", "Hapus Data"};
                        builder.setItems(dialogitem, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int
                                            item) {
                                        switch(item){
                                            case 0 :
                                                Intent intent = new Intent(getActivity(), UpdateProduct.class);
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
                                                Toast.makeText(getActivity(),
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
                        Intent intent = new Intent(getActivity(), UsrTransaction.class);
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
            mxcsr.addRow(new Object[]{p.productId,p.name,p.stock,("Rp."+p.price), (p.warranty+" tahun")});
        }
        return mxcsr;
    }
}