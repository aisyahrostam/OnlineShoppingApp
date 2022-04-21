package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.onlineshopping.Adapter.StockAdapter;
import com.example.onlineshopping.Models.Stock;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StockListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Stock> stockList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore firestore;
    StockAdapter stockAdapter;
    FloatingActionButton add_FLbtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);

        firestore = FirebaseFirestore.getInstance();
        add_FLbtn = findViewById(R.id.add_FLbtn);
        recyclerView = findViewById(R.id.stock_recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(this);
        displayStockData();

        add_FLbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StockListActivity.this, StockActivity.class));
                finish();
            }
        });
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
                    Stock stock = new Stock(documentSnapshot.getString("id"),
                            documentSnapshot.getString("name"),
                            documentSnapshot.getString("price"),
                            documentSnapshot.getString("quantity"),
                            documentSnapshot.getString("manufacturer"),
                            documentSnapshot.getString("category")
                    );

                    stockList.add(stock);
                }

                stockAdapter = new StockAdapter(StockListActivity.this, stockList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(stockAdapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(StockListActivity.this, "There's an error!", Toast.LENGTH_SHORT).show();
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

            Collections.sort(stockList, Stock.StockAZComparator);
            Toast.makeText(this, "Sort Stock Name from A-Z", Toast.LENGTH_SHORT).show();
            stockAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.action_sortZA) {

            Collections.sort(stockList, Stock.StockZAComparator);
            Toast.makeText(this, "Sort Stock Name from Z-A", Toast.LENGTH_SHORT).show();
            stockAdapter.notifyDataSetChanged();
            return true;

        }
        if (id == R.id.action_sortBrandAZ) {

            Collections.sort(stockList, Stock.StockManufacturerAZComparator);
            Toast.makeText(this, "Sort Stock Name from Z-A", Toast.LENGTH_SHORT).show();
            stockAdapter.notifyDataSetChanged();
            return true;

        }
        if (id == R.id.action_sortBrandZA) {

            Collections.sort(stockList, Stock.StockManufacturerZAComparator);
            Toast.makeText(this, "Sort Stock Name from Z-A", Toast.LENGTH_SHORT).show();
            stockAdapter.notifyDataSetChanged();
            return true;

        }

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
                    Stock stock = new Stock(documentSnapshot.getString("id"),
                            documentSnapshot.getString("name"),
                            documentSnapshot.getString("price"),
                            documentSnapshot.getString("quantity"),
                            documentSnapshot.getString("manufacturer"),
                            documentSnapshot.getString("category")
                    );

                    stockList.add(stock);
                }

                stockAdapter = new StockAdapter(StockListActivity.this, stockList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(stockAdapter);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(StockListActivity.this, "There's an error!", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public void deleteStockData(int position) {
        progressDialog.setTitle("Deleting stock data..");
        progressDialog.show();

        firestore.collection("Stocks").document(stockList.get(position).getId())
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(StockListActivity.this, "Item deleted!", Toast.LENGTH_SHORT).show();
                displayStockData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(StockListActivity.this, "There's an error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}