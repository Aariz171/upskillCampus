package com.hrms.models;

public enum AttendanceStatus {
    PRESENT, ABSENT, ON_LEAVE;

    public static AttendanceStatus fromString(String s) {
        try { return AttendanceStatus.valueOf(s.toUpperCase()); }
        catch (Exception e) { return null; }
    }
}
