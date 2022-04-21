package com.example.onlineshopping.Models;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

public class FeedbackId {
    @Exclude
    public String FeedbackId;

    public  <T extends FeedbackId> T withId(@NonNull final  String id){

        this.FeedbackId=id;
        return (T) this;
    }
}
