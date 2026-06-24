package com.hrms.ui;

import com.hrms.auth.AuthService;
import com.hrms.services.AttendanceService;
import com.hrms.services.EmployeeService;
import com.hrms.services.LeaveService;
import com.hrms.utils.ConsoleUtils;

public class MainMenu {
    private final AuthService       authService;
    private final EmployeeService   employeeService   = new EmployeeService();
    private final AttendanceService attendanceService = new AttendanceService();
    private final LeaveService      leaveService      = new LeaveService(attendanceService);

    public MainMenu(AuthService authService) { this.authService = authService; }

    public void start() { showLoginScreen(); }

    private void showLoginScreen() {
        while (true) {
            ConsoleUtils.printHeader("Login to HRMS");
            String username = ConsoleUtils.getInput("Username");
            String password = ConsoleUtils.getInput("Password");
            if (authService.login(username, password)) {
                ConsoleUtils.printSuccess("Welcome, " + authService.getLoggedInUsername() + "!");
                ConsoleUtils.pressEnterToContinue();
                showMainMenu();
            } else {
                ConsoleUtils.printError("Invalid username or password.");
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private void showMainMenu() {
        while (authService.isAuthenticated()) {
            ConsoleUtils.printHeader("Main Menu  [User: " + authService.getLoggedInUsername() + "]");
            System.out.println("  1. Employee Management");
            System.out.println("  2. Attendance Tracking");
            System.out.println("  3. Leave Management");
            System.out.println("  4. Employee Search");
            System.out.println("  5. Reports");
            System.out.println("  0. Logout");
            int choice = ConsoleUtils.getMenuChoice(0, 5);
            switch (choice) {
                case 1: new EmployeeMenu(employeeService).show();      break;
                case 2: new AttendanceMenu(attendanceService).show();  break;
                case 3: new LeaveMenu(leaveService).show();            break;
                case 4: new SearchMenu(employeeService).show();        break;
                case 5: new ReportMenu().show();                       break;
                case 0:
                    authService.logout();
                    ConsoleUtils.printSuccess("Logged out. Goodbye!");
                    ConsoleUtils.pressEnterToContinue();
                    return;
            }
        }
    }
}
