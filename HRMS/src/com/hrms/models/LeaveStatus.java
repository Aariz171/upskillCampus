package com.hrms.models;

public enum LeaveStatus {
    PENDING, APPROVED, REJECTED;

    public static LeaveStatus fromString(String s) {
        try { return LeaveStatus.valueOf(s.toUpperCase()); }
        catch (Exception e) { return null; }
    }
}
