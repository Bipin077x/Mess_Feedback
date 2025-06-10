package com.messfeedback.models;

/**
 * Model class representing a feedback entry.
 */
public class Feedback {
    private int feedbackId;   // Unique ID for each feedback
    private int userId;       // ID of the user who submitted the feedback
    private String mealType;  // Meal type (Breakfast, Lunch, Dinner)
    private int rating;       // Rating given by the user (1 to 5)
    private String comments;  // Optional comments about the meal
    private String date;      // Date of feedback submission (stored as String for simplicity)

    /**
     * Constructor to initialize a Feedback object.
     */
    public Feedback(int feedbackId, int userId, String mealType, int rating, String comments, String date) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.mealType = mealType;
        this.rating = rating;
        this.comments = comments;
        this.date = date;
    }

    // === Getter and Setter Methods ===

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Converts feedback object to a readable string format.
     */
    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", userId=" + userId +
                ", mealType='" + mealType + '\'' +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
