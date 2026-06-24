package com.hrms.services;

import com.hrms.models.*;
import com.hrms.repositories.*;
import com.hrms.utils.FileHandler;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReportService {
    private final EmployeeRepository   empRepo    = new EmployeeRepository();
    private final AttendanceRepository attendRepo = new AttendanceRepository();
    private final LeaveRepository      leaveRepo  = new LeaveRepository();
    private static final String LINE  = "------------------------------------------------------------------------\n";
    private static final String STAMP = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

    public String generateEmployeeReport() {
        List<Employee> employees = empRepo.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("+=======================================================+\n");
        sb.append("|           HRMS - EMPLOYEE REPORT                      |\n");
        sb.append("+=======================================================+\n");
        sb.append("  Generated: ").append(LocalDate.now()).append("\n\n");
        sb.append(LINE);
        sb.append(String.format("| %-8s | %-20s | %-15s | %-15s |%n", "ID","Name","Department","Designation"));
        sb.append(LINE);
        if (employees.isEmpty()) sb.append("  No records found.\n");
        else for (Employee e : employees)
            sb.append(String.format("| %-8s | %-20s | %-15s | %-15s |%n",
                    e.getEmployeeId(), e.getName(), e.getDepartment(), e.getDesignation()));
        sb.append(LINE);
        sb.append("  Total Employees: ").append(employees.size()).append("\n");
        FileHandler.writeReport("employee_report_" + STAMP + ".txt", sb.toString());
        return sb.toString();
    }

    public String generateAttendanceReport(String fromDate, String toDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("+=======================================================+\n");
        sb.append("|          HRMS - ATTENDANCE REPORT                     |\n");
        sb.append("+=======================================================+\n");
        sb.append("  Period : ").append(fromDate).append(" to ").append(toDate).append("\n");
        sb.append("  Generated: ").append(LocalDate.now()).append("\n\n");
        sb.append(LINE);
        sb.append(String.format("| %-8s | %-12s | %-10s |%n", "Emp ID", "Date", "Status"));
        sb.append(LINE);
        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to   = LocalDate.parse(toDate);
        int present = 0, absent = 0, onLeave = 0;
        for (Attendance a : attendRepo.findAll()) {
            LocalDate d = LocalDate.parse(a.getDate());
            if (!d.isBefore(from) && !d.isAfter(to)) {
                sb.append(String.format("| %-8s | %-12s | %-10s |%n",
                        a.getEmployeeId(), a.getDate(), a.getStatus()));
                if (a.getStatus() == AttendanceStatus.PRESENT)       present++;
                else if (a.getStatus() == AttendanceStatus.ABSENT)   absent++;
                else if (a.getStatus() == AttendanceStatus.ON_LEAVE) onLeave++;
            }
        }
        sb.append(LINE);
        sb.append("  Summary -> Present: ").append(present)
          .append(" | Absent: ").append(absent)
          .append(" | On Leave: ").append(onLeave).append("\n");
        FileHandler.writeReport("attendance_report_" + STAMP + ".txt", sb.toString());
        return sb.toString();
    }

    public String generateLeaveReport() {
        List<LeaveRequest> leaves = leaveRepo.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("+=======================================================+\n");
        sb.append("|         HRMS - LEAVE SUMMARY REPORT                   |\n");
        sb.append("+=======================================================+\n");
        sb.append("  Generated: ").append(LocalDate.now()).append("\n\n");
        sb.append(LINE);
        sb.append(String.format("| %-18s | %-8s | %-10s | %-12s | %-12s | %-10s |%n",
                "Leave ID","Emp ID","Type","Start","End","Status"));
        sb.append(LINE);
        int pending = 0, approved = 0, rejected = 0;
        Map<String, Integer> perEmp = new LinkedHashMap<>();
        for (LeaveRequest lr : leaves) {
            sb.append(String.format("| %-18s | %-8s | %-10s | %-12s | %-12s | %-10s |%n",
                    lr.getLeaveId(), lr.getEmployeeId(), lr.getLeaveType(),
                    lr.getStartDate(), lr.getEndDate(), lr.getStatus()));
            if (lr.getStatus() == LeaveStatus.PENDING)        pending++;
            else if (lr.getStatus() == LeaveStatus.APPROVED)  approved++;
            else if (lr.getStatus() == LeaveStatus.REJECTED)  rejected++;
            perEmp.merge(lr.getEmployeeId(), 1, Integer::sum);
        }
        sb.append(LINE);
        sb.append("  Pending: ").append(pending).append(" | Approved: ").append(approved)
          .append(" | Rejected: ").append(rejected).append("\n\n");
        sb.append("  Leave Count per Employee:\n");
        for (Map.Entry<String, Integer> entry : perEmp.entrySet())
            sb.append("    ").append(entry.getKey()).append(" : ").append(entry.getValue()).append(" request(s)\n");
        FileHandler.writeReport("leave_report_" + STAMP + ".txt", sb.toString());
        return sb.toString();
    }
}
