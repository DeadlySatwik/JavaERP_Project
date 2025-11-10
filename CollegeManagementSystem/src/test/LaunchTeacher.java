package test;

import controller.TeacherController;
import model.Teacher;
import view.TeacherDashboard;

import javax.swing.*;

public class LaunchTeacher {
    public static void main(String[] args) {
        
        String name = "Naveen Saini";
        String pass = "naveen123";

        TeacherController tc = new TeacherController();
        Teacher t = tc.authenticate(name, pass);
        if (t == null) {
            System.err.println("Failed to authenticate teacher: " + name);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            TeacherDashboard d = new TeacherDashboard(t);
            d.setVisible(true);
        });
    }
}
