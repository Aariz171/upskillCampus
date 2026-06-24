package com.hrms.utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileHandler {
    public static final String DATA_DIR        = "data/";
    public static final String REPORTS_DIR     = "reports/";
    public static final String EMPLOYEES_FILE  = DATA_DIR + "employees.txt";
    public static final String ADMINS_FILE     = DATA_DIR + "admins.txt";
    public static final String ATTENDANCE_FILE = DATA_DIR + "attendance.txt";
    public static final String LEAVES_FILE     = DATA_DIR + "leaves.txt";

    public static void initializeDataFiles() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            Files.createDirectories(Paths.get(REPORTS_DIR));
            File adminFile = new File(ADMINS_FILE);
            if (!adminFile.exists() || adminFile.length() == 0) {
                adminFile.createNewFile();
                String defaultHash = HashUtils.sha256("admin123");
                writeLines(ADMINS_FILE, Collections.singletonList("admin|" + defaultHash));
                System.out.println("[INFO] Default admin created -> username: admin | password: admin123");
            }
            createIfNotExists(EMPLOYEES_FILE);
            createIfNotExists(ATTENDANCE_FILE);
            createIfNotExists(LEAVES_FILE);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to initialize data files: " + e.getMessage());
        }
    }

    private static void createIfNotExists(String path) throws IOException {
        File f = new File(path);
        if (!f.exists()) f.createNewFile();
    }

    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null)
                if (!line.trim().isEmpty()) lines.add(line.trim());
        } catch (IOException e) {
            System.out.println("[ERROR] Cannot read file: " + filePath);
        }
        return lines;
    }

    public static void writeLines(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : lines) { writer.write(line); writer.newLine(); }
        } catch (IOException e) {
            System.out.println("[ERROR] Cannot write to file: " + filePath);
        }
    }

    public static void appendLine(String filePath, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line); writer.newLine();
        } catch (IOException e) {
            System.out.println("[ERROR] Cannot append to file: " + filePath);
        }
    }

    public static void writeReport(String filename, String content) {
        String path = REPORTS_DIR + filename;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, false))) {
            writer.write(content);
            System.out.println("[INFO] Report saved to: " + path);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to write report: " + e.getMessage());
        }
    }
}
