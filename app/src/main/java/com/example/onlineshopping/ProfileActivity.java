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

import com.example.onlineshopping.Adapter.ProfileAdapter;
import com.example.onlineshopping.Adapter.StockAdapter;
import com.example.onlineshopping.Models.Stock;
import com.example.onlineshopping.Models.User;
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

public class ProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<User> profileList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore firestore;
    ProfileAdapter profileAdapter;
    ProfileActivity profileActivity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.profile_recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(this);
        displayProfile();

    }


    private void displayProfile() {

        progressDialog.setTitle("Please wait..");
        progressDialog.show();
        firestore.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                profileList.clear();
                progressDialog.dismiss();
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    User user = new User(documentSnapshot.getString("Name"),
                            documentSnapshot.getString("Email"),
                            documentSnapshot.getString("Phone Number"),
                            documentSnapshot.getString("Address"),
                            documentSnapshot.getString("Card Number"),
                            documentSnapshot.getString("Pin")

                    );

                    profileList.add(user);
                }

                profileAdapter = new ProfileAdapter(ProfileActivity.this, profileList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(profileAdapter);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "There's an error!", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}