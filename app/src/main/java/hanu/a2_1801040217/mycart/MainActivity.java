package hanu.a2_1801040217.mycart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProductAdapter;
import Database.DbHelper;
import Model.Product;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbarHome;
    TextView searchBar;
    RecyclerView rvHome;
    ImageView btnCart;
    ImageButton btnSearch;
    public static DbHelper myDB;
    public List<Product> lstProduct;
    public ProductAdapter productAdapter;
    public static ArrayList<Product> lstCart;
    ProductAsyncTask asyncTask;
    RecyclerView recyclerView;
    List<Product> list;
    private String URL_PRODUCT = "https://mpr-cart-api.herokuapp.com/products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        goCart();

        asyncTask = new ProductAsyncTask(this, rvHome, lstProduct);
        asyncTask.execute(URL_PRODUCT);


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Product> searchproduct =new ArrayList<>();
                for (int i=0 ; i<asyncTask.listProduct().size(); i++){
                    Log.i("onTextChanged: ",asyncTask.listProduct().get(i).getName());
                    if (asyncTask.listProduct().get(i).getName().toLowerCase().contains(String.valueOf(s))){
                        searchproduct.add(asyncTask.listProduct().get(i));
                    }
                }
                adapter(searchproduct);
                if(String.valueOf(s)==null||String.valueOf(s)==""){
                    adapter(asyncTask.listProduct());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private void mapping() {
        btnSearch = findViewById(R.id.btnSearch);
        toolbarHome = findViewById(R.id.toolBarHome);
        rvHome = findViewById(R.id.rvHome);
        searchBar = findViewById(R.id.searchBar);
        btnCart = findViewById(R.id.btnCart);
        recyclerView= findViewById(R.id.rvHome);
        if (lstCart != null){
            lstCart = new ArrayList<>();
        }
    }



    public void goCart() {
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hanu.a2_1801040217.mycart.MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    public void adapter(List<Product> list){
        productAdapter = new ProductAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
    }




}