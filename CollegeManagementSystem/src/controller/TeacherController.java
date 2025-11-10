package controller;

import model.DatabaseConnection;
import model.Student;
import model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherController {

    public Teacher authenticate(String name, String password) {
        String sql = "SELECT * FROM teachers WHERE name = ? AND password = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Teacher t = new Teacher();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setSubject(rs.getString("subject"));
                    t.setPhotoUrl(rs.getString("photo_url"));
                    t.setPassword(rs.getString("password"));
                    return t;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Student> getStudentsBySubject(String subject) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students"; 
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setRollno(rs.getString("rollno"));
                s.setPhone(rs.getString("phone"));
                s.setAddress(rs.getString("address"));
                s.setPhotoUrl(rs.getString("photo_url"));
                s.setPassword(rs.getString("password"));
                s.setCOA(rs.getInt("COA"));
                s.setDSA(rs.getInt("DSA"));
                s.setPDC(rs.getInt("PDC"));
                s.setPOM(rs.getInt("POM"));
                s.setWEBDEV(rs.getInt("WEBDEV"));
                s.setDMS(rs.getInt("DMS"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateMark(int studentId, String subject, int mark) {
        StudentController sc = new StudentController();
        return sc.updateMark(studentId, subject, mark);
    }
}
