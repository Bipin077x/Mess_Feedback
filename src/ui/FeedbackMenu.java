// FeedbackMenu.java
package com.messfeedback.ui;

import java.io.Console;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.messfeedback.dao.UserDAO;
import com.messfeedback.dao.VoteDAO;
import com.messfeedback.dao.RateDAO;
import com.messfeedback.models.Rate;
import com.messfeedback.models.User;
import com.messfeedback.models.Vote;

public class FeedbackMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final UserDAO userDAO = new UserDAO();
    private final VoteDAO voteDAO = new VoteDAO();
    private final RateDAO rateDAO = new RateDAO();
    private User user; // Stores the currently logged-in user

    // Entry point for the menu system
    public void start() {
        System.out.println("Welcome to Hostel Mess Feedback System");
        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> register();
                case 2 -> loginUser();
                case 3 -> adminLogin();
                case 4 -> {
                    System.out.println("Exiting. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Handles user registration
    private void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        int userId = userDAO.generateUserId();
        User user = new User(userId, username, password, false);

        boolean success = userDAO.registerUser(user);
        if (success) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    // Handles user login with up to 3 attempts
    private void loginUser() {
        System.out.println("\n--- User Login ---");
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            // Use masked password input if supported
            Console console = System.console();
            String password;
            if (console != null) {
                char[] passwordChars = console.readPassword("Enter password: ");
                password = new String(passwordChars);
            } else {
                System.out.print("Enter password: ");
                password = scanner.nextLine();
            }

            user = userDAO.login(username, password);
            if (user != null) {
                System.out.println("Login successful. Welcome, " + username + "!");
                userMenu(); // Go to user menu
                return;
            } else {
                attempts++;
                System.out.println("Invalid credentials. Attempt " + attempts + "/" + MAX_ATTEMPTS);
            }
        }
        System.out.println("Too many failed attempts. Please try again later.");
    }

    // Displays menu for regular users
    private void userMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Vote for a meal");
            System.out.println("2. View current vote summary");
            System.out.println("3. View top meal");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> vote();
                case 2 -> showVoteSummary();
                case 3 -> showTopMeal();
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Handles voting and rating by users with meal type selection (Breakfast/Lunch/Dinner)
    // Handles voting and rating by users
    private void vote() {
        String today = java.time.LocalDate.now().toString(); // ✅ Inlined getCurrentDate()

        // Select meal type first
        System.out.println("Select meal type:");
        System.out.println("1. Breakfast");
        System.out.println("2. Lunch");
        System.out.println("3. Dinner");
        System.out.print("Enter choice: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine();

        String mealType;
        switch (typeChoice) {
            case 1 -> mealType = "Breakfast";
            case 2 -> mealType = "Lunch";
            case 3 -> mealType = "Dinner";
            default -> {
                System.out.println("Invalid meal type.");
                return; // ✅ Use return here safely since it's not inside a switch expression
            }
        }

        // Show meal options
        System.out.println("\nToday's Date: " + today + " (" + mealType + ")");
        System.out.println("Meal Options:");
        System.out.println("1. Veg Biryani");
        System.out.println("2. Rajma Chawal");
        System.out.println("3. Paneer Butter Masala");
        System.out.println("4. Chole Bhature");
        System.out.println("5. Dal Chawal And Chokha");
        System.out.println("6. Puri Sabji");
        System.out.println("7. Khichdi");
        System.out.println("8. Kheer Puri");
        System.out.println("9. Idli Dosa Sambhar Chatni");
        System.out.print("Enter your choice (1-9): ");
        int mealChoice = scanner.nextInt();
        scanner.nextLine();

        // Check if user has already voted for this meal today in this type
        if (voteDAO.hasUserVotedToday(user.getUserId(), mealChoice, today, mealType)) {
            System.out.println("You have already voted for this meal type today.✅");
            return;
        }

        // Record vote
        int voteId = voteDAO.generateVoteId();
        Vote vote = new Vote(voteId, user.getUserId(), mealChoice, today, mealType);

        if (voteDAO.saveVote(vote)) {
            System.out.println("Your vote has been recorded✅. Thank you!");

            // Ask for meal rating
            System.out.print("Rate this meal (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Rate rate = new Rate(user.getUserId(), mealChoice, rating, today);
            rateDAO.saveRate(rate);
        } else {
            System.out.println("Failed to record vote.");
        }
    }


    // Shows meal-wise vote summary with histogram
    private void showVoteSummary() {
        Map<Integer, Integer> voteCounts = voteDAO.getVoteCounts();
        System.out.println("\n--- Vote Summary ---");
        for (Map.Entry<Integer, Integer> entry : voteCounts.entrySet()) {
            int mealId = entry.getKey();
            int count = entry.getValue();
            String mealName = voteDAO.getMealName(mealId);

            String bar = "#".repeat(count);
            System.out.printf("%-25s: %2d votes  [%s]%n", mealName, count, bar);
        }
    }

    // Shows most voted meal of the day
    private void showTopMeal() {
        int topMeal = voteDAO.getTopMeal();
        if (topMeal != -1) {
            System.out.println("Most Voted Meal Today: " + voteDAO.getMealName(topMeal));
        } else {
            System.out.println("No votes yet.");
        }
    }

    // Admin login with fixed password
    private void adminLogin() {
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        if (password.equals("admin123")) {
            System.out.println("Admin login successful.");
            adminMenu();
        } else {
            System.out.println("Incorrect admin password.");
        }
    }

    // Displays menu for admin users
    private void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View all votes with usernames");
            System.out.println("2. Filter votes by date");
            System.out.println("3. Filter votes by username");
            System.out.println("4. Show top meal today");
            System.out.println("5. Export vote summary to file");
            System.out.println("6. Show date-wise voting summary");
            System.out.println("7. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> voteDAO.showAllVotesWithUsernames();
                case 2 -> filterVotesByDate();
                case 3 -> filterVotesByUsername();
                case 4 -> showTopMeal();
                case 5 -> voteDAO.exportSummaryToFile("summary.txt");
                case 6 -> showDateWiseSummary();
                case 7 -> {
                    System.out.println("Logging out from admin...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Filters votes by date input
    private void filterVotesByDate() {
        System.out.print("Enter date (yyyy-MM-dd): ");
        String date = scanner.nextLine();
        List<Vote> votes = voteDAO.getVotesByDate(date);
        if (votes.isEmpty()) {
            System.out.println("No votes on this date.");
            return;
        }
        System.out.println("Votes on " + date + ":");
        for (Vote v : votes) {
            String username = userDAO.getUsernameByUserId(v.getUserId());
            System.out.println("User: " + username + ", Meal: " + voteDAO.getMealName(v.getMealChoice()));
        }
    }

    // Filters votes by username input
    private void filterVotesByUsername() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        User u = userDAO.getUserByUsername(username);
        if (u == null) {
            System.out.println("User not found.");
            return;
        }
        List<Vote> votes = voteDAO.getVotesByUserId(u.getUserId());
        if (votes.isEmpty()) {
            System.out.println("No votes by this user.");
            return;
        }
        System.out.println("Votes by " + username + ":");
        for (Vote v : votes) {
            System.out.println("Meal: " + voteDAO.getMealName(v.getMealChoice()) + ", Date: " + v.getDate());
        }
    }

    // Shows summary grouped by each date
    public void showDateWiseSummary() {
        Map<String, Map<String, Integer>> summary = voteDAO.getDateWiseSummary();

        if (summary.isEmpty()) {
            System.out.println("No votes found.");
            return;
        }

        System.out.println("\n--- Date-wise Voting Summary ---");
        for (String date : summary.keySet()) {
            System.out.println("Date: " + date);
            Map<String, Integer> meals = summary.get(date);
            for (Map.Entry<String, Integer> entry : meals.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " votes");
            }
            System.out.println(); // Add spacing between days
        }
    }


    }
