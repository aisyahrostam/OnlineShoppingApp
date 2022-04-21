package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.onlineshopping.Adapter.ItemsAdapter;
import com.example.onlineshopping.Models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Item> stockList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore firestore;
    ItemsAdapter itemsAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.item_recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(this);
        displayStockData();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem stockItem = menu.findItem(R.id.action_searchBar);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(stockItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void searchItem(String query) {
        progressDialog.setTitle("Searching Item..");
        progressDialog.show();

        firestore.collection("Stocks").whereEqualTo("search", query.toLowerCase()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                itemsAdapter = new ItemsAdapter(ItemsActivity.this, stockList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(itemsAdapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(ItemsActivity.this, "There's an error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_searchBar) {


            return true;
        }

        if (id == R.id.action_sortAZ) {

            Collections.sort(stockList, Item.itemAZComparator);
            Toast.makeText(this, "Sort Item Name from A-Z", Toast.LENGTH_SHORT).show();
            itemsAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_sortZA) {

            Collections.sort(stockList, Item.itemZAComparator);
            Toast.makeText(this, "Sort Item Name from Z-A", Toast.LENGTH_SHORT).show();
            itemsAdapter.notifyDataSetChanged();
            return true;

        }
        if (id == R.id.action_sortBrandAZ) {

            Collections.sort(stockList, Item.itemBrandAZComparator);
            Toast.makeText(this, "Sort Item Brand from A-Z", Toast.LENGTH_SHORT).show();
            itemsAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_sortBrandZA) {

            Collections.sort(stockList, Item.itemBrandZAComparator);
            Toast.makeText(this, "Sort Item Brand from Z-A", Toast.LENGTH_SHORT).show();
            itemsAdapter.notifyDataSetChanged();
            return true;

        }
//        if (id == R.id.action_priceAscending) {
//
//            Collections.sort(stockList, Item.itemAscendingComparator);
//            Toast.makeText(this,"Sort price from lowest - highest", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        if (id == R.id.action_priceDescending) {
//            Collections.sort(stockList, Item.itemDescendingComparator);
//            Toast.makeText(this,"Sort price from highest - lowest", Toast.LENGTH_SHORT).show();
//
//            return true;
//        }


        return super.onOptionsItemSelected(item);
    }

    private void displayStockData() {

        progressDialog.setTitle("Please wait..");
        progressDialog.show();
        firestore.collection("Stocks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                itemsAdapter = new ItemsAdapter(ItemsActivity.this, stockList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(itemsAdapter);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(ItemsActivity.this, "There's an error!", Toast.LENGTH_SHORT).show();

                    }
                });
    }


}