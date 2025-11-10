package test;

import controller.AdminController;
import view.AdminDashboard;

import javax.swing.*;

public class LaunchAdmin {
    public static void main(String[] args) {
        
        String user = "admin";
        String pass = "admin";

        AdminController ac = new AdminController();
        boolean ok = ac.authenticate(user, pass);
        if (!ok) {
            System.err.println("Failed to authenticate admin: " + user);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            AdminDashboard d = new AdminDashboard();
            d.setVisible(true);
        });
    }
}
