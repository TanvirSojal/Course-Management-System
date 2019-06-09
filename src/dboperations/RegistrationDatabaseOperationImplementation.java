package dboperations;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Course;
import objects.Registration;
import objects.dbinterfaces.RegistrationDatabaseOperation;
import users.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationDatabaseOperationImplementation implements RegistrationDatabaseOperation {
    @Override
    public boolean insertRegistrationEntry(Registration registration) {
        String insertQuery = String.format("INSERT INTO REGISTRATION VALUES(%d, '%s', %d)",
                                            registration.getRegistrationId(),
                                            registration.getStudentId(),
                                            registration.getCourseId());

        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);
        } catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean exists(Course course, Student student) throws SQLException {
        String getQuery = String.format("SELECT COURSE.Course_Code\n" +
                        "FROM COURSE, REGISTRATION\n" +
                        "WHERE REGISTRATION.Student_ID='%s' AND COURSE.Course_Code='%s' AND REGISTRATION.Course_ID=COURSE.Course_Id",
                        student.getStudentId(),
                        course.getCourseCode());
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getQuery);
        return resultSet.next(); // if resultSet has element, the course is already registered for this particular student
    }

    // currently this method is implemented for manual auto-increment purpose
    @Override
    public int getLastPrimaryKey() throws SQLException {
        String getMaxPrimaryKey = String.format("SELECT MAX(Registration_ID) FROM REGISTRATION");
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(getMaxPrimaryKey);
        int lastKey = 0;
        if (resultSet.next()){
            lastKey = resultSet.getInt(1);
        }
        return lastKey;
    }

    @Override
    public boolean removeRegistration(Course course, Student student) {
        String deleteQuery = String.format("DELETE FROM REGISTRATION WHERE Student_Id='%s' AND Course_Id=%d",
                                            student.getStudentId(),
                                            course.getCourseId());
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteQuery);
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public ObservableList<Course> getAllRegisteredCourses(Student student) throws SQLException {
        String getQuery = String.format("SELECT COURSE.Course_ID, COURSE.Course_Code, COURSE.Course_Title, COURSE.Course_Section\n" +
                                        "FROM COURSE, REGISTRATION\n" +
                                        "WHERE REGISTRATION.Student_ID='%s' AND REGISTRATION.Course_ID=COURSE.Course_ID",
                                        student.getStudentId());
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getQuery);

        ObservableList <Course> courseList = FXCollections.observableArrayList();
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
    public ObservableList <Student> getAllRegisteredStudents(Course course) throws SQLException {
        String query = String.format("SELECT STUDENT.Student_ID, STUDENT.Student_Name, STUDENT.Student_Email, STUDENT.Student_Blood_Group, STUDENT.Student_Contact_Number, STUDENT.Student_Address " +
                                    "FROM REGISTRATION, STUDENT " +
                                    "WHERE REGISTRATION.Course_ID=%d AND STUDENT.Student_ID=REGISTRATION.Student_ID",
                                    course.getCourseId());

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ObservableList <Student> studentList = FXCollections.observableArrayList();

        while(resultSet.next()){
            String studentId = resultSet.getString("Student_ID");
            String studentName = resultSet.getString("Student_Name");
            String studentEmail = resultSet.getString("Student_Email");
            String studentBloodGroup = resultSet.getString("Student_Blood_Group");
            String studentContactNumber = resultSet.getString("Student_Contact_Number");
            String studentAddress = resultSet.getString("Student_Address");

            studentList.add(new Student(studentId, studentName, studentEmail, studentBloodGroup, studentContactNumber, studentAddress));
        }
        return studentList;
    }
}
