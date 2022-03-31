package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registerUser;
    private EditText EDTname, EDTphoneNumber, EDTemail, EDTpassword;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        EDTname = (EditText) findViewById(R.id.name);
        EDTphoneNumber = (EditText) findViewById(R.id.phoneNumber);
        EDTemail = (EditText) findViewById(R.id.email);
        EDTpassword = (EditText) findViewById(R.id.password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {

        String email = EDTemail.getText().toString().trim();
        String password = EDTpassword.getText().toString().trim();
        String name = EDTname.getText().toString().trim();
        String phoneNumber = EDTphoneNumber.getText().toString().trim();

        if (name.isEmpty()) {
            EDTname.setError("Full name is required");
            EDTname.requestFocus();
            return;
        }

        if (phoneNumber.isEmpty()) {
            EDTphoneNumber.setError("Phone Number is required");
            EDTphoneNumber.requestFocus();
            return;
        }
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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userid = mAuth.getCurrentUser().getUid();
                            Toast.makeText(RegisterActivity.this, "on complete create user", Toast.LENGTH_SHORT).show();

                            Map<String, Object> user = new HashMap<>();
                            user.put("Email", email);
                            user.put("Name", name);
                            user.put("Phone Number", phoneNumber);
                            db.collection("user")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(RegisterActivity.this, "User has registered successfully!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, HomePageActivity.class));
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "User failed to add additional info!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}

