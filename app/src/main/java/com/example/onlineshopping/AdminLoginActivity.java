package com.example.onlineshopping;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText EDTemail, EDTpassword;
    private Button login;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        EDTemail = (EditText) findViewById(R.id.email);
        EDTpassword = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = EDTemail.getText().toString().trim();
        String password = EDTpassword.getText().toString().trim();

        if (email.isEmpty()) {
            EDTemail.setError("Email is required");
            EDTemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            EDTemail.setError("Please provide a valid email");
            EDTemail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            EDTpassword.setError("Password is required");
            EDTpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            EDTpassword.setError("Password must be 6 or more characters");
            EDTpassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(AdminLoginActivity.this, "Admin signed in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminLoginActivity.this, AdminHomePageActivity.class));
                    finish();

                } else {

                    Toast.makeText(AdminLoginActivity.this, "Admin failed to login!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

