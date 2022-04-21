package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.Adapter.CartAdapter;
import com.example.onlineshopping.Models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Item> stockList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore firestore;
    CartAdapter cartAdapter;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private Button pay;
    private FirebaseUser mUser;
    Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        pay = (Button) findViewById(R.id.pay_btn);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.cart_recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(this);
        displayCartItems();

        Intent intent = getIntent();
        String tAmount = intent.getStringExtra(Add_ToCart_Activity.total);
        TextView totalAmount = (TextView) findViewById(R.id.totalAmount_txt);
        totalAmount.setText(tAmount);

        Intent intent2 = getIntent();
        String discount = intent2.getStringExtra(Add_ToCart_Activity.mdiscount);
        TextView discount_txt2 = (TextView) findViewById(R.id.discount_txt2);
        discount_txt2.setText(discount);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShoppingCart.this, "Customer Successfully paid", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ShoppingCart.this, HomePageActivity.class));
                finish();
            }
        });

    }


    private void displayCartItems() {

        progressDialog.setTitle("Please wait..");
        progressDialog.show();
        firestore.collection("ShoppingCart").document(mUser.getUid()).collection("My Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                stockList.clear();
                progressDialog.dismiss();
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    Item item = new Item(documentSnapshot.getString("id"),
                            documentSnapshot.getString("name"),
                            documentSnapshot.getString("price"),
                            documentSnapshot.getString("quantity"),
                            documentSnapshot.getString("manufacturer"),
                            documentSnapshot.getString("category")
                    );

                    stockList.add(item);
                }

                cartAdapter = new CartAdapter(ShoppingCart.this, stockList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(cartAdapter);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(ShoppingCart.this, "There's an error!", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void removeItemFromCart(int position) {
        progressDialog.setTitle("Deleting Item ..");
        progressDialog.show();

        firestore.collection("ShoppingCart").document(mUser.getUid()).collection("My Cart")
                .document(stockList.get(position).getId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(ShoppingCart.this, "Item deleted!", Toast.LENGTH_SHORT).show();
                displayCartItems();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(ShoppingCart.this, "There's an error!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}