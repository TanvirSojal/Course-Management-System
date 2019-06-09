package dboperations;

import dbconnection.DBConnection;
import objects.Deadline;
import objects.dbinterfaces.DeadlineDatabaseOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DeadlineDatabaseOperationImplementation implements DeadlineDatabaseOperation {

    @Override
    public boolean insertDeadline(Deadline deadline) {
        String query = String.format("INSERT INTO DEADLINE(Deadline_Topic, Deadline_Date) VALUES('%s', '%s')",
                                    deadline.getDeadlineTopic(),
                                    deadline.getDeadlineDate());
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateApplicationDeadline(LocalDate date) {
        String query = String.format("UPDATE DEADLINE SET Deadline_Date='%s' WHERE Deadline_Topic='Application_Submission'", date);
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEvaluationDeadline(LocalDate date) {
        String query = String.format("UPDATE DEADLINE SET Deadline_Date='%s' WHERE Deadline_Topic='Application_Evaluation'", date);
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeAllDeadline() {
        String query = String.format("DELETE FROM DEADLINE");
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public LocalDate getApplicationSubmissionDeadline() throws SQLException {
        String query = String.format("SELECT Deadline_Date FROM DEADLINE WHERE Deadline_Topic='Application_Submission'");
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next())
            return (LocalDate.parse(resultSet.getString("Deadline_Date")));
        return null;
    }

    @Override
    public LocalDate getApplicationEvaluationDeadline() throws SQLException {
        String query = String.format("SELECT Deadline_Date FROM DEADLINE WHERE Deadline_Topic='Application_Evaluation'");
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next())
            return (LocalDate.parse(resultSet.getString("Deadline_Date")));
        return null;
    }
}
