package com.example.onlineshopping.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.ItemsActivity;
import com.example.onlineshopping.Models.Stock;
import com.example.onlineshopping.R;
import com.example.onlineshopping.StockActivity;
import com.example.onlineshopping.StockListActivity;
import com.example.onlineshopping.ViewHolder;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<ViewHolder> {

    StockListActivity stockListActivity;
    ItemsActivity itemsActivity;
    List<Stock> stockList;
    Context context;

    public StockAdapter(StockListActivity stockListActivity, List<Stock> stockList) {
        this.stockListActivity = stockListActivity;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View iView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stock_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(iView);
        viewHolder.setOnClickListener(new ViewHolder.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = stockList.get(position).getId();
                String name = stockList.get(position).getName();
                String price = stockList.get(position).getPrice();
                String quantity = stockList.get(position).getQuantity();
                String manufacturer = stockList.get(position).getManufacturer();
                String category = stockList.get(position).getCategory();

                Toast.makeText(stockListActivity, name + "\n" + price + "\n" + quantity + "\n" + manufacturer + "\n" + category, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(stockListActivity);
                String[] actions = {"Update", "Delete"};
                builder.setItems(actions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (i == 0) {

                            String id = stockList.get(position).getId();
                            String name = stockList.get(position).getName();
                            String price = stockList.get(position).getPrice();
                            String quantity = stockList.get(position).getQuantity();
                            String manufacturer = stockList.get(position).getManufacturer();
                            String category = stockList.get(position).getCategory();

                            Intent intent = new Intent(stockListActivity, StockActivity.class);
                            intent.putExtra("id", id);
                            intent.putExtra("name", name);
                            intent.putExtra("price", price);
                            intent.putExtra("quantity", quantity);
                            intent.putExtra("manufacturer", manufacturer);
                            intent.putExtra("category", category);
                            stockListActivity.startActivity(intent);


                        }

                        if (i == 1) {

                            stockListActivity.deleteStockData(position);

                        }
                    }
                }).create().show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.Item_name_TXT.setText(stockList.get(i).getName());
        viewHolder.Item_Price_TXT.setText(stockList.get(i).getPrice());
        viewHolder.Item_Quantity_TXT.setText(stockList.get(i).getQuantity());
        viewHolder.Item_Manufacturer_TXT.setText(stockList.get(i).getManufacturer());
        viewHolder.Item_Category_TXT.setText(stockList.get(i).getCategory());
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }
}
