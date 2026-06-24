package com.hrms.services;

import com.hrms.models.Employee;
import com.hrms.repositories.EmployeeRepository;
import com.hrms.utils.HRMSException;
import java.util.*;

public class EmployeeService {
    private final EmployeeRepository repo = new EmployeeRepository();

    public void addEmployee(Employee e) throws HRMSException {
        if (e.getEmployeeId() == null || e.getEmployeeId().isEmpty())
            throw new HRMSException("Employee ID cannot be empty.");
        if (e.getName() == null || e.getName().isEmpty())
            throw new HRMSException("Name cannot be empty.");
        if (e.getDepartment() == null || e.getDepartment().isEmpty())
            throw new HRMSException("Department cannot be empty.");
        repo.save(e);
    }

    public List<Employee> getAllEmployees()              { return repo.findAll(); }
    public void updateEmployee(Employee e) throws HRMSException { repo.update(e); }
    public void deleteEmployee(String id) throws HRMSException  { repo.delete(id); }

    public Employee getEmployeeById(String id) throws HRMSException {
        Employee e = repo.findById(id);
        if (e == null) throw new HRMSException("No employee found with ID: " + id);
        return e;
    }

    public List<Employee> searchByName(String keyword) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : repo.findAll())
            if (e.getName().toLowerCase().contains(keyword.toLowerCase())) result.add(e);
        return result;
    }

    public List<Employee> searchByDepartment(String keyword) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : repo.findAll())
            if (e.getDepartment().toLowerCase().contains(keyword.toLowerCase())) result.add(e);
        return result;
    }

    public Employee searchById(String id) { return repo.findById(id); }
}
