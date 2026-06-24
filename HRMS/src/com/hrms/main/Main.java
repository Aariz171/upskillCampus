package com.hrms.main;

import com.hrms.auth.AuthService;
import com.hrms.ui.MainMenu;
import com.hrms.utils.ConsoleUtils;
import com.hrms.utils.FileHandler;

public class Main {
    public static void main(String[] args) {
        FileHandler.initializeDataFiles();
        ConsoleUtils.printBanner();
        AuthService authService = new AuthService();
        MainMenu mainMenu = new MainMenu(authService);
        mainMenu.start();
    }
}
