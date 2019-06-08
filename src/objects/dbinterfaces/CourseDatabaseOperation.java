package objects.dbinterfaces;

import javafx.collections.ObservableList;
import objects.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CourseDatabaseOperation {
    ObservableList <Course> getAllCourses() throws SQLException;
    Course getCourse(int courseId) throws SQLException;
}
