package controller;

import model.DatabaseConnection;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentController {

    public Student getStudentByRoll(String rollno) {
        String sql = "SELECT * FROM students WHERE rollno = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, rollno);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateMark(int studentId, String subject, int mark) {
        String col = mapSubjectToColumn(subject);
        if (col == null) return false;
        String sql = "UPDATE students SET " + col + " = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, mark);
            ps.setInt(2, studentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private String mapSubjectToColumn(String subject) {
        if (subject == null) return null;
        switch (subject.toUpperCase()) {
            case "COA": return "COA";
            case "DSA": return "DSA";
            case "PDC": return "PDC";
            case "POM": return "POM";
            case "WEB-DEV":
            case "WEBDEV": return "WEBDEV";
            case "DMS": return "DMS";
            default: return null;
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
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
        return s;
    }
}
