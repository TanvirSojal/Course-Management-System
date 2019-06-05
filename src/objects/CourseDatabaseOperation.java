package objects;

import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CourseDatabaseOperation {
    ObservableList <Course> getAllCourses() throws SQLException;
}