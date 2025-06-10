// VoteDAO.java
package com.messfeedback.dao;

import com.messfeedback.models.Vote;
import com.messfeedback.models.User;
import com.messfeedback.util.FileUtil;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import java.util.*;

public class VoteDAO {
    private final String VOTE_FILE = "votes.txt";
    private final UserDAO userDAO = new UserDAO();

    // Save a new vote
    public boolean saveVote(Vote vote) {
        try {
            FileUtil.appendLine(VOTE_FILE, vote.toFileString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get all votes
    public List<Vote> getAllVotes() {
        List<String> lines = FileUtil.readAllLines(VOTE_FILE);
        List<Vote> votes = new ArrayList<>();
        for (String line : lines) {
            Vote vote = Vote.fromFileString(line);
            if (vote != null) {
                votes.add(vote);
            }
        }
        return votes;
    }


    // Generate a new vote ID
    public int generateVoteId() {
        List<Vote> votes = getAllVotes();
        int maxId = 0;
        for (Vote vote : votes) {
            if (vote.getVoteId() > maxId) {
                maxId = vote.getVoteId();
            }
        }
        return maxId + 1;
    }

    // Check if user has voted today for the same meal
    public boolean hasUserVotedToday(int userId, int mealChoice, String date, String mealType) {
        List<Vote> allVotes = getAllVotes();
        for (Vote v : allVotes) {
            if (v.getUserId() == userId && v.getMealChoice() == mealChoice
                    && v.getDate().equals(date) && v.getMealType().equalsIgnoreCase(mealType)) {
                return true;
            }
        }
        return false;
    }


    // Count how many votes each meal got
    public Map<Integer, Integer> getVoteCounts() {
        List<Vote> votes = getAllVotes();
        Map<Integer, Integer> countMap = new HashMap<>();

        for (Vote vote : votes) {
            countMap.put(vote.getMealChoice(), countMap.getOrDefault(vote.getMealChoice(), 0) + 1);
        }

        return countMap;
    }

    // Get meal name from meal choice number
    public String getMealName(int choice) {
        return switch (choice) {
            case 1 -> "Veg Biryani";
            case 2 -> "Rajma Chawal";
            case 3 -> "Paneer Butter Masala";
            case 4 -> "Chole Bhature";
            case 5 -> "Dal Chawal And Chokha";
            case 6 -> "Puri Sabji";
            case 7 -> "Khichdi";
            case 8 -> "Kheer Puri";
            case 9 -> "Idli Dosa Sambhar Chatni";
            default -> "Unknown";
        };
    }

    // Get the most popular meal
    public int getTopMeal() {
        Map<Integer, Integer> countMap = getVoteCounts();
        int topMeal = -1;
        int maxVotes = -1;

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                topMeal = entry.getKey();
            }
        }
        return topMeal;
    }

    // Get votes by specific date
    public List<Vote> getVotesByDate(String date) {
        List<Vote> votes = getAllVotes();
        List<Vote> result = new ArrayList<>();
        for (Vote vote : votes) {
            if (vote.getDate().equals(date)) {
                result.add(vote);
            }
        }
        return result;
    }

    // Get votes by user ID
    public List<Vote> getVotesByUserId(int userId) {
        List<Vote> votes = getAllVotes();
        List<Vote> result = new ArrayList<>();
        for (Vote vote : votes) {
            if (vote.getUserId() == userId) {
                result.add(vote);
            }
        }
        return result;
    }
    public Map<String, Map<String, Integer>> getDateWiseSummary() {
        Map<String, Map<String, Integer>> summaryMap = new TreeMap<>();

        for (Vote vote : getAllVotes()) {
            if (vote == null) continue;

            String date = vote.getDate();  // Already in yyyy-MM-dd
            int mealId = vote.getMealChoice();
            String mealName = getMealName(mealId); // Convert mealId to meal name

            if (date == null || mealName.equals("Unknown")) continue;

            // Insert into summary
            summaryMap.putIfAbsent(date, new HashMap<>());
            Map<String, Integer> mealMap = summaryMap.get(date);
            mealMap.put(mealName, mealMap.getOrDefault(mealName, 0) + 1);
        }

        return summaryMap;
    }




    public void exportSummaryToFile(String filename) {
        Map<Integer, Integer> voteCounts = getVoteCounts();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<Integer, Integer> entry : voteCounts.entrySet()) {
                String mealName = getMealName(entry.getKey());
                int count = entry.getValue();
                writer.write(mealName + ": " + count + " votes");
                writer.newLine();
            }
            System.out.println("Vote summary exported to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    private List<Vote> readVotesFromFile() {
        List<Vote> votes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(VOTE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int voteId = Integer.parseInt(parts[0]);
                    int userId = Integer.parseInt(parts[1]);
                    int mealChoice = Integer.parseInt(parts[2]);
                    String date = parts[3];
                    votes.add(new Vote(voteId, userId, mealChoice, date));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return votes;
    }

    // Show all votes with usernames (Admin feature)
    public void showAllVotesWithUsernames() {
        List<Vote> votes = getAllVotes();
        List<User> users = userDAO.getAllUsers();

        Map<Integer, String> userIdToName = new HashMap<>();
        for (User user : users) {
            userIdToName.put(user.getUserId(), user.getUsername());
        }

        if (votes.isEmpty()) {
            System.out.println("No votes found.");
            return;
        }

        System.out.println("All Votes:");
        for (Vote vote : votes) {
            String username = userIdToName.getOrDefault(vote.getUserId(), "Unknown");
            System.out.println("User: " + username + ", Meal Choice: " + getMealName(vote.getMealChoice()) + ", Date: " + vote.getDate());
        }
    }
}


