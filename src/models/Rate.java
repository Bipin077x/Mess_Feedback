package com.messfeedback.models;

public class Rate {
    private int userId;
    private int mealChoice;
    private int rating;
    private String date;

    // Constructor
    public Rate(int userId, int mealChoice, int rating, String date) {
        this.userId = userId;
        this.mealChoice = mealChoice;
        this.rating = rating;
        this.date = date;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public int getMealChoice() {
        return mealChoice;
    }

    public int getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }

    /**
     * Converts the Rate object to a string for file storage.
     * Format: userId,mealChoice,rating,date
     */
    public String toFileString() {
        return userId + "," + mealChoice + "," + rating + "," + date;
    }

    /**
     * Parses a line from the file and returns a Rate object.
     * Expected format: userId,mealChoice,rating,date
     */
    public static Rate fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 4) return null;

        try {
            int userId = Integer.parseInt(parts[0]);
            int mealChoice = Integer.parseInt(parts[1]);
            int rating = Integer.parseInt(parts[2]);
            String date = parts[3];
            return new Rate(userId, mealChoice, rating, date);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
