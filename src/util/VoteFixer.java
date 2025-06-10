package com.messfeedback.util;

import java.io.*;
import java.util.*;

public class VoteFixer {

    public static void fixVotes(String filepath) {
        List<String> fixedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            int fixedCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    // Old format: voteId,userId,mealChoice,date
                    // Append "Unknown" as mealType
                    line += ",Unknown";
                    fixedCount++;
                }
                fixedLines.add(line);
            }
            System.out.println("✔ Fixed " + fixedCount + " vote lines.");
        } catch (IOException e) {
            System.out.println("❌ Error reading vote file: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (String fixedLine : fixedLines) {
                writer.write(fixedLine);
                writer.newLine();
            }
            System.out.println("✔ Updated file written successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error writing fixed votes: " + e.getMessage());
        }
    }
}

