package com.messfeedback.dao;

import com.messfeedback.models.Feedback;
import com.messfeedback.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackDAO {
    // File name where feedbacks are stored
    private final String FILE_NAME = "feedbacks.txt";

    /**
     * Submit new feedback by appending it to the feedback file.
     * @param feedback Feedback object containing details
     * @return true if feedback was successfully saved, false otherwise
     */
    public boolean submitFeedback(Feedback feedback) {
        try {
            // Prepare CSV line string from feedback object fields
            String line = feedback.getFeedbackId() + "," +
                    feedback.getUserId() + "," +
                    feedback.getMealType() + "," +
                    feedback.getRating() + "," +
                    escapeCommas(feedback.getComments()) + "," +  // Handle commas in comments by replacing them
                    feedback.getDate();

            // Append the feedback line to the file
            FileUtil.appendLine(FILE_NAME, line);
            return true;
        } catch (Exception e) {
            // Print error message if something goes wrong while writing
            System.out.println("Error writing feedback: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieve all feedback entries from the file.
     * @return List of Feedback objects
     */
    public List<Feedback> getAllFeedbacks() {
        // Read all lines from the feedback file
        List<String> lines = FileUtil.readAllLines(FILE_NAME);
        List<Feedback> feedbackList = new ArrayList<>();

        // Process each line to create Feedback objects
        for (String line : lines) {
            // Split line by comma (simple CSV parsing)
            String[] parts = splitCSV(line);

            // Check if line has exactly 6 parts (fields)
            if (parts.length == 6) {
                try {
                    // Parse fields from string array
                    int feedbackId = Integer.parseInt(parts[0]);
                    int userId = Integer.parseInt(parts[1]);
                    String mealType = parts[2];
                    int rating = Integer.parseInt(parts[3]);
                    String comments = parts[4];
                    String date = parts[5];

                    // Create Feedback object and add to list
                    feedbackList.add(new Feedback(feedbackId, userId, mealType, rating, comments, date));
                } catch (NumberFormatException e) {
                    // Skip invalid lines and notify
                    System.out.println("Skipping invalid feedback line: " + line);
                }
            }
        }
        return feedbackList;
    }

    /**
     * Get feedback list filtered by user ID.
     * @param userId ID of the user
     * @return List of Feedback objects for that user
     */
    public List<Feedback> getFeedbacksByUserId(int userId) {
        List<Feedback> all = getAllFeedbacks();
        List<Feedback> result = new ArrayList<>();
        // Filter feedbacks matching the given userId
        for (Feedback f : all) {
            if (f.getUserId() == userId) {
                result.add(f);
            }
        }
        return result;
    }
    public void showAverageRatings() {
        Map<String, List<Integer>> ratingsMap = new HashMap<>();

        for (Feedback fb : getAllFeedbacks()) {
            ratingsMap.putIfAbsent(fb.getMealType().toLowerCase(), new ArrayList<>());
            ratingsMap.get(fb.getMealType().toLowerCase()).add(fb.getRating());
        }

        String topMeal = "";
        double maxAvg = 0;

        System.out.println("Average Ratings by Meal Type:");
        for (String meal : ratingsMap.keySet()) {
            List<Integer> ratings = ratingsMap.get(meal);
            double avg = ratings.stream().mapToInt(i -> i).average().orElse(0);
            System.out.printf("- %s: %.2f\n", meal, avg);
            if (avg > maxAvg) {
                maxAvg = avg;
                topMeal = meal;
            }
        }

        if (!topMeal.isEmpty()) {
            System.out.println("🍽️ Top Meal (by average rating): " + topMeal + " (Avg: " + maxAvg + ")");
        }
    }

    /**
     * Helper method to replace commas in comments with semicolons.
     * This avoids issues with splitting CSV lines by commas.
     * @param input Original comment string
     * @return Comment string with commas replaced
     */
    private String escapeCommas(String input) {
        if (input.contains(",")) {
            return input.replace(",", ";"); // simple replacement to avoid CSV split issues
        }
        return input;
    }

    /**
     * Helper method to split CSV lines by commas.
     * Note: This is a simple split; it doesn't handle quotes or complex CSV cases.
     * @param line CSV line string
     * @return Array of field values
     */
    private String[] splitCSV(String line) {
        return line.split(",");
    }
}
