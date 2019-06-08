package dboperations;

import dbconnection.DBConnection;
import users.Chairman;
import users.dbinterfaces.ChairmanDatabaseOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChairmanDatabaseOperationImplementation implements ChairmanDatabaseOperation {
    @Override
    public Chairman getChairman(String chairmanId) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String query = String.format("SELECT * FROM CHAIRMAN WHERE Chairman_Id='%s'", chairmanId);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        if (!resultSet.next())
            return null;

        String chairmanName = resultSet.getString("Chairman_Name");
        String chairmanEmail = resultSet.getString("Chairman_Email");
        String chairmanBloodGroup = resultSet.getString("Chairman_Blood_Group");
        String chairmanContactNumber = resultSet.getString("Chairman_Contact_Number");
        String chairmanAddress = resultSet.getString("Chairman_Address");

        return new Chairman(chairmanId, chairmanName, chairmanEmail, chairmanBloodGroup, chairmanContactNumber, chairmanAddress);
    }

    @Override
    public boolean updateChairmanEmail(String chairmanId, String email) {
        String queryForEmailUpdate = String.format("UPDATE CHAIRMAN SET Chairman_Email='%s' WHERE Chairman_ID='%s'",
                email,
                chairmanId);

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
    public boolean updateChairmanBloodGroup(String chairmanId, String bloodGroup) {
        String queryForBloodGroupUpdate = String.format("UPDATE CHAIRMAN SET Chairman_Blood_Group='%s' WHERE Chairman_ID='%s'",
                bloodGroup,
                chairmanId);
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
    public boolean updateChairmanContactNumber(String chairmanId, String contactNumber) {
        String queryForContactNumberUpdate = String.format("UPDATE CHAIRMAN SET Chairman_Contact_Number='%s' WHERE Chairman_ID='%s'",
                contactNumber,
                chairmanId);
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
    public boolean updateChairmanAddress(String chairmanId, String address) {
        String queryForAddressUpdate = String.format("UPDATE CHAIRMAN SET Chairman_Address='%s' WHERE Chairman_ID='%s'",
                address,
                chairmanId);

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
