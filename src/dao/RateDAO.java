package com.messfeedback.dao;

import com.messfeedback.models.Rate;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RateDAO {
    private final String RATE_FILE = "ratings.txt";

    // Save a single Rate object to file
    public void saveRate(Rate rate) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RATE_FILE, true))) {
            writer.write(rate.toFileString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read all rates from file
    public List<Rate> readAllRates() {
        List<Rate> rateList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RATE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Rate rate = Rate.fromFileString(line);
                if (rate != null) {
                    rateList.add(rate);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rateList;
    }

    // Calculate average rating for a meal
    public double getAverageRatingForMeal(int mealChoice) {
        List<Rate> allRates = readAllRates();
        int total = 0;
        int count = 0;
        for (Rate rate : allRates) {
            if (rate.getMealChoice() == mealChoice) {
                total += rate.getRating();
                count++;
            }
        }
        return (count == 0) ? 0 : (double) total / count;
    }
}


