package hanu.a2_1801040217.mycart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import Adapter.CartAdapter;
import Database.ProductManager;
import Model.Product;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnBack;
    RecyclerView rvCart;
    CartAdapter adapter;
    List<Product> lstCart;
    public TextView txtvPrice, txtTotalPrice, txtvEmpty;
    ProductManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        mapping();
        manager = ProductManager.getInstance(this);
        lstCart =manager.getAllData();
        int numberPr = lstCart.size();
        check(numberPr);

        txtTotalPrice.setText(manager.countPrice() + " VND");


        btnBack.setOnClickListener(this);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(lstCart, this);
        rvCart.setAdapter(adapter);

    }




    private void mapping() {
        txtvEmpty = findViewById(R.id.txtvEmpty);
        rvCart = findViewById(R.id.rvCart);
        txtvPrice = findViewById(R.id.txtvPrice);
        btnBack = findViewById(R.id.btnBack);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

    }

    private void check(int number){
        if (number == 0){
            txtvEmpty.setVisibility(View.VISIBLE);
        }else {
            txtvEmpty.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent i = new Intent(CartActivity.this, MainActivity.class);
                startActivity(i);
                break;
            default:
        }


    }
}

