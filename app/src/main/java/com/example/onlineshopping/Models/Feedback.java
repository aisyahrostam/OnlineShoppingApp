package com.example.onlineshopping.Models;

public class Feedback extends FeedbackId {

        private String review, rating;

        public String getReview() {
            return review;
        }

        public String getRating() {
            return rating;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

}
