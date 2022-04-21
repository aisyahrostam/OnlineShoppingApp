package com.example.onlineshopping;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class ViewHolder2 extends RecyclerView.ViewHolder {


    public TextView Item_name_TXT, Item_PhoneNumber_TXT, Item_Email_TXT, Item_Address_TXT;
    View nView;

    public ViewHolder2(@NonNull View iView) {
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
        Item_PhoneNumber_TXT = iView.findViewById(R.id.displayPhoneNumber);
        Item_Email_TXT = iView.findViewById(R.id.displayEmail);
        Item_Address_TXT = iView.findViewById(R.id.displayAddress);

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
