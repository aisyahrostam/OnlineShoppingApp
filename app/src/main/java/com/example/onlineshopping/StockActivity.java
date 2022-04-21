package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StockActivity extends AppCompatActivity {


    private FirebaseFirestore firestore;
    EditText Item_name_EDT, Item_Price_EDT, Item_Quantity_EDT, Item_Manufacturer_EDT, Item_Category_EDT;
    Button save_btn;
    String uid, uname, uprice, uquantity, umanufacturer, ucategory;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        firestore = FirebaseFirestore.getInstance();
        actionBar = getSupportActionBar();
        actionBar.setTitle("Add new stock data");

        Item_name_EDT = findViewById(R.id.Item_name_EDT);
        Item_Price_EDT = findViewById(R.id.Item_Price_EDT);
        Item_Quantity_EDT = findViewById(R.id.Item_Quantity_EDT);
        Item_Manufacturer_EDT = findViewById(R.id.Item_Manufacturer_EDT);
        Item_Category_EDT = findViewById(R.id.Item_Category_EDT);
        save_btn = findViewById(R.id.save_btn);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actionBar.setTitle("Update Stock Data");
            save_btn.setText("Update");
            uid = bundle.getString("id");
            uname = bundle.getString("name");
            uprice = bundle.getString("price");
            uquantity = bundle.getString("quantity");
            umanufacturer = bundle.getString("manufacturer");
            ucategory = bundle.getString("category");

            Item_name_EDT.setText(uname);
            Item_Price_EDT.setText(uprice);
            Item_Quantity_EDT.setText(uquantity);
            Item_Manufacturer_EDT.setText(umanufacturer);
            Item_Category_EDT.setText(ucategory);

        } else {
            actionBar.setTitle("Add new stock data");
            save_btn.setText("Add Stock");
        }


        //Button to upload data to db
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {

                    String id = uid;
                    String name = Item_name_EDT.getText().toString().trim();
                    String price = Item_Price_EDT.getText().toString().trim();
                    String quantity = Item_Quantity_EDT.getText().toString().trim();
                    String manufacturer = Item_Manufacturer_EDT.getText().toString().trim();
                    String category = Item_Category_EDT.getText().toString().trim();

                    UpdateData(id, name, price, quantity, manufacturer, category);
                } else {
                    //enter info
                    String name = Item_name_EDT.getText().toString().trim();
                    String price = Item_Price_EDT.getText().toString().trim();
                    String quantity = (Item_Quantity_EDT.getText().toString().trim());
                    String manufacturer = Item_Manufacturer_EDT.getText().toString().trim();
                    String category = Item_Category_EDT.getText().toString().trim();

                    saveData(name, price, quantity, manufacturer, category);
                }
                startActivity(new Intent(StockActivity.this, StockListActivity.class));
                finish();
            }

            private void UpdateData(String id, String name, String price, String quantity, String manufacturer, String category) {
                firestore.collection("Stocks").document(id).update("name", name, "search", name.toLowerCase(), "search2", category.toLowerCase(), "search3", manufacturer.toLowerCase(), "price", price, "quantity", quantity, "manufacturer", manufacturer, "category", category)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(StockActivity.this, "Stock data updated", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(StockActivity.this, "There's an error!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    private void saveData(String name, String price, String quantity, String manufacturer, String category) {

        String id = UUID.randomUUID().toString();

        Map<String, Object> stock = new HashMap<>();
        stock.put("id", id);
        stock.put("name", name);
        stock.put("price", price);
        stock.put("quantity", quantity);
        stock.put("manufacturer", manufacturer);
        stock.put("category", category);
        stock.put("search", name.toLowerCase());
        stock.put("search2", category.toLowerCase());
        stock.put("search3", manufacturer.toLowerCase());

        //add data to db firestore
        firestore.collection("Stocks").document(id).set(stock)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        //called after data is added to the database successfully
                        Toast.makeText(StockActivity.this, "Data is being saved!", Toast.LENGTH_SHORT).show();

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //called after data fails to add to the database
                        Toast.makeText(StockActivity.this, "There's an error!", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}