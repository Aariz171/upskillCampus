package com.hrms.models;

public class Admin {
    private String username;
    private String passwordHash;

    public Admin(String username, String passwordHash) {
        this.username     = username;
        this.passwordHash = passwordHash;
    }

    public String toFileString() { return username + "|" + passwordHash; }

    public static Admin fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 2) return null;
        return new Admin(p[0].trim(), p[1].trim());
    }

    public String getUsername()     { return username; }
    public String getPasswordHash() { return passwordHash; }
}
