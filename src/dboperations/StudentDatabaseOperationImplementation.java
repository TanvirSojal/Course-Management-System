package dboperations;

import dbconnection.DBConnection;
import users.Student;
import users.dbinterfaces.StudentDatabaseOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDatabaseOperationImplementation implements StudentDatabaseOperation {


    @Override
    public boolean updateStudentEmail(String studentId, String email) {
        String queryForEmailUpdate = String.format("UPDATE STUDENT SET Student_Email='%s' WHERE Student_ID='%s'",
                                                    email,
                                                    studentId);

        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForEmailUpdate);
            return true;

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStudentBloodGroup(String studentId, String bloodGroup) {
        String queryForBloodGroupUpdate = String.format("UPDATE STUDENT SET Student_Blood_Group='%s' WHERE Student_ID='%s'",
                                                    bloodGroup,
                                                    studentId);
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForBloodGroupUpdate);
            return true;

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStudentContactNumber(String studentId, String contactNumber) {
        String queryForContactNumberUpdate = String.format("UPDATE STUDENT SET Student_Contact_Number='%s' WHERE Student_ID='%s'",
                contactNumber,
                studentId);
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForContactNumberUpdate);
            return true;

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateStudentAddress(String studentId, String address) {
        String queryForAddressUpdate = String.format("UPDATE STUDENT SET Student_Address='%s' WHERE Student_ID='%s'",
                address,
                studentId);

        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForAddressUpdate);
            return true;

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public Student getStudent(String studentId) throws SQLException {

        Connection connection = DBConnection.getConnection();
        String query = String.format("SELECT * FROM STUDENT WHERE Student_ID='%s'", studentId);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        if (!resultSet.next())
            return null;

        String studentName = resultSet.getString("Student_Name");
        String studentEmail = resultSet.getString("Student_Email");
        String studentBloodGroup = resultSet.getString("Student_Blood_Group");
        String studentContactNumber = resultSet.getString("Student_Contact_Number");
        String studentAddress = resultSet.getString("Student_Address");

        return new Student(studentId, studentName, studentEmail, studentBloodGroup, studentContactNumber, studentAddress);
    }
}
