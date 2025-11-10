package view;

import model.Student;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class StudentDashboard extends JFrame {
    private Student student;

    public StudentDashboard(Student s) {
        this.student = s;
        setTitle("Student Dashboard - " + s.getName());
        setSize(800, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        try {
            Image bg = null;
            if (student.getPhotoUrl() != null && !student.getPhotoUrl().isEmpty()) {
                try {
                    bg = ImageIO.read(new URL("https://t3.ftcdn.net/jpg/08/94/18/56/360_F_894185679_2ZvyxKySzDZ8axuP2mC96NcL2Kbb188I.jpg"));
                } catch (Exception ex) {
                    bg = null;
                }
            }
            if (bg == null) {
                File f = new File("assets/background.jpg");
                if (f.exists()) bg = ImageIO.read(f);
                else bg = ImageIO.read(new URL("https://hips.hearstapps.com/hmg-prod/images/berry-college-historic-campus-at-twilight-royalty-free-image-1652127954.jpg"));
            }
            Image scaledBg = bg.getScaledInstance(800, 520, Image.SCALE_SMOOTH);
            JLabel bgLabel = new JLabel(new ImageIcon(scaledBg));
            bgLabel.setLayout(new BorderLayout());
            setContentPane(bgLabel);

            JPanel container = new JPanel(new BorderLayout());
            container.setOpaque(false);

            JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
            header.setOpaque(false);
            header.setPreferredSize(new Dimension(800, 100));
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            titlePanel.setOpaque(false);
            JLabel title = new JLabel("Student Profile");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Serif", Font.BOLD, 26));
            titlePanel.add(title);

            JButton logoutBtn = new JButton("Logout");
            logoutBtn.setFocusPainted(false);
            logoutBtn.setBackground(new Color(220,220,220,180));
            logoutBtn.addActionListener(ae -> {
                dispose();
                SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
            });

            header.setLayout(new BorderLayout());
            header.add(titlePanel, BorderLayout.WEST);
            JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            rightButtons.setOpaque(false);
            rightButtons.add(logoutBtn);
            header.add(rightButtons, BorderLayout.EAST);

            JPanel main = new JPanel();
            main.setOpaque(false);
            main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
            main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JPanel left = new JPanel();
            left.setOpaque(false);
            left.setPreferredSize(new Dimension(260, 360));
            JLabel photo = new JLabel();
            photo.setPreferredSize(new Dimension(220, 300));
            try {
                    Image img = null;
                    if (student.getPhotoUrl() != null && !student.getPhotoUrl().isEmpty()) {
                        File pf = new File(student.getPhotoUrl());
                        if (pf.exists()) img = ImageIO.read(pf);
                        else {
                            try {
                                img = ImageIO.read(new URL(student.getPhotoUrl()));
                            } catch (Exception ex) {
                                img = null;
                            }
                        }
                    }
                    if (img == null) {
                        File def = new File("assets/student_placeholder.jpg");
                        if (def.exists()) img = ImageIO.read(def);
                        else img = ImageIO.read(new URL("https://t3.ftcdn.net/jpg/08/94/18/56/360_F_894185679_2ZvyxKySzDZ8axuP2mC96NcL2Kbb188I.jpg"));
                    }
                Image scaled = img.getScaledInstance(220, 300, Image.SCALE_SMOOTH);
                photo.setIcon(new ImageIcon(scaled));
            } catch (IOException e) { /* ignore */ }
            left.add(photo);

            JPanel right = new JPanel();
            right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
            right.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
            right.setBackground(new Color(255, 255, 255, 200));
            right.setOpaque(true);

            JLabel nameL = new JLabel("Full Name: " + student.getName());
            JLabel rollL = new JLabel("Roll Number: " + student.getRollno());
            JLabel phoneL = new JLabel("Phone: " + student.getPhone());
            JLabel addrL = new JLabel("Address: " + student.getAddress());
            nameL.setFont(new Font("SansSerif", Font.BOLD, 16));
            rollL.setFont(new Font("SansSerif", Font.PLAIN, 14));

            right.add(nameL);
            right.add(Box.createRigidArea(new Dimension(0,8)));
            right.add(rollL);
            right.add(Box.createRigidArea(new Dimension(0,8)));
            right.add(phoneL);
            right.add(Box.createRigidArea(new Dimension(0,8)));
            right.add(addrL);
            right.add(Box.createRigidArea(new Dimension(0,16)));

            right.add(new JLabel("Marks:"));
            right.add(new JLabel("COA: " + student.getCOA()));
            right.add(new JLabel("DSA: " + student.getDSA()));
            right.add(new JLabel("PDC: " + student.getPDC()));
            right.add(new JLabel("POM: " + student.getPOM()));
            right.add(new JLabel("WEB-DEV: " + student.getWEBDEV()));
            right.add(new JLabel("DMS: " + student.getDMS()));

            main.add(left);
            main.add(Box.createRigidArea(new Dimension(20,0)));
            main.add(right);

            container.add(header, BorderLayout.NORTH);
            container.add(main, BorderLayout.CENTER);

            getContentPane().add(container, BorderLayout.CENTER);
        } catch (IOException e) {
            JPanel root = new JPanel(new BorderLayout());

            JPanel header = new JPanel();
            header.setBackground(new Color(10, 50, 120));
            header.setPreferredSize(new Dimension(800, 100));
            header.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel title = new JLabel("Student Profile");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Serif", Font.BOLD, 26));
            header.add(title);

            JPanel main = new JPanel();
            main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
            main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JPanel left = new JPanel();
            left.setPreferredSize(new Dimension(260, 360));
            JLabel photo = new JLabel();
            photo.setPreferredSize(new Dimension(220, 300));
            try {
                Image img = null;
                if (student.getPhotoUrl() != null && !student.getPhotoUrl().isEmpty()) {
                    File f = new File(student.getPhotoUrl());
                    if (f.exists()) img = ImageIO.read(f);
                    else img = ImageIO.read(new URL(student.getPhotoUrl()));
                }
                if (img == null) {
                    File def = new File("assets/student_placeholder.jpg");
                    if (def.exists()) img = ImageIO.read(def);
                    else img = ImageIO.read(new URL("https://t3.ftcdn.net/jpg/08/94/18/56/360_F_894185679_2ZvyxKySzDZ8axuP2mC96NcL2Kbb188I.jpg"));
                }
                Image scaled = img.getScaledInstance(220, 300, Image.SCALE_SMOOTH);
                photo.setIcon(new ImageIcon(scaled));
            } catch (IOException ex) { /* ignore */ }

            left.add(photo);

            JPanel right = new JPanel();
            right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
            right.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));

            right.add(new JLabel("Full Name: " + student.getName()));
            right.add(Box.createRigidArea(new Dimension(0,8)));
            right.add(new JLabel("Roll Number: " + student.getRollno()));
            right.add(Box.createRigidArea(new Dimension(0,8)));
            right.add(new JLabel("Phone: " + student.getPhone()));
            right.add(Box.createRigidArea(new Dimension(0,8)));
            right.add(new JLabel("Address: " + student.getAddress()));
            right.add(Box.createRigidArea(new Dimension(0,16)));

            right.add(new JLabel("Marks:"));
            right.add(new JLabel("COA: " + student.getCOA()));
            right.add(new JLabel("DSA: " + student.getDSA()));
            right.add(new JLabel("PDC: " + student.getPDC()));
            right.add(new JLabel("POM: " + student.getPOM()));
            right.add(new JLabel("WEB-DEV: " + student.getWEBDEV()));
            right.add(new JLabel("DMS: " + student.getDMS()));

            main.add(left);
            main.add(right);

            root.add(header, BorderLayout.NORTH);
            root.add(main, BorderLayout.CENTER);

            add(root);
        }
    }
}
