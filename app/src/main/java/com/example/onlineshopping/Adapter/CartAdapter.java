package com.example.onlineshopping.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.Models.Item;
import com.example.onlineshopping.R;
import com.example.onlineshopping.ShoppingCart;
import com.example.onlineshopping.ViewHolder;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<ViewHolder> {

    ShoppingCart shoppingCart;
    CartAdapter cartAdapter;
    List<Item> stockList;
    Context context;

    public CartAdapter(ShoppingCart shoppingCart, List<Item> stockList) {
        this.shoppingCart = shoppingCart;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View iView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(iView);
        viewHolder.setOnClickListener(new ViewHolder.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = stockList.get(position).getId();
                String name = stockList.get(position).getName();
                String price = stockList.get(position).getPrice();
                String manufacturer = stockList.get(position).getManufacturer();
                String category = stockList.get(position).getCategory();

                Toast.makeText(shoppingCart, name + "\n" + price + "\n" + manufacturer + "\n" + category, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onItemLongClick(View view, int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(shoppingCart);
                String[] actions = {"Remove From Cart"};
                builder.setItems(actions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {

                            shoppingCart.removeItemFromCart(position);

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
        viewHolder.Item_Manufacturer_TXT.setText(stockList.get(i).getManufacturer());
        viewHolder.Item_Category_TXT.setText(stockList.get(i).getCategory());
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }
}
