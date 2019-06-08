package users.dbinterfaces;

import javafx.collections.ObservableList;
import users.Teacher;

import java.sql.SQLException;

public interface TeacherDatabaseOperation {
    ObservableList <Teacher> getAllTeachers() throws SQLException;
    Teacher getTeacher(String teacherId) throws SQLException;
    boolean updateTeacherEmail(String teacherId, String email);
    boolean updateTeacherBloodGroup(String teacherId, String bloodGroup);
    boolean updateTeacherContactNumber(String teacherId, String contactNumber);
    boolean updateTeacherAddress(String teacherId, String address);
}
