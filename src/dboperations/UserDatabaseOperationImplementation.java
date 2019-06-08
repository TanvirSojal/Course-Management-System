package dboperations;

import dbconnection.DBConnection;
import users.LoginInfo;
import users.User;
import users.dbinterfaces.UserDatabaseOperation;

import java.sql.*;

public class UserDatabaseOperationImplementation implements UserDatabaseOperation {

    @Override
    public boolean insertUser(User user) {
        /*
        * queryForLoginTable consists of the attribute the database table "LOGINS" have.
        * They are: username, password, usertype
        * username and password are later used to login, and usertype to fetch information from corresponding table
        * */
        String queryForLoginTable = String.format("INSERT INTO LOGINS VALUES('%s', '%s', '%s')",
                                    user.getUsername(),
                                    user.getPassword(),
                                    user.getType());

        /*
        * queryForUserTable takes signup information of the user and
        * inserts into corresponding table
        * Currently, the tables are: Student, Teacher, Chairman
        * */
        String queryForUserTable = String.format("INSERT INTO %s VALUES('%s', '%s', '%s', '', '', '')",
                user.getType(),
                user.getUsername(),
                user.getName(),
                user.getEmail());
//        if (user.getType() == "Teacher" || user.getType() == "Chairman"){
//           queryForUserTable = String.format("INSERT INTO %s VALUES('%s', '%s', '%s')",
//                                            user.getType(),
//                                            user.getUsername(),
//                                            user.getName(),
//                                            user.getEmail());
//        }
//        else {
//            queryForUserTable = String.format("INSERT INTO %s VALUES('%s', '%s', '%s', '', '', '')",
//                    user.getType(),
//                    user.getUsername(),
//                    user.getName(),
//                    user.getEmail());
//        }

        // executing 2 queries
        System.out.println(queryForLoginTable);
        System.out.println(queryForUserTable);
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryForLoginTable);
            statement.executeUpdate(queryForUserTable);
        } catch (SQLException sqle){
            System.out.println("Invalid Credentials.");
            /*
            * This block makes sure if 2nd query fails, 1st query is reverted
            * */
            try {
                String queryForDeletingLogin = String.format("DELETE FROM LOGINS WHERE USERNAME='%s'", user.getUsername());
                Statement statement = connection.createStatement();
                statement.executeUpdate(queryForDeletingLogin);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean verifyUserLogin(LoginInfo logins) {
        /*
        * This query returns count value (supposed to be 1) of username and password matching row in LOGINS table
        * */
        String queryVerification = String.format("SELECT * FROM LOGINS WHERE USERNAME='%s' AND PASSWORD='%s'",
                                            logins.getUsername(),
                                            logins.getPassword());
        
        Connection connection = DBConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryVerification);
            if (resultSet.next()){
                return true; // if there are elements, then the login credentials exist
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // login info does not exist in LOGINS table
    }

    @Override
    public String getUserType(LoginInfo logins) throws SQLException {
        String query = String.format("SELECT USERTYPE FROM LOGINS WHERE USERNAME='%s'",
                logins.getUsername());
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()){
            return resultSet.getString("USERTYPE");
        }
        return null;
    }

    @Override
    public boolean exists(User user) throws SQLException {
        String query = String.format("SELECT USERNAME FROM LOGINS WHERE USERNAME='%s' AND USERTYPE='%s'",
                user.getUsername(),
                user.getType());
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return (resultSet.next());
    }
}
