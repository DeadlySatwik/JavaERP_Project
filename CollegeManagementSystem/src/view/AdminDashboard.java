package view;

import controller.AdminController;
import model.Student;
import model.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private AdminController controller = new AdminController();
    private JTable studentTable;
    private JTable teacherTable;
    private DefaultTableModel sModel;
    private DefaultTableModel tModel;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();

        
        sModel = new DefaultTableModel(new Object[]{"ID","Roll","Name","Phone","COA","DSA","PDC","POM","WEB-DEV","DMS"},0);
        studentTable = new JTable(sModel);
        JScrollPane sScroll = new JScrollPane(studentTable);
        JPanel sPanel = new JPanel(new BorderLayout());
        JPanel sActions = new JPanel();
        JButton sRefresh = new JButton("Refresh");
        sRefresh.addActionListener(e -> loadStudents());
        JButton sDelete = new JButton("Delete Selected");
        sDelete.addActionListener(e -> deleteSelectedStudent());
    JButton sEdit = new JButton("Edit Selected");
    sEdit.addActionListener(e -> editSelectedStudent());
        sActions.add(sRefresh); sActions.add(sDelete);
    sActions.add(sEdit);
        sPanel.add(sScroll, BorderLayout.CENTER);
        sPanel.add(sActions, BorderLayout.SOUTH);

        
    tModel = new DefaultTableModel(new Object[]{"ID","Name","Subject","Phone"},0);
        teacherTable = new JTable(tModel);
        JScrollPane tScroll = new JScrollPane(teacherTable);
        JPanel tPanel = new JPanel(new BorderLayout());
        JPanel tActions = new JPanel();
        JButton tRefresh = new JButton("Refresh");
        tRefresh.addActionListener(e -> loadTeachers());
        JButton tDelete = new JButton("Delete Selected");
        tDelete.addActionListener(e -> deleteSelectedTeacher());
    JButton tEdit = new JButton("Edit Selected");
    tEdit.addActionListener(e -> editSelectedTeacher());
        tActions.add(tRefresh); tActions.add(tDelete);
    tActions.add(tEdit);
        tPanel.add(tScroll, BorderLayout.CENTER);
        tPanel.add(tActions, BorderLayout.SOUTH);

        tabs.addTab("Students", sPanel);
        tabs.addTab("Teachers", tPanel);

        
        JPanel top = new JPanel(new BorderLayout());
        top.setPreferredSize(new Dimension(1000, 60));
        top.setBackground(new Color(14, 65, 140));
        JLabel headerTitle = new JLabel("Admin Dashboard");
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setFont(new Font("Serif", Font.BOLD, 20));
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setOpaque(false);
        left.add(headerTitle);
        top.add(left, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(ae -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
        });
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setOpaque(false);
        right.add(logoutBtn);
        top.add(right, BorderLayout.EAST);

        JPanel container = new JPanel(new BorderLayout());
        container.add(top, BorderLayout.NORTH);
        container.add(tabs, BorderLayout.CENTER);

        add(container);
        loadStudents();
        loadTeachers();
    }

    private void loadStudents() {
        sModel.setRowCount(0);
        List<Student> list = controller.getAllStudents();
        for (Student s : list) {
            sModel.addRow(new Object[]{s.getId(), s.getRollno(), s.getName(), s.getPhone(), s.getCOA(), s.getDSA(), s.getPDC(), s.getPOM(), s.getWEBDEV(), s.getDMS()});
        }
    }

    private void deleteSelectedStudent() {
        int row = studentTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a student"); return; }
        int id = (int) sModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete student id " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (controller.deleteStudent(id)) {
                JOptionPane.showMessageDialog(this, "Deleted");
                loadStudents();
            } else JOptionPane.showMessageDialog(this, "Failed to delete");
        }
    }

    private void editSelectedStudent() {
        int row = studentTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a student"); return; }
        int id = (int) sModel.getValueAt(row, 0);
        
        Student target = null;
        for (Student s : controller.getAllStudents()) if (s.getId() == id) { target = s; break; }
        if (target == null) { JOptionPane.showMessageDialog(this, "Student not found"); return; }

        JTextField nameF = new JTextField(target.getName());
        JTextField rollF = new JTextField(target.getRollno());
        JTextField phoneF = new JTextField(target.getPhone());
        JTextField addrF = new JTextField(target.getAddress());
        JTextField photoF = new JTextField(target.getPhotoUrl() == null ? "" : target.getPhotoUrl());
        JTextField passF = new JTextField(target.getPassword() == null ? "" : target.getPassword());

        JPanel p = new JPanel(new GridLayout(0,1));
        p.add(new JLabel("Name")); p.add(nameF);
        p.add(new JLabel("Roll No")); p.add(rollF);
        p.add(new JLabel("Phone")); p.add(phoneF);
        p.add(new JLabel("Address")); p.add(addrF);
        p.add(new JLabel("Photo URL (path or URL)")); p.add(photoF);
        p.add(new JLabel("Password")); p.add(passF);

        int ok = JOptionPane.showConfirmDialog(this, p, "Edit Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok == JOptionPane.OK_OPTION) {
            boolean res = controller.updateStudent(id, nameF.getText().trim(), rollF.getText().trim(), phoneF.getText().trim(), addrF.getText().trim(), photoF.getText().trim(), passF.getText().trim());
            if (res) { JOptionPane.showMessageDialog(this, "Student updated"); loadStudents(); }
            else JOptionPane.showMessageDialog(this, "Failed to update student");
        }
    }

    private void loadTeachers() {
        tModel.setRowCount(0);
        List<Teacher> list = controller.getAllTeachers();
        for (Teacher t : list) {
            tModel.addRow(new Object[]{t.getId(), t.getName(), t.getSubject(), t.getPhone() == null ? "" : t.getPhone()});
        }
    }

    private void deleteSelectedTeacher() {
        int row = teacherTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a teacher"); return; }
        int id = (int) tModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete teacher id " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (controller.deleteTeacher(id)) {
                JOptionPane.showMessageDialog(this, "Deleted");
                loadTeachers();
            } else JOptionPane.showMessageDialog(this, "Failed to delete");
        }
    }

    private void editSelectedTeacher() {
        int row = teacherTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a teacher"); return; }
        int id = (int) tModel.getValueAt(row, 0);
        Teacher target = null;
        for (Teacher t : controller.getAllTeachers()) if (t.getId() == id) { target = t; break; }
        if (target == null) { JOptionPane.showMessageDialog(this, "Teacher not found"); return; }

        JTextField nameF = new JTextField(target.getName());
        JTextField subjectF = new JTextField(target.getSubject());
        JTextField phoneF = new JTextField(target.getPhone() == null ? "" : target.getPhone());
        JTextField passF = new JTextField(target.getPassword() == null ? "" : target.getPassword());

        JPanel p = new JPanel(new GridLayout(0,1));
        p.add(new JLabel("Name")); p.add(nameF);
        p.add(new JLabel("Subject")); p.add(subjectF);
        p.add(new JLabel("Phone")); p.add(phoneF);
        p.add(new JLabel("Password")); p.add(passF);

        int ok = JOptionPane.showConfirmDialog(this, p, "Edit Teacher", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok == JOptionPane.OK_OPTION) {
            boolean res = controller.updateTeacher(id, nameF.getText().trim(), subjectF.getText().trim(), phoneF.getText().trim(), passF.getText().trim());
            if (res) { JOptionPane.showMessageDialog(this, "Teacher updated"); loadTeachers(); }
            else JOptionPane.showMessageDialog(this, "Failed to update teacher");
        }
    }
}
