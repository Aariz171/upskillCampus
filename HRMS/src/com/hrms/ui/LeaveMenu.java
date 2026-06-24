package com.hrms.ui;

import com.hrms.models.LeaveRequest;
import com.hrms.models.LeaveType;
import com.hrms.services.LeaveService;
import com.hrms.utils.ConsoleUtils;
import com.hrms.utils.HRMSException;
import java.util.List;

public class LeaveMenu {
    private final LeaveService service;

    public LeaveMenu(LeaveService service) { this.service = service; }

    public void show() {
        while (true) {
            ConsoleUtils.printHeader("Leave Management");
            System.out.println("  1. Submit Leave Request");
            System.out.println("  2. View All Leave Requests");
            System.out.println("  3. View Pending Leaves");
            System.out.println("  4. View Leaves by Employee");
            System.out.println("  5. Approve Leave");
            System.out.println("  6. Reject Leave");
            System.out.println("  0. Back");
            int choice = ConsoleUtils.getMenuChoice(0, 6);
            switch (choice) {
                case 1: submitLeave();       break;
                case 2: viewAllLeaves();     break;
                case 3: viewPendingLeaves(); break;
                case 4: viewByEmployee();    break;
                case 5: approveLeave();      break;
                case 6: rejectLeave();       break;
                case 0: return;
            }
        }
    }

    private void submitLeave() {
        ConsoleUtils.printHeader("Submit Leave Request");
        try {
            String empId = ConsoleUtils.getInput("Employee ID");
            System.out.println("  Leave Type: 1.SICK  2.CASUAL  3.ANNUAL  4.MATERNITY  5.UNPAID");
            int tc = ConsoleUtils.getMenuChoice(1, 5);
            LeaveType type = tc == 1 ? LeaveType.SICK : tc == 2 ? LeaveType.CASUAL
                    : tc == 3 ? LeaveType.ANNUAL : tc == 4 ? LeaveType.MATERNITY : LeaveType.UNPAID;
            String start  = ConsoleUtils.getInput("Start Date (YYYY-MM-DD)");
            String end    = ConsoleUtils.getInput("End Date   (YYYY-MM-DD)");
            String reason = ConsoleUtils.getInput("Reason");
            service.submitLeave(empId, type, start, end, reason);
            ConsoleUtils.printSuccess("Leave request submitted successfully!");
        } catch (HRMSException ex) { ConsoleUtils.printError(ex.getMessage()); }
        ConsoleUtils.pressEnterToContinue();
    }

    private void viewAllLeaves()     { ConsoleUtils.printHeader("All Leave Requests");    printTable(service.getAllLeaves());      ConsoleUtils.pressEnterToContinue(); }
    private void viewPendingLeaves() { ConsoleUtils.printHeader("Pending Leave Requests"); printTable(service.getPendingLeaves()); ConsoleUtils.pressEnterToContinue(); }

    private void viewByEmployee() {
        ConsoleUtils.printHeader("Leaves by Employee");
        printTable(service.getLeavesByEmployee(ConsoleUtils.getInput("Employee ID")));
        ConsoleUtils.pressEnterToContinue();
    }

    private void approveLeave() {
        ConsoleUtils.printHeader("Approve Leave");
        try {
            String id = ConsoleUtils.getInput("Enter Leave ID");
            service.getLeaveById(id).printFormatted();
            if (ConsoleUtils.getInput("Approve? (yes/no)").equalsIgnoreCase("yes")) {
                service.approveLeave(id);
                ConsoleUtils.printSuccess("Leave approved. Attendance auto-updated to ON_LEAVE.");
            } else ConsoleUtils.printInfo("Action cancelled.");
        } catch (HRMSException ex) { ConsoleUtils.printError(ex.getMessage()); }
        ConsoleUtils.pressEnterToContinue();
    }

    private void rejectLeave() {
        ConsoleUtils.printHeader("Reject Leave");
        try {
            String id = ConsoleUtils.getInput("Enter Leave ID");
            service.getLeaveById(id).printFormatted();
            if (ConsoleUtils.getInput("Reject? (yes/no)").equalsIgnoreCase("yes")) {
                service.rejectLeave(id);
                ConsoleUtils.printSuccess("Leave rejected.");
            } else ConsoleUtils.printInfo("Action cancelled.");
        } catch (HRMSException ex) { ConsoleUtils.printError(ex.getMessage()); }
        ConsoleUtils.pressEnterToContinue();
    }

    private void printTable(List<LeaveRequest> list) {
        if (list.isEmpty()) { ConsoleUtils.printInfo("No leave records found."); return; }
        ConsoleUtils.printDivider();
        System.out.printf("| %-18s | %-8s | %-10s | %-12s | %-12s | %-10s |%n",
                "Leave ID","Emp ID","Type","Start","End","Status");
        ConsoleUtils.printDivider();
        for (LeaveRequest lr : list) System.out.println(lr);
        ConsoleUtils.printDivider();
        ConsoleUtils.printInfo("Total: " + list.size());
    }
}
