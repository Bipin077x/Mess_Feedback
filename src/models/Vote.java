package com.messfeedback.models;

/**
 * Represents a vote cast by a user for a specific meal on a specific date and meal type.
 */
public class Vote {
    private int voteId;
    private int userId;
    private int mealChoice;  // Meal number (1-9)
    private String date;     // Format: yyyy-MM-dd
    private String mealType; // e.g., Breakfast, Lunch, Dinner

    // Constructor with all fields
    public Vote(int voteId, int userId, int mealChoice, String date, String mealType) {
        this.voteId = voteId;
        this.userId = userId;
        this.mealChoice = mealChoice;
        this.date = date;
        this.mealType = mealType;
    }

    // Optional: in case some places still use 4-arg constructor (not recommended now)
    public Vote(int voteId, int userId, int mealChoice, String date) {
        this(voteId, userId, mealChoice, date, "Unknown"); // Assign default value to mealType
    }

    // Getters
    public int getVoteId() {
        return voteId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMealChoice() {
        return mealChoice;
    }

    public String getDate() {
        return date;
    }

    public String getMealType() {
        return mealType;
    }

    // Returns the date of the vote (for compatibility with old code)
    public String getVoteDate() {
        return date;
    }

    // Returns the mealChoice as mealId (for compatibility)
    public int getMealId() {
        return mealChoice;
    }

    // Converts the vote to a string format suitable for saving to a file
    public String toFileString() {
        return voteId + "," + userId + "," + mealChoice + "," + date + "," + mealType;
    }

    // Parses a vote from a string line in the file
    public static Vote fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length < 5) {
            System.err.println("Skipping invalid vote line: " + line);
            return null;
        }
        try {
            int voteId = Integer.parseInt(parts[0]);
            int userId = Integer.parseInt(parts[1]);
            int mealChoice = Integer.parseInt(parts[2]);
            String date = parts[3];
            String mealType = parts[4];
            return new Vote(voteId, userId, mealChoice, date, mealType);
        } catch (Exception e) {
            System.err.println("Error parsing vote line: " + line);
            return null;
        }
    }
}

