package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static  final String HOSTNAME = "localhost:3306";
    private static final String DBNAME = "course_management";
    private static final String URL = "jdbc:mysql://" + HOSTNAME + "/" + DBNAME;

    private static Connection connection = null;
    private static final DBConnection INSTANCE = new DBConnection();

    // private constructor to create a connection once
    private DBConnection(){
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to database.");
        } catch (SQLException sqle){
            System.out.println("Error connecting to database!");
            sqle.printStackTrace();
        }
    }

    // public method to get connection to database
    public static Connection getConnection(){
        return connection;
    }
}
