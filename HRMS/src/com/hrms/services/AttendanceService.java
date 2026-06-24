package com.hrms.services;

import com.hrms.models.Attendance;
import com.hrms.models.AttendanceStatus;
import com.hrms.repositories.AttendanceRepository;
import com.hrms.repositories.EmployeeRepository;
import com.hrms.utils.HRMSException;
import java.time.LocalDate;
import java.util.List;

public class AttendanceService {
    private final AttendanceRepository repo    = new AttendanceRepository();
    private final EmployeeRepository   empRepo = new EmployeeRepository();

    public void markAttendance(String employeeId, String date, AttendanceStatus status)
            throws HRMSException {
        if (empRepo.findById(employeeId) == null)
            throw new HRMSException("Employee not found: " + employeeId);
        if (!isValidDate(date))
            throw new HRMSException("Invalid date format. Use YYYY-MM-DD.");
        repo.saveOrUpdate(new Attendance(employeeId, date, status));
    }

    public List<Attendance> getAttendanceByDate(String date)        { return repo.findByDate(date); }
    public List<Attendance> getAttendanceByEmployee(String empId)   { return repo.findByEmployee(empId); }
    public List<Attendance> getAllAttendance()                       { return repo.findAll(); }

    public void markRangeAsOnLeave(String employeeId, String startDate, String endDate)
            throws HRMSException {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end   = LocalDate.parse(endDate);
        LocalDate cur   = start;
        while (!cur.isAfter(end)) {
            repo.saveOrUpdate(new Attendance(employeeId, cur.toString(), AttendanceStatus.ON_LEAVE));
            cur = cur.plusDays(1);
        }
    }

    private boolean isValidDate(String date) {
        try { LocalDate.parse(date); return true; } catch (Exception e) { return false; }
    }
}
