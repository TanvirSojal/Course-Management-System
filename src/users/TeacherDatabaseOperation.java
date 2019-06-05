package users;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface TeacherDatabaseOperation {
    ObservableList <Teacher> getAllTeachers() throws SQLException;
}
