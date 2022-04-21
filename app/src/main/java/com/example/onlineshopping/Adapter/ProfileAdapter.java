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

import com.example.onlineshopping.Models.User;
import com.example.onlineshopping.ProfileActivity;
import com.example.onlineshopping.R;
import com.example.onlineshopping.ViewHolder;
import com.example.onlineshopping.ViewHolder2;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ViewHolder2> {


    ProfileActivity profileActivity;
    List<User> profileList;
    Context context;

    public ProfileAdapter(ProfileActivity profileActivity, List<User> profileList) {
        this.profileActivity = profileActivity;
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View iView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_layout, viewGroup, false);

        ViewHolder2 viewHolder = new ViewHolder2(iView);
        viewHolder.setOnClickListener(new ViewHolder.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = profileList.get(position).getName();
                String phoneNumber = profileList.get(position).getPhoneNumber();
                String email = profileList.get(position).getEmail();
                String address = profileList.get(position).getAddress();

                Toast.makeText(profileActivity, name + "\n" + phoneNumber + "\n" + email + "\n" + address + "\n", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }


        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 viewHolder, int i) {
        viewHolder.Item_name_TXT.setText(profileList.get(i).getName());
        viewHolder.Item_PhoneNumber_TXT.setText(profileList.get(i).getPhoneNumber());
        viewHolder.Item_Email_TXT.setText(profileList.get(i).getEmail());
        viewHolder.Item_Address_TXT.setText(profileList.get(i).getAddress());
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }
}
