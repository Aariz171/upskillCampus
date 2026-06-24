package com.hrms.auth;

import com.hrms.models.Admin;
import com.hrms.utils.FileHandler;
import com.hrms.utils.HashUtils;
import java.util.List;

public class AuthService {
    private Admin loggedInAdmin = null;

    public boolean login(String username, String password) {
        String hash = HashUtils.sha256(password);
        List<String> lines = FileHandler.readLines(FileHandler.ADMINS_FILE);
        for (String line : lines) {
            Admin admin = Admin.fromFileString(line);
            if (admin != null
                    && admin.getUsername().equalsIgnoreCase(username)
                    && admin.getPasswordHash().equals(hash)) {
                loggedInAdmin = admin;
                return true;
            }
        }
        return false;
    }

    public void logout()                    { loggedInAdmin = null; }
    public boolean isAuthenticated()        { return loggedInAdmin != null; }
    public String getLoggedInUsername()     { return loggedInAdmin != null ? loggedInAdmin.getUsername() : "N/A"; }
}
