package com.hrms.models;

public enum LeaveType {
    SICK, CASUAL, ANNUAL, MATERNITY, UNPAID;

    public static LeaveType fromString(String s) {
        try { return LeaveType.valueOf(s.toUpperCase()); }
        catch (Exception e) { return null; }
    }
}
