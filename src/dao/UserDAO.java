// UserDAO.java
package com.messfeedback.dao;

import com.messfeedback.models.User;
import com.messfeedback.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final String USER_FILE = "users.txt";

    // Register a new user
    public boolean registerUser(User user) {
        try {
            FileUtil.appendLine(USER_FILE, user.toFileString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get all users
    public List<User> getAllUsers() {
        List<String> lines = FileUtil.readAllLines(USER_FILE);
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(User.fromFileString(line));
        }
        return users;
    }

    // Login: get user by username and password
    public User loginUser(String username, String password) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Get user by username
    public User getUserByUsername(String username) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Get username from user ID
    public String getUsernameByUserId(int userId) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user.getUsername();
            }
        }
        return "Unknown";
    }

    // Generate new user ID
    public int generateUserId() {
        List<User> users = getAllUsers();
        int maxId = 0;
        for (User user : users) {
            if (user.getUserId() > maxId) {
                maxId = user.getUserId();
            }
        }
        return maxId + 1;
    }
    public User login(String username, String password) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

}
