package dboperations;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import users.Teacher;
import users.dbinterfaces.TeacherDatabaseOperation;

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
            String teacherName = resultSet.getString("Teacher_Name");
            String teacherEmail = resultSet.getString("Teacher_Email");
            String teacherBloodGroup = resultSet.getString("Teacher_Blood_Group");
            String teacherContactNumber = resultSet.getString("Teacher_Contact_Number");
            String teacherAddress = resultSet.getString("Teacher_Address");

            Teacher teacher = new Teacher(teacherInitial, teacherName, teacherEmail, teacherBloodGroup, teacherContactNumber, teacherAddress);
            teacherList.add(teacher);
        }
        return teacherList;
    }

    @Override
    public Teacher getTeacher(String teacherId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = String.format("SELECT * FROM TEACHER WHERE Teacher_Id='%s'", teacherId);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        if (!resultSet.next())
            return null;

        String teacherName = resultSet.getString("Teacher_Name");
        String teacherEmail = resultSet.getString("Teacher_Email");
        String teacherBloodGroup = resultSet.getString("Teacher_Blood_Group");
        String teacherContactNumber = resultSet.getString("Teacher_Contact_Number");
        String teacherAddress = resultSet.getString("Teacher_Address");

        return new Teacher(teacherId, teacherName, teacherEmail, teacherBloodGroup, teacherContactNumber, teacherAddress);
    }

    @Override
    public boolean updateTeacherEmail(String teacherId, String email) {
        String queryForEmailUpdate = String.format("UPDATE TEACHER SET Teacher_Email='%s' WHERE Teacher_ID='%s'",
                email,
                teacherId);

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
    public boolean updateTeacherBloodGroup(String teacherId, String bloodGroup) {
        String queryForBloodGroupUpdate = String.format("UPDATE TEACHER SET Teacher_Blood_Group='%s' WHERE Teacher_ID='%s'",
                bloodGroup,
                teacherId);
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
    public boolean updateTeacherContactNumber(String teacherId, String contactNumber) {
        String queryForContactNumberUpdate = String.format("UPDATE TEACHER SET Teacher_Contact_Number='%s' WHERE Teacher_ID='%s'",
                contactNumber,
                teacherId);
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
    public boolean updateTeacherAddress(String teacherId, String address) {
        String queryForAddressUpdate = String.format("UPDATE TEACHER SET Teacher_Address='%s' WHERE Teacher_ID='%s'",
                address,
                teacherId);

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
}
