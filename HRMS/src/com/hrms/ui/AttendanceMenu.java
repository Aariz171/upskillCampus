package com.hrms.ui;

import com.hrms.models.Attendance;
import com.hrms.models.AttendanceStatus;
import com.hrms.services.AttendanceService;
import com.hrms.utils.ConsoleUtils;
import com.hrms.utils.HRMSException;
import java.util.List;

public class AttendanceMenu {
    private final AttendanceService service;

    public AttendanceMenu(AttendanceService service) { this.service = service; }

    public void show() {
        while (true) {
            ConsoleUtils.printHeader("Attendance Tracking");
            System.out.println("  1. Mark Attendance");
            System.out.println("  2. View Attendance by Date");
            System.out.println("  3. View Attendance by Employee");
            System.out.println("  0. Back");
            int choice = ConsoleUtils.getMenuChoice(0, 3);
            switch (choice) {
                case 1: markAttendance(); break;
                case 2: viewByDate();     break;
                case 3: viewByEmployee(); break;
                case 0: return;
            }
        }
    }

    private void markAttendance() {
        ConsoleUtils.printHeader("Mark Attendance");
        try {
            String id   = ConsoleUtils.getInput("Employee ID");
            String date = ConsoleUtils.getInput("Date (YYYY-MM-DD)");
            System.out.println("  Status: 1. PRESENT   2. ABSENT   3. ON_LEAVE");
            int sc = ConsoleUtils.getMenuChoice(1, 3);
            AttendanceStatus status = sc == 1 ? AttendanceStatus.PRESENT
                    : sc == 2 ? AttendanceStatus.ABSENT : AttendanceStatus.ON_LEAVE;
            service.markAttendance(id, date, status);
            ConsoleUtils.printSuccess("Attendance marked: " + id + " -> " + status + " on " + date);
        } catch (HRMSException ex) { ConsoleUtils.printError(ex.getMessage()); }
        ConsoleUtils.pressEnterToContinue();
    }

    private void viewByDate() {
        ConsoleUtils.printHeader("Attendance by Date");
        String date = ConsoleUtils.getInput("Enter Date (YYYY-MM-DD)");
        printTable(service.getAttendanceByDate(date), "Date: " + date);
        ConsoleUtils.pressEnterToContinue();
    }

    private void viewByEmployee() {
        ConsoleUtils.printHeader("Attendance by Employee");
        String id = ConsoleUtils.getInput("Enter Employee ID");
        printTable(service.getAttendanceByEmployee(id), "Employee: " + id);
        ConsoleUtils.pressEnterToContinue();
    }

    private void printTable(List<Attendance> list, String context) {
        if (list.isEmpty()) { ConsoleUtils.printInfo("No records found for " + context); return; }
        ConsoleUtils.printDivider();
        System.out.printf("| %-8s | %-12s | %-10s |%n", "Emp ID", "Date", "Status");
        ConsoleUtils.printDivider();
        for (Attendance a : list) System.out.println(a);
        ConsoleUtils.printDivider();
        ConsoleUtils.printInfo("Total records: " + list.size());
    }
}
