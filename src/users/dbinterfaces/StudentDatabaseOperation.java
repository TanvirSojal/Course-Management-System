package users.dbinterfaces;

import users.Student;

import java.sql.SQLException;

/*
* This interface holds all the methods necessary for Student dashboard
* */
public interface StudentDatabaseOperation {
    // All methods from "Student Information" page
    boolean updateStudentEmail(String studentId, String email);
    boolean updateStudentBloodGroup(String studentId, String bloodGroup);
    boolean updateStudentContactNumber(String studentId, String contactNumber);
    boolean updateStudentAddress(String studentId, String address);
    Student getStudent(String studentId) throws SQLException;
}
