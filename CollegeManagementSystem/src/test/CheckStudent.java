package test;

import controller.StudentController;
import model.Student;

public class CheckStudent {
    public static void main(String[] args) {
        String roll = args.length > 0 ? args[0] : "R001";
        StudentController sc = new StudentController();
        Student s = sc.getStudentByRoll(roll);
        if (s == null) {
            System.out.println("Student not found for roll: " + roll);
        } else {
            System.out.println("Found student: " + s.getName());
            System.out.println("Roll: " + s.getRollno());
            System.out.println("Password stored: '" + s.getPassword() + "'");
            System.out.println("COA: " + s.getCOA());
        }
    }
}
