package com.hrms.repositories;

import com.hrms.models.Employee;
import com.hrms.utils.FileHandler;
import com.hrms.utils.HRMSException;
import java.util.*;

public class EmployeeRepository {

    public void save(Employee employee) throws HRMSException {
        if (findById(employee.getEmployeeId()) != null)
            throw new HRMSException("Employee ID '" + employee.getEmployeeId() + "' already exists.");
        FileHandler.appendLine(FileHandler.EMPLOYEES_FILE, employee.toFileString());
    }

    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        for (String line : FileHandler.readLines(FileHandler.EMPLOYEES_FILE)) {
            Employee e = Employee.fromFileString(line);
            if (e != null) list.add(e);
        }
        return list;
    }

    public Employee findById(String id) {
        for (Employee e : findAll())
            if (e.getEmployeeId().equalsIgnoreCase(id)) return e;
        return null;
    }

    public void update(Employee updated) throws HRMSException {
        List<Employee> all = findAll();
        boolean found = false;
        List<String> newLines = new ArrayList<>();
        for (Employee e : all) {
            if (e.getEmployeeId().equalsIgnoreCase(updated.getEmployeeId())) {
                newLines.add(updated.toFileString()); found = true;
            } else newLines.add(e.toFileString());
        }
        if (!found) throw new HRMSException("Employee not found: " + updated.getEmployeeId());
        FileHandler.writeLines(FileHandler.EMPLOYEES_FILE, newLines);
    }

    public void delete(String id) throws HRMSException {
        List<Employee> all = findAll();
        boolean found = false;
        List<String> newLines = new ArrayList<>();
        for (Employee e : all) {
            if (e.getEmployeeId().equalsIgnoreCase(id)) found = true;
            else newLines.add(e.toFileString());
        }
        if (!found) throw new HRMSException("Employee not found: " + id);
        FileHandler.writeLines(FileHandler.EMPLOYEES_FILE, newLines);
    }
}
