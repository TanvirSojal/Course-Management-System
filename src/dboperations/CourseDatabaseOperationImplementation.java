package dboperations;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Course;
import objects.dbinterfaces.CourseDatabaseOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseDatabaseOperationImplementation implements CourseDatabaseOperation {
    @Override
    public ObservableList <Course> getAllCourses() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = String.format("SELECT * FROM COURSE");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ObservableList < Course > courseList = FXCollections.observableArrayList();
        while(resultSet.next()){
            int courseId = resultSet.getInt("Course_ID");
            String courseCode = resultSet.getString("Course_Code");
            String courseTitle = resultSet.getString("Course_Title");
            int courseSection = resultSet.getInt("Course_Section");
            Course course = new Course(courseId, courseCode, courseTitle, courseSection);
            courseList.add(course);
        }
        return courseList;
    }

    @Override
    public Course getCourse(int courseId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = String.format("SELECT * FROM COURSE WHERE Course_ID=%d",
                                    courseId);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet.next()){

            String courseCode = resultSet.getString("Course_Code");
            String courseTitle = resultSet.getString("Course_Title");
            int courseSection = resultSet.getInt("Course_Section");
            return new Course(courseId, courseCode, courseTitle, courseSection);
        }
        return null;
    }
}
