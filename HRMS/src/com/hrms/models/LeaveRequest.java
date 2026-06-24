package com.hrms.models;

public class LeaveRequest {
    private String leaveId;
    private String employeeId;
    private LeaveType leaveType;
    private String startDate;
    private String endDate;
    private LeaveStatus status;
    private String reason;

    public LeaveRequest(String leaveId, String employeeId, LeaveType leaveType,
                        String startDate, String endDate, LeaveStatus status, String reason) {
        this.leaveId    = leaveId;
        this.employeeId = employeeId;
        this.leaveType  = leaveType;
        this.startDate  = startDate;
        this.endDate    = endDate;
        this.status     = status;
        this.reason     = reason;
    }

    public String toFileString() {
        return String.join("|", leaveId, employeeId, leaveType.name(),
                startDate, endDate, status.name(), reason);
    }

    public static LeaveRequest fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 7) return null;
        LeaveType   lt = LeaveType.fromString(p[2].trim());
        LeaveStatus ls = LeaveStatus.fromString(p[5].trim());
        if (lt == null || ls == null) return null;
        return new LeaveRequest(p[0].trim(), p[1].trim(), lt,
                p[3].trim(), p[4].trim(), ls, p[6].trim());
    }

    public String getLeaveId()       { return leaveId; }
    public String getEmployeeId()    { return employeeId; }
    public LeaveType getLeaveType()  { return leaveType; }
    public String getStartDate()     { return startDate; }
    public String getEndDate()       { return endDate; }
    public LeaveStatus getStatus()   { return status; }
    public String getReason()        { return reason; }
    public void setStatus(LeaveStatus s) { this.status = s; }

    public void printFormatted() {
        System.out.println("+-----------------------------------------------+");
        System.out.printf( "|  Leave ID    : %-32s|%n", leaveId);
        System.out.printf( "|  Employee ID : %-32s|%n", employeeId);
        System.out.printf( "|  Type        : %-32s|%n", leaveType);
        System.out.printf( "|  Start Date  : %-32s|%n", startDate);
        System.out.printf( "|  End Date    : %-32s|%n", endDate);
        System.out.printf( "|  Status      : %-32s|%n", status);
        System.out.printf( "|  Reason      : %-32s|%n", reason);
        System.out.println("+-----------------------------------------------+");
    }

    @Override
    public String toString() {
        return String.format("| %-18s | %-8s | %-10s | %-12s | %-12s | %-10s |",
                leaveId, employeeId, leaveType, startDate, endDate, status);
    }
}
