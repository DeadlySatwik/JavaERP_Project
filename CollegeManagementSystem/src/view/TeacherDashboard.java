package view;

import controller.TeacherController;
import model.Student;
import model.Teacher;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TeacherDashboard extends JFrame {
    private Teacher teacher;
    private TeacherController controller = new TeacherController();
    private JTable table;
    private DefaultTableModel model;

    public TeacherDashboard(Teacher t) {
        this.teacher = t;
        setTitle("Teacher Dashboard - " + t.getName() + " (" + t.getSubject() + ")");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        
        try {
            Image bg = null;
            
            File assetsDir = new File("assets");
            if (!assetsDir.exists()) assetsDir.mkdirs();
            File cached = new File(assetsDir, "teacher_background.jpg");
            try {
                if (cached.exists()) {
                    bg = ImageIO.read(cached);
                } else {
                    
                    BufferedImage fetched = ImageIO.read(new URL("https://thumbs.dreamstime.com/b/professor-23802534.jpg"));
                    if (fetched != null) {
                        try {
                            ImageIO.write(fetched, "jpg", cached);
                        } catch (IOException w) {
                            
                        }
                        bg = fetched;
                    } else {
                        
                        if (cached.exists()) bg = ImageIO.read(cached);
                    }
                }
            } catch (Exception e) {
                
                if (cached.exists()) {
                    try { bg = ImageIO.read(cached); } catch (IOException ignored) {}
                }
            }
            if (bg == null) {
                
                bg = new BufferedImage(900, 520, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = ((BufferedImage) bg).createGraphics();
                g2.setPaint(new Color(14, 65, 140));
                g2.fillRect(0,0,900,520);
                g2.dispose();
            }
            JLabel bgLabel = new JLabel(new ImageIcon(bg.getScaledInstance(900, 520, Image.SCALE_SMOOTH)));
            bgLabel.setLayout(new BorderLayout());
            setContentPane(bgLabel);

            JPanel root = new JPanel(new BorderLayout());
            root.setOpaque(false);

            JPanel top = new JPanel(new BorderLayout());
            top.setOpaque(false);
            
            JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            titlePanel.setOpaque(false);
            JLabel title = new JLabel("Students - " + teacher.getSubject());
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Serif", Font.BOLD, 22));
            titlePanel.add(title);
            top.add(titlePanel, BorderLayout.WEST);

            
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            rightPanel.setOpaque(false);
            JTextField searchField = new JTextField(12);
            JButton searchBtn = new JButton("Search by Roll");
            JButton logoutBtn = new JButton("Logout");
            logoutBtn.setFocusPainted(false);
            logoutBtn.addActionListener(ae -> {
                dispose();
                SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
            });
            rightPanel.add(searchField);
            rightPanel.add(searchBtn);
            rightPanel.add(logoutBtn);
            top.add(rightPanel, BorderLayout.EAST);

        model = new DefaultTableModel(new Object[]{"ID","Roll","Name","Mark","Full Marks"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        table = new JTable(model);
        table.setRowHeight(28);
        
        table.setOpaque(false);
        table.setBackground(new Color(0,0,0,0));

        
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setOpaque(true);
        headerRenderer.setBackground(new Color(255,255,255,200));
        headerRenderer.setForeground(Color.BLACK);
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setOpaque(false);
        table.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane sp = new JScrollPane(table);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);

        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(true);
        tableContainer.setBackground(new Color(255,255,255,180)); 
        tableContainer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        tableContainer.add(sp, BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadStudents());

        JButton editBtn = new JButton("Edit Selected Mark");
        editBtn.addActionListener(e -> editSelectedMark());

        JPanel actions = new JPanel();
        actions.add(refresh);
        actions.add(editBtn);

        
        searchBtn.addActionListener(e -> {
            String roll = searchField.getText().trim();
            if (roll.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter a roll number"); return; }
            Student s = controller.getStudentsBySubject(teacher.getSubject()).stream().filter(x -> roll.equals(x.getRollno())).findFirst().orElse(null);
            if (s == null) JOptionPane.showMessageDialog(this, "Student not found: " + roll);
            else {
                
                String info = s.getName() + "\nRoll: " + s.getRollno() + "\nCurrent mark: " + getMarkForSubject(s, teacher.getSubject());
                String inp = JOptionPane.showInputDialog(this, info + "\nEnter new mark (or leave blank to cancel):");
                if (inp != null && !inp.trim().isEmpty()) {
                    try { int mark = Integer.parseInt(inp.trim());
                        boolean ok = controller.updateMark(s.getId(), teacher.getSubject(), mark);
                        if (ok) JOptionPane.showMessageDialog(this, "Mark updated"); else JOptionPane.showMessageDialog(this, "Failed to update mark");
                        loadStudents();
                    } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Enter a valid integer"); }
                }
            }
        });

    root.add(top, BorderLayout.NORTH);
    root.add(tableContainer, BorderLayout.CENTER);
        root.add(actions, BorderLayout.SOUTH);

        add(root);
        loadStudents();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadStudents() {
        model.setRowCount(0);
        List<Student> students = controller.getStudentsBySubject(teacher.getSubject());
        for (Student s : students) {
            int mark = getMarkForSubject(s, teacher.getSubject());
            model.addRow(new Object[]{s.getId(), s.getRollno(), s.getName(), mark, 100});
        }
    }

    private int getMarkForSubject(Student s, String subj) {
        switch (subj.toUpperCase()) {
            case "COA": return s.getCOA();
            case "DSA": return s.getDSA();
            case "PDC": return s.getPDC();
            case "POM": return s.getPOM();
            case "WEB-DEV":
            case "WEBDEV": return s.getWEBDEV();
            case "DMS": return s.getDMS();
            default: return 0;
        }
    }

    private void editSelectedMark() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a student row first"); return; }
        int id = (int) model.getValueAt(row, 0);
        String studentName = (String) model.getValueAt(row, 2);
        String input = JOptionPane.showInputDialog(this, "Enter new mark for " + studentName + " (" + teacher.getSubject() + "):");
        if (input == null) return;
        try {
            int mark = Integer.parseInt(input.trim());
            boolean ok = controller.updateMark(id, teacher.getSubject(), mark);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Mark updated");
                loadStudents();
            } else JOptionPane.showMessageDialog(this, "Failed to update mark");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer");
        }
    }
}
