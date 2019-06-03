package users;

public interface UserDatabaseOperation {
    // Inserting User in database
    boolean insertUser(User user);
    // Check if User exists in database
    boolean verifyUserLogin(LoginInfo logins);
}
