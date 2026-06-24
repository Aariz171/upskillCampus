package com.hrms.ui;

import com.hrms.services.ReportService;
import com.hrms.utils.ConsoleUtils;
import com.hrms.utils.HRMSException;
import com.hrms.utils.ValidationUtils;

public class ReportMenu {
    private final ReportService service = new ReportService();

    public void show() {
        while (true) {
            ConsoleUtils.printHeader("Reports");
            System.out.println("  1. Employee Report");
            System.out.println("  2. Attendance Report (by date range)");
            System.out.println("  3. Leave Summary Report");
            System.out.println("  0. Back");
            int choice = ConsoleUtils.getMenuChoice(0, 3);
            switch (choice) {
                case 1: System.out.println(service.generateEmployeeReport()); ConsoleUtils.pressEnterToContinue(); break;
                case 2: attendanceReport(); break;
                case 3: System.out.println(service.generateLeaveReport());    ConsoleUtils.pressEnterToContinue(); break;
                case 0: return;
            }
        }
    }

    private void attendanceReport() {
        ConsoleUtils.printHeader("Attendance Report");
        try {
            String from = ConsoleUtils.getInput("From Date (YYYY-MM-DD)");
            String to   = ConsoleUtils.getInput("To Date   (YYYY-MM-DD)");
            ValidationUtils.validateDateRange(from, to);
            System.out.println(service.generateAttendanceReport(from, to));
        } catch (HRMSException ex) { ConsoleUtils.printError(ex.getMessage()); }
        ConsoleUtils.pressEnterToContinue();
    }
}
