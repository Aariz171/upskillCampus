package com.hrms.utils;

import java.util.Scanner;

public class ConsoleUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static void printBanner() {
        System.out.println("+----------------------------------------------------------+");
        System.out.println("|      HUMAN RESOURCE MANAGEMENT SYSTEM (HRMS)             |");
        System.out.println("|                 Console Edition v1.0                      |");
        System.out.println("+----------------------------------------------------------+");
        System.out.println();
    }

    public static void printHeader(String title) {
        System.out.println();
        System.out.println("+----------------------------------------------------------+");
        System.out.printf( "|   %-55s|%n", title.toUpperCase());
        System.out.println("+----------------------------------------------------------+");
    }

    public static void printDivider() {
        System.out.println("----------------------------------------------------------");
    }

    public static String getInput(String prompt) {
        System.out.print("  " + prompt + ": ");
        return scanner.nextLine().trim();
    }

    public static int getMenuChoice(int min, int max) {
        while (true) {
            try {
                System.out.print("\n  Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= min && choice <= max) return choice;
                printError("Enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                printError("Invalid input. Please enter a number.");
            }
        }
    }

    public static void pressEnterToContinue() {
        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }

    public static void printSuccess(String message) {
        System.out.println("\n  [SUCCESS] " + message);
    }

    public static void printError(String message) {
        System.out.println("\n  [ERROR] " + message);
    }

    public static void printInfo(String message) {
        System.out.println("  [INFO] " + message);
    }

    public static Scanner getScanner() { return scanner; }
}
