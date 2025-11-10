package view;

import controller.AdminController;
import controller.StudentController;
import controller.TeacherController;
import model.Student;
import model.Teacher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LoginScreen extends JFrame {
    private JComboBox<String> roleCombo;
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JLabel bgLabel;

    public LoginScreen() {
        setTitle("College Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        try {
            Image img = null;
            File f = new File("assets/background.jpg");
            if (f.exists()) img = ImageIO.read(f);
            else img = ImageIO.read(new URL("https://hips.hearstapps.com/hmg-prod/images/berry-college-historic-campus-at-twilight-royalty-free-image-1652127954.jpg"));
            Image scaled = img.getScaledInstance(700, 420, Image.SCALE_SMOOTH);
            bgLabel = new JLabel(new ImageIcon(scaled));
            bgLabel.setLayout(new BorderLayout());
            setContentPane(bgLabel);
        } catch (IOException e) {
            System.err.println("Background not available: " + e.getMessage());
        }

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("College Management System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        roleCombo = new JComboBox<>(new String[]{"Student", "Teacher", "Admin"});
        roleCombo.setMaximumSize(new Dimension(300, 30));
        panel.add(roleCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        userField = new JTextField();
        userField.setMaximumSize(new Dimension(300, 30));
        userField.setAlignmentX(Component.CENTER_ALIGNMENT);
        userField.setToolTipText("Student: Roll No, Teacher: Name, Admin: Username");
        panel.add(userField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 30));
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        loginBtn = new JButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(loginBtn);

    getContentPane().add(panel, BorderLayout.CENTER);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        String role = (String) roleCombo.getSelectedItem();
        String user = userField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if (role.equals("Student")) {
            StudentController sc = new StudentController();
            Student s = sc.getStudentByRoll(user);
            if (s != null && (s.getPassword() == null || s.getPassword().isEmpty() || s.getPassword().equals(pass))) {
                SwingUtilities.invokeLater(() -> new StudentDashboard(s).setVisible(true));
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid student credentials (roll or password)");
            }
        } else if (role.equals("Teacher")) {
            TeacherController tc = new TeacherController();
            Teacher t = tc.authenticate(user, pass);
            if (t != null) {
                SwingUtilities.invokeLater(() -> new TeacherDashboard(t).setVisible(true));
                dispose();
            } else JOptionPane.showMessageDialog(this, "Invalid teacher credentials");
        } else if (role.equals("Admin")) {
            AdminController ac = new AdminController();
            if (ac.authenticate(user, pass)) {
                SwingUtilities.invokeLater(() -> new view.AdminDashboard().setVisible(true));
                dispose();
            } else JOptionPane.showMessageDialog(this, "Invalid admin credentials");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen ls = new LoginScreen();
            ls.setVisible(true);
        });
    }
}
