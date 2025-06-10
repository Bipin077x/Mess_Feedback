// FileUtil.java
package com.messfeedback.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    // Append a line to a file
    public static void appendLine(String fileName, String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(line);
            writer.newLine();
        }
    }

    // Read all lines from a file
    public static List<String> readAllLines(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return lines; // return empty list if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    // Overwrite file with a list of lines (optional)
    public static void writeAllLines(String fileName, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}


