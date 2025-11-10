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

public class AdminController {

    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<Student> getAllStudents() {
        StudentController sc = new StudentController();
        return sc.getAllStudents();
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM teachers";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Teacher t = new Teacher();
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("name"));
                t.setSubject(rs.getString("subject"));
                t.setPhotoUrl(rs.getString("photo_url"));
                
                try { t.setPhone(rs.getString("phone")); } catch (SQLException ignore) { /* column missing */ }
                t.setPassword(rs.getString("password"));
                list.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean deleteTeacher(int teacherId) {
        String sql = "DELETE FROM teachers WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateTeacherSubject(int teacherId, String subject) {
        String sql = "UPDATE teachers SET subject = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, subject);
            ps.setInt(2, teacherId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateStudent(int studentId, String name, String rollno, String phone, String address, String photoUrl, String password) {
        String sql = "UPDATE students SET name = ?, rollno = ?, phone = ?, address = ?, photo_url = ?, password = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, rollno);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setString(5, photoUrl);
            ps.setString(6, password);
            ps.setInt(7, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTeacher(int teacherId, String name, String subject, String phone, String password) {
        
        String sqlPhone = "UPDATE teachers SET name = ?, subject = ?, phone = ?, password = ? WHERE id = ?";
        String sqlPhotoFallback = "UPDATE teachers SET name = ?, subject = ?, photo_url = ?, password = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement(sqlPhone)) {
                ps.setString(1, name);
                ps.setString(2, subject);
                ps.setString(3, phone);
                ps.setString(4, password);
                ps.setInt(5, teacherId);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                
            }
            try (PreparedStatement ps2 = c.prepareStatement(sqlPhotoFallback)) {
                ps2.setString(1, name);
                ps2.setString(2, subject);
                ps2.setString(3, phone); 
                ps2.setString(4, password);
                ps2.setInt(5, teacherId);
                return ps2.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
