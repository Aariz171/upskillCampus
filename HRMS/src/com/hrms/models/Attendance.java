package com.hrms.models;

public class Attendance {
    private String employeeId;
    private String date;
    private AttendanceStatus status;

    public Attendance(String employeeId, String date, AttendanceStatus status) {
        this.employeeId = employeeId;
        this.date       = date;
        this.status     = status;
    }

    public String toFileString() {
        return String.join("|", employeeId, date, status.name());
    }

    public static Attendance fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 3) return null;
        AttendanceStatus s = AttendanceStatus.fromString(p[2].trim());
        if (s == null) return null;
        return new Attendance(p[0].trim(), p[1].trim(), s);
    }

    public String getEmployeeId()            { return employeeId; }
    public String getDate()                  { return date; }
    public AttendanceStatus getStatus()      { return status; }
    public void setStatus(AttendanceStatus s){ this.status = s; }

    @Override
    public String toString() {
        return String.format("| %-8s | %-12s | %-10s |", employeeId, date, status);
    }
}
