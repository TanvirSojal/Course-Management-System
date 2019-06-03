package dboperations;

import dbconnection.DBConnection;
import objects.Registration;
import objects.RegistrationDatabaseOperation;

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
    public int getLastPrimaryKey() throws SQLException {
        String getMaxPrimaryKey = String.format("SELECT MAX(Registration_Id) FROM REGISTRATION");
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(getMaxPrimaryKey);
        int lastKey = 0;
        if (resultSet.next()){
            lastKey = resultSet.getInt(1);
        }
        return lastKey;
    }
}
