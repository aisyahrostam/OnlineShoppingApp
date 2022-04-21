package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Add_ToCart_Activity extends AppCompatActivity {

    TextView Item_name_EDT, Item_Price_EDT, Item_Manufacturer_EDT, Item_Category_EDT;
    EditText Item_Quantity_EDT;
    Button save_btn;
    String uid, uname, uprice, uquantity, umanufacturer, ucategory;
    FirebaseFirestore firestore;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    List<Map<String, Object>> ItemsList = new ArrayList<Map<String, Object>>();
    ArrayList<Double> ItemsPriceList = new ArrayList<Double>();
    public static final String total = "prices";
    public static final String mdiscount = "discount";
    private double totalAmount = 0;
    private double discount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        firestore = FirebaseFirestore.getInstance();


        reference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        Item_name_EDT = findViewById(R.id.Item_name_EDT);
        Item_Price_EDT = findViewById(R.id.Item_Price_EDT);
        Item_Quantity_EDT = findViewById(R.id.Item_Quantity_EDT);
        Item_Manufacturer_EDT = findViewById(R.id.Item_Manufacturer_EDT);
        Item_Category_EDT = findViewById(R.id.Item_Category_EDT);
        save_btn = findViewById(R.id.save_btn);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            save_btn.setText("Add To Cart");
            uid = bundle.getString("id");
            uname = bundle.getString("name");
            uprice = bundle.getString("price");
            umanufacturer = bundle.getString("manufacturer");
            ucategory = bundle.getString("category");

            Item_name_EDT.setText(uname);
            Item_Price_EDT.setText(uprice);
            Item_Quantity_EDT.setText(uquantity);
            Item_Manufacturer_EDT.setText(umanufacturer);
            Item_Category_EDT.setText(ucategory);

        } else {
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

                    AddItem(id, name, price, quantity, manufacturer, category);
                }
//                startActivity(new Intent(Add_ToCart_Activity.this, ItemsActivity.class));
//                finish();
                Intent i = new Intent(Add_ToCart_Activity.this, ShoppingCart.class);
                i.putExtra(total, String.valueOf(totalAmount));
                i.putExtra(mdiscount, String.valueOf(discount));
                startActivity(i);
            }

            private void AddItem(String id, String name, String price, String quantity, String manufacturer, String category) {
                id = UUID.randomUUID().toString();

                Map<String, Object> Cart = new HashMap<>();
                Cart.put("id", id);
                Cart.put("name", name);
                Cart.put("price", price);
                Cart.put("quantity", quantity);
                Cart.put("manufacturer", manufacturer);
                Cart.put("category", category);
                Cart.put("search", name.toLowerCase());
                Cart.put("search", category.toLowerCase());
                String userid = mAuth.getCurrentUser().getUid();


                ItemsList.add(Cart);
                Log.d("ItemsList", String.valueOf(ItemsList));
                for (int i = 0; i < ItemsList.size(); i++) {

                    Log.d("ItemsList", String.valueOf(ItemsList.get(i)));

                    Double dprice = Double.parseDouble(String.valueOf(ItemsList.get(i).get("price")));
                    Double dquantity = Double.parseDouble(String.valueOf(ItemsList.get(i).get("quantity")));
                    Double calculation = dprice * dquantity;
                    Log.d("dprice", String.valueOf(dprice));
                    Log.d("dquantity", String.valueOf(dquantity));
                    Log.d("calculation", String.valueOf(calculation));

                    ItemsPriceList.add(calculation);
                    totalAmount = totalAmount + calculation;
                    Log.d("totalAmount", String.valueOf(totalAmount));

                    if (totalAmount >= 100) {
                        discount = totalAmount / 100 * 15;
                        totalAmount = totalAmount - discount;
                        Log.d("discount", String.valueOf(discount));
                    } else {
                        totalAmount = totalAmount;
                    }

                    Log.d("totalAmount", String.valueOf(totalAmount));
                }
//                ItemsList.add(Cart);
//                Log.d("ItemsList", String.valueOf(ItemsList));
//                for(int i = 0; i < ItemsList.size(); i++) {
//
//                    Log.d("ItemsList[i]", String.valueOf(ItemsList.get(i)));
//                    Log.d("ItemsList[price]", String.valueOf(ItemsList.get(i).get("price")));
//
//                    Double dprice= Double.parseDouble(price);
//                    Double dquantity = Double.parseDouble(quantity);
//                    Double calculation = dprice * dquantity;
//                    Log.d("dprice", String.valueOf(dprice));
//                    Log.d("dquantity", String.valueOf(dquantity));
//                    Log.d("calculation", String.valueOf(calculation));
//
//                    ItemsPriceList.add(calculation);
//                    totalAmount = totalAmount + calculation;
//
//                    if(totalAmount >= 100) {
//                        double discount = totalAmount / 100 * 15;
//                        totalAmount = totalAmount - discount;
//                        Log.d("discount", String.valueOf(discount));
//                    } else{
//                        totalAmount = totalAmount ;
//                    }
//
//                    Log.d("totalAmount", String.valueOf(totalAmount));
//                }


//                for(int i = 0; i < ItemsPriceList.size(); i++)
//                if (!ItemsPriceList.contains(price)) {
//                    ItemsPriceList.add(dprice);
//                    totalAmount = totalAmount + calculation;
//                }
//                Log.d("myTag", String.valueOf(totalAmount));
//                    TextView Amount = (TextView) findViewById(R.id.sum);
//                    Amount.setText( String.valueOf(totalAmount) );
                //add data to db firestore
                firestore.collection("ShoppingCart").document(mUser.getUid()).collection("My Cart").add(Cart).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Add_ToCart_Activity.this, "Item is added to cart!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Add_ToCart_Activity.this, "Item failed to add to cart!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_ToCart_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//                firestore.collection("ShoppingCart").document(id).set(stock)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//
//                                //called after data is added to the database successfully
//                                Toast.makeText(Add_ToCart_Activity.this, "Item added to Cart!", Toast.LENGTH_SHORT).show();
//
//                            }
//                        })
//
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                                //called after data fails to add to the database
//                                Toast.makeText(Add_ToCart_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        });

            }
        });
    }


}


//                }
//            }
//        });
//
//
//    }}