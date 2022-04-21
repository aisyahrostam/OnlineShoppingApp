package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedBackActivity extends AppCompatActivity {

    TextView rateCounter, displayRating;
    RatingBar ratingBar;
    EditText EDT_review;
    Button save_BTN;
    float rateVal;
    String temp;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        ratingBar = findViewById(R.id.ratingBar);
        rateCounter = findViewById(R.id.rateCounter);
        save_BTN = findViewById(R.id.save_BTN);
        EDT_review = findViewById(R.id.EDT_review);
        displayRating = findViewById(R.id.displayRating);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateVal = ratingBar.getRating();
                if (rateVal <= 1 && rateVal > 0) {
                    rateCounter.setText("Very Bad " + rateVal + "/5");
                } else if (rateVal <= 2 && rateVal > 1) {
                    rateCounter.setText("Okay " + rateVal + "/5");
                } else if (rateVal <= 3 && rateVal > 2) {
                    rateCounter.setText("Good " + rateVal + "/5");
                } else if (rateVal <= 4 && rateVal > 3) {
                    rateCounter.setText("Very Good " + rateVal + "/5");
                } else if (rateVal <= 5 && rateVal > 4) {
                    rateCounter.setText("Excellent " + rateVal + "/5");
                }
            }
        });

        save_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = rateCounter.getText().toString();
                displayRating.setText("Your Feedback: " + temp + "\n" + "\n" + "Review: " + EDT_review.getText());
                ratingBar.setRating(0);

                String userid = mAuth.getCurrentUser().getUid();
                String review = EDT_review.getText().toString();
                String rating = rateCounter.getText().toString();


                if (review.isEmpty()) {
                    Toast.makeText(FeedBackActivity.this, "Review is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> feedbackMap = new HashMap<>();
                    Map<String, Object> user = new HashMap<>();
                    feedbackMap.put("Review", review);
                    feedbackMap.put("Rating", rating);


                    firestore.collection("Feedback").document(mUser.getUid()).collection("My Feedback").add(feedbackMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(FeedBackActivity.this, "New Feedback is Saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FeedBackActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FeedBackActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                ;


            }
        });
    }

    ;


}
