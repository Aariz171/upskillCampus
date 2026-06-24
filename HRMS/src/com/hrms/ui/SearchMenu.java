package com.hrms.ui;

import com.hrms.models.Employee;
import com.hrms.services.EmployeeService;
import com.hrms.utils.ConsoleUtils;
import java.util.List;

public class SearchMenu {
    private final EmployeeService service;

    public SearchMenu(EmployeeService service) { this.service = service; }

    public void show() {
        while (true) {
            ConsoleUtils.printHeader("Employee Search");
            System.out.println("  1. Search by Name");
            System.out.println("  2. Search by Employee ID");
            System.out.println("  3. Search by Department");
            System.out.println("  0. Back");
            int choice = ConsoleUtils.getMenuChoice(0, 3);
            switch (choice) {
                case 1: searchByName();       break;
                case 2: searchById();         break;
                case 3: searchByDepartment(); break;
                case 0: return;
            }
        }
    }

    private void searchByName() {
        String kw = ConsoleUtils.getInput("Enter name keyword");
        printResults(service.searchByName(kw), "name containing '" + kw + "'");
    }

    private void searchById() {
        String id = ConsoleUtils.getInput("Enter Employee ID");
        Employee e = service.searchById(id);
        if (e != null) { ConsoleUtils.printInfo("Employee found:"); e.printFormatted(); }
        else ConsoleUtils.printError("No employee found with ID: " + id);
        ConsoleUtils.pressEnterToContinue();
    }

    private void searchByDepartment() {
        String kw = ConsoleUtils.getInput("Enter department keyword");
        printResults(service.searchByDepartment(kw), "department containing '" + kw + "'");
    }

    private void printResults(List<Employee> results, String context) {
        System.out.println();
        if (results.isEmpty()) { ConsoleUtils.printInfo("No results found for " + context); }
        else {
            ConsoleUtils.printInfo("Found " + results.size() + " result(s) for " + context + ":");
            ConsoleUtils.printDivider();
            System.out.printf("| %-8s | %-20s | %-15s | %-18s | %-12s |%n",
                    "ID","Name","Department","Designation","Join Date");
            ConsoleUtils.printDivider();
            for (Employee e : results) System.out.println(e);
            ConsoleUtils.printDivider();
        }
        ConsoleUtils.pressEnterToContinue();
    }
}
