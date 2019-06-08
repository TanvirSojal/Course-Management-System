package users.dbinterfaces;

import users.LoginInfo;
import users.User;

import java.sql.SQLException;

public interface UserDatabaseOperation {
    // Inserting User in database
    boolean insertUser(User user);
    // Check if User exists in database
    boolean verifyUserLogin(LoginInfo logins);
    String getUserType(LoginInfo logins) throws SQLException;
    boolean exists(User user) throws SQLException;
}
