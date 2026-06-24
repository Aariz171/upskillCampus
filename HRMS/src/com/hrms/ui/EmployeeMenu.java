package com.hrms.ui;

import com.hrms.models.Employee;
import com.hrms.services.EmployeeService;
import com.hrms.utils.ConsoleUtils;
import com.hrms.utils.HRMSException;
import java.util.List;

public class EmployeeMenu {
    private final EmployeeService service;

    public EmployeeMenu(EmployeeService service) { this.service = service; }

    public void show() {
        while (true) {
            ConsoleUtils.printHeader("Employee Management");
            System.out.println("  1. Add New Employee");
            System.out.println("  2. View All Employees");
            System.out.println("  3. View Employee by ID");
            System.out.println("  4. Update Employee");
            System.out.println("  5. Delete Employee");
            System.out.println("  0. Back to Main Menu");
            int choice = ConsoleUtils.getMenuChoice(0, 5);
            switch (choice) {
                case 1: addEmployee();      break;
                case 2: viewAllEmployees(); break;
                case 3: viewById();         break;
                case 4: updateEmployee();   break;
                case 5: deleteEmployee();   break;
                case 0: return;
            }
        }
    }

    private void addEmployee() {
        ConsoleUtils.printHeader("Add New Employee");
        try {
            String id    = ConsoleUtils.getInput("Employee ID (e.g. EMP001)");
            String name  = ConsoleUtils.getInput("Full Name");
            String dept  = ConsoleUtils.getInput("Department");
            String desig = ConsoleUtils.getInput("Designation");
            String email = ConsoleUtils.getInput("Email");
            String phone = ConsoleUtils.getInput("Phone");
            String date  = ConsoleUtils.getInput("Join Date (YYYY-MM-DD)");
            service.addEmployee(new Employee(id, name, desig, dept, email, phone, date));
            ConsoleUtils.printSuccess("Employee '" + name + "' added successfully!");
        } catch (HRMSException ex) {
            ConsoleUtils.printError(ex.getMessage());
        }
        ConsoleUtils.pressEnterToContinue();
    }

    private void viewAllEmployees() {
        ConsoleUtils.printHeader("All Employees");
        List<Employee> list = service.getAllEmployees();
        if (list.isEmpty()) {
            ConsoleUtils.printInfo("No employees found.");
        } else {
            printTableHeader();
            for (Employee e : list) System.out.println(e);
            ConsoleUtils.printDivider();
            ConsoleUtils.printInfo("Total: " + list.size() + " employee(s)");
        }
        ConsoleUtils.pressEnterToContinue();
    }

    private void viewById() {
        ConsoleUtils.printHeader("View Employee by ID");
        try {
            service.getEmployeeById(ConsoleUtils.getInput("Employee ID")).printFormatted();
        } catch (HRMSException ex) { ConsoleUtils.printError(ex.getMessage()); }
        ConsoleUtils.pressEnterToContinue();
    }

    private void updateEmployee() {
        ConsoleUtils.printHeader("Update Employee");
        try {
            String id = ConsoleUtils.getInput("Employee ID to update");
            Employee existing = service.getEmployeeById(id);
            System.out.println("\n  Current record:");
            existing.printFormatted();
            System.out.println("  (Press ENTER to keep current value)\n");
            String name  = prompt("Name",        existing.getName());
            String dept  = prompt("Department",  existing.getDepartment());
            String desig = prompt("Designation", existing.getDesignation());
            String email = prompt("Email",       existing.getEmail());
            String phone = prompt("Phone",       existing.getPhone());
            String date  = prompt("Join Date",   existing.getJoinDate());
            Employee updated = new Employee(id,
                    empty(name)  ? existing.getName()        : name,
                    empty(desig) ? existing.getDesignation() : desig,
                    empty(dept)  ? existing.getDepartment()  : dept,
                    empty(email) ? existing.getEmail()       : email,
                    empty(phone) ? existing.getPhone()       : phone,
                    empty(date)  ? existing.getJoinDate()    : date);
            service.updateEmployee(updated);
            ConsoleUtils.printSuccess("Employee updated successfully!");
        } catch (HRMSException ex) { ConsoleUtils.printError(ex.getMessage()); }
        ConsoleUtils.pressEnterToContinue();
    }

    private void deleteEmployee() {
        ConsoleUtils.printHeader("Delete Employee");
        try {
            String id = ConsoleUtils.getInput("Employee ID to delete");
            Employee e = service.getEmployeeById(id);
            e.printFormatted();
            String confirm = ConsoleUtils.getInput("Confirm deletion? (yes/no)");
            if (confirm.equalsIgnoreCase("yes")) {
                service.deleteEmployee(id);
                ConsoleUtils.printSuccess("Employee '" + e.getName() + "' deleted.");
            } else ConsoleUtils.printInfo("Deletion cancelled.");
        } catch (HRMSException ex) { ConsoleUtils.printError(ex.getMessage()); }
        ConsoleUtils.pressEnterToContinue();
    }

    private void printTableHeader() {
        ConsoleUtils.printDivider();
        System.out.printf("| %-8s | %-20s | %-15s | %-18s | %-12s |%n",
                "ID","Name","Department","Designation","Join Date");
        ConsoleUtils.printDivider();
    }

    private String prompt(String label, String current) {
        System.out.print("  " + label + " [" + current + "]: ");
        return ConsoleUtils.getScanner().nextLine().trim();
    }

    private boolean empty(String s) { return s == null || s.isEmpty(); }
}
