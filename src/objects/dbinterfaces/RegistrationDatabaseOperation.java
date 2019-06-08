package objects.dbinterfaces;

import javafx.collections.ObservableList;
import objects.Course;
import objects.Registration;
import users.Student;

import java.sql.SQLException;

public interface RegistrationDatabaseOperation {
    boolean insertRegistrationEntry(Registration registration);
    boolean exists(Course course, Student student) throws SQLException;
    int getLastPrimaryKey() throws SQLException;
    boolean removeRegistration(Course course, Student student);
    ObservableList <Course> getAllRegisteredCourses(Student student) throws SQLException;
}
