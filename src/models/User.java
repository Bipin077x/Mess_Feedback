// User.java
package com.messfeedback.models;

public class User {
    private int userId;
    private String username;
    private String password;
    private boolean isAdmin;

    public User(int userId, String username, String password, boolean isAdmin) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return isAdmin; }

    // Convert to file string format
    public String toFileString() {
        return userId + "," + username + "," + password + "," + isAdmin;
    }

    // Create User object from a line in the file
    public static User fromFileString(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String uname = parts[1];
        String pass = parts[2];
        boolean admin = Boolean.parseBoolean(parts[3]);
        return new User(id, uname, pass, admin);
    }

    @Override
    public String toString() {
        return "User ID: " + userId + ", Username: " + username + ", Admin: " + isAdmin;
    }
}

