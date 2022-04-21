package com.example.onlineshopping;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class ViewHolder extends RecyclerView.ViewHolder {


    public TextView Item_name_TXT, Item_Price_TXT, Item_Quantity_TXT, Item_Manufacturer_TXT, Item_Category_TXT;
    View nView;

    public ViewHolder(@NonNull View iView) {
        super(iView);

        nView = iView;

        iView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nitemClickListener.onItemClick(view, getAdapterPosition());

            }
        });


        iView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                nitemClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });

        Item_name_TXT = iView.findViewById(R.id.displayName);
        Item_Price_TXT = iView.findViewById(R.id.displayPrice);
        Item_Quantity_TXT = iView.findViewById(R.id.displayQuantity);
        Item_Manufacturer_TXT = iView.findViewById(R.id.displayManufacturer);
        Item_Category_TXT = iView.findViewById(R.id.displayCategory);

    }

    private ViewHolder.ItemClickListener nitemClickListener;

    public interface ItemClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }

    public void setOnClickListener(ViewHolder.ItemClickListener itemClickListener) {
        nitemClickListener = itemClickListener;
    }
}
