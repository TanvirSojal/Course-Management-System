package dboperations;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import users.Teacher;
import users.TeacherDatabaseOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeacherDatabaseOperationImplementation implements TeacherDatabaseOperation {
    @Override
    public ObservableList<Teacher> getAllTeachers() throws SQLException {
        String getQuery = String.format("SELECT * FROM TEACHER");
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getQuery);

        ObservableList <Teacher> teacherList = FXCollections.observableArrayList();
        while(resultSet.next()){
            String teacherInitial = resultSet.getString("Teacher_ID");
            String teacherName    = resultSet.getString("Teacher_Name");
            String teacherEmail   = resultSet.getString("Teacher_Email");

            Teacher teacher = new Teacher(teacherInitial, teacherName, teacherEmail);
            teacherList.add(teacher);
        }
        return teacherList;
    }
}
