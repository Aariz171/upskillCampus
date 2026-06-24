package com.hrms.models;

public class Employee {
    private String employeeId;
    private String name;
    private String designation;
    private String department;
    private String email;
    private String phone;
    private String joinDate;

    public Employee() {}

    public Employee(String employeeId, String name, String designation,
                    String department, String email, String phone, String joinDate) {
        this.employeeId  = employeeId;
        this.name        = name;
        this.designation = designation;
        this.department  = department;
        this.email       = email;
        this.phone       = phone;
        this.joinDate    = joinDate;
    }

    public String toFileString() {
        return String.join("|", employeeId, name, designation, department, email, phone, joinDate);
    }

    public static Employee fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 7) return null;
        return new Employee(p[0].trim(), p[1].trim(), p[2].trim(),
                p[3].trim(), p[4].trim(), p[5].trim(), p[6].trim());
    }

    public String getEmployeeId()  { return employeeId; }
    public String getName()        { return name; }
    public String getDesignation() { return designation; }
    public String getDepartment()  { return department; }
    public String getEmail()       { return email; }
    public String getPhone()       { return phone; }
    public String getJoinDate()    { return joinDate; }

    public void setEmployeeId(String employeeId)   { this.employeeId = employeeId; }
    public void setName(String name)               { this.name = name; }
    public void setDesignation(String designation) { this.designation = designation; }
    public void setDepartment(String department)   { this.department = department; }
    public void setEmail(String email)             { this.email = email; }
    public void setPhone(String phone)             { this.phone = phone; }
    public void setJoinDate(String joinDate)       { this.joinDate = joinDate; }

    public void printFormatted() {
        System.out.println("+-------------------------------------------------+");
        System.out.printf( "|  Employee ID   : %-30s|%n", employeeId);
        System.out.printf( "|  Name          : %-30s|%n", name);
        System.out.printf( "|  Designation   : %-30s|%n", designation);
        System.out.printf( "|  Department    : %-30s|%n", department);
        System.out.printf( "|  Email         : %-30s|%n", email);
        System.out.printf( "|  Phone         : %-30s|%n", phone);
        System.out.printf( "|  Join Date     : %-30s|%n", joinDate);
        System.out.println("+-------------------------------------------------+");
    }

    @Override
    public String toString() {
        return String.format("| %-8s | %-20s | %-15s | %-18s | %-12s |",
                employeeId, name, department, designation, joinDate);
    }
}
