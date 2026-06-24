package com.hrms.repositories;

import com.hrms.models.LeaveRequest;
import com.hrms.models.LeaveStatus;
import com.hrms.utils.FileHandler;
import com.hrms.utils.HRMSException;
import java.util.*;

public class LeaveRepository {

    public void save(LeaveRequest lr) {
        FileHandler.appendLine(FileHandler.LEAVES_FILE, lr.toFileString());
    }

    public List<LeaveRequest> findAll() {
        List<LeaveRequest> list = new ArrayList<>();
        for (String line : FileHandler.readLines(FileHandler.LEAVES_FILE)) {
            LeaveRequest lr = LeaveRequest.fromFileString(line);
            if (lr != null) list.add(lr);
        }
        return list;
    }

    public LeaveRequest findById(String leaveId) {
        for (LeaveRequest lr : findAll())
            if (lr.getLeaveId().equalsIgnoreCase(leaveId)) return lr;
        return null;
    }

    public List<LeaveRequest> findByEmployee(String employeeId) {
        List<LeaveRequest> result = new ArrayList<>();
        for (LeaveRequest lr : findAll())
            if (lr.getEmployeeId().equalsIgnoreCase(employeeId)) result.add(lr);
        return result;
    }

    public List<LeaveRequest> findByStatus(LeaveStatus status) {
        List<LeaveRequest> result = new ArrayList<>();
        for (LeaveRequest lr : findAll()) if (lr.getStatus() == status) result.add(lr);
        return result;
    }

    public void updateStatus(String leaveId, LeaveStatus newStatus) throws HRMSException {
        List<LeaveRequest> all = findAll();
        boolean found = false;
        List<String> lines = new ArrayList<>();
        for (LeaveRequest lr : all) {
            if (lr.getLeaveId().equalsIgnoreCase(leaveId)) {
                lr.setStatus(newStatus); lines.add(lr.toFileString()); found = true;
            } else lines.add(lr.toFileString());
        }
        if (!found) throw new HRMSException("Leave request not found: " + leaveId);
        FileHandler.writeLines(FileHandler.LEAVES_FILE, lines);
    }
}
