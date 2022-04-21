package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;


public class AdminHomePageActivity extends AppCompatActivity {

    ImageView profile_btn, viewStock_btn, addStock_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        profile_btn = findViewById(R.id.profile_btn);
        viewStock_btn = findViewById(R.id.viewStock_btn);
        addStock_btn = findViewById(R.id.addStock_btn);

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this, ProfileActivity.class));

            }
        });

        addStock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this, StockActivity.class));

            }
        });

        viewStock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this, StockListActivity.class));

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_logout) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminHomePageActivity.this, MainActivity.class));
            return true;
        }


        return true;
    }
}