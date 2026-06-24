package com.hrms.services;

import com.hrms.models.*;
import com.hrms.repositories.EmployeeRepository;
import com.hrms.repositories.LeaveRepository;
import com.hrms.utils.HRMSException;
import java.time.LocalDate;
import java.util.List;

public class LeaveService {
    private final LeaveRepository    repo     = new LeaveRepository();
    private final EmployeeRepository empRepo  = new EmployeeRepository();
    private final AttendanceService  attendSvc;

    public LeaveService(AttendanceService attendSvc) { this.attendSvc = attendSvc; }

    public void submitLeave(String employeeId, LeaveType type,
                            String startDate, String endDate, String reason) throws HRMSException {
        if (empRepo.findById(employeeId) == null)
            throw new HRMSException("Employee not found: " + employeeId);
        validateDates(startDate, endDate);
        repo.save(new LeaveRequest("LV" + System.currentTimeMillis(), employeeId, type,
                startDate, endDate, LeaveStatus.PENDING, reason));
    }

    public List<LeaveRequest> getAllLeaves()                   { return repo.findAll(); }
    public List<LeaveRequest> getPendingLeaves()               { return repo.findByStatus(LeaveStatus.PENDING); }
    public List<LeaveRequest> getLeavesByEmployee(String id)   { return repo.findByEmployee(id); }

    public LeaveRequest getLeaveById(String leaveId) throws HRMSException {
        LeaveRequest lr = repo.findById(leaveId);
        if (lr == null) throw new HRMSException("Leave request not found: " + leaveId);
        return lr;
    }

    public void approveLeave(String leaveId) throws HRMSException {
        LeaveRequest lr = getLeaveById(leaveId);
        if (lr.getStatus() != LeaveStatus.PENDING)
            throw new HRMSException("Leave is already " + lr.getStatus());
        repo.updateStatus(leaveId, LeaveStatus.APPROVED);
        attendSvc.markRangeAsOnLeave(lr.getEmployeeId(), lr.getStartDate(), lr.getEndDate());
    }

    public void rejectLeave(String leaveId) throws HRMSException {
        LeaveRequest lr = getLeaveById(leaveId);
        if (lr.getStatus() != LeaveStatus.PENDING)
            throw new HRMSException("Leave is already " + lr.getStatus());
        repo.updateStatus(leaveId, LeaveStatus.REJECTED);
    }

    private void validateDates(String s, String e) throws HRMSException {
        try {
            LocalDate start = LocalDate.parse(s);
            LocalDate end   = LocalDate.parse(e);
            if (end.isBefore(start)) throw new HRMSException("End date cannot be before start date.");
        } catch (HRMSException ex) { throw ex; }
        catch (Exception ex) { throw new HRMSException("Invalid date format. Use YYYY-MM-DD."); }
    }
}
