package dboperations;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.ResearchApplication;
import objects.dbinterfaces.ResearchApplicationDatabaseOperation;
import objects.dbinterfaces.TeamDatabaseOperation;

import java.sql.*;
import java.time.LocalDate;

public class ResearchApplicationDatabaseOperationImplementation implements ResearchApplicationDatabaseOperation {
    @Override
    public int getMaxPrimaryKey() throws SQLException {
        String getMaxPrimaryKey = String.format("SELECT MAX(Application_ID) FROM APPLICATION");
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
    public boolean insertResearchApplication(ResearchApplication researchApplication) {
        //String insertQuery = String.format("INSERT INTO APPLICATION(Application_ID, Application_Date, Application_Title, Application_Semester_Needed, Application_Hypothesis, Application_Comment, Application_Team_ID, Application_Status) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        String insertQuery = String.format("INSERT INTO APPLICATION VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

        Connection connection = DBConnection.getConnection();
        System.out.println(insertQuery);
        try{
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setInt(1, researchApplication.getApplicationId());
            statement.setString(2, researchApplication.getApplicationDate().toString());
            statement.setString(3, researchApplication.getApplicationTitle());
            statement.setInt(4, researchApplication.getApplicationSemesterNeeded());

            if (researchApplication.getApplicationHypothesis() == null || researchApplication.getApplicationHypothesis().length() != 0)
                statement.setString(5, researchApplication.getApplicationHypothesis());
            else
                statement.setNull(5, Types.VARCHAR);

            if (researchApplication.getApplicationComment() == null || researchApplication.getApplicationComment().length() != 0)
                statement.setString(6, researchApplication.getApplicationComment());
            else
                statement.setNull(6, Types.VARCHAR);

            statement.setInt(7, researchApplication.getApplicationTeamId());
            statement.setString(8, researchApplication.getApplicationStatus());
            //statement.setString(3, );
            System.out.println(statement);
            statement.executeUpdate();
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public ObservableList<ResearchApplication> getAllApplications() throws SQLException {

        String getQuery = String.format("SELECT * FROM APPLICATION");
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getQuery);

        ObservableList <ResearchApplication> researchApplicationList = FXCollections.observableArrayList();
        while(resultSet.next()){
            int applicationId = resultSet.getInt("Application_ID");
            LocalDate applicationDate = resultSet.getDate("Application_Date").toLocalDate();
            String applicationTitle = resultSet.getString("Application_Title");
            int applicationSemesterNeeded = resultSet.getInt("Application_Semester_Needed");
            String applicationHypothesis = resultSet.getString("Application_Hypothesis");
            String applicationComment = resultSet.getString("Application_Comment");
            int applicationTeamId = resultSet.getInt("Application_Team_ID");
            String applicationStatus = resultSet.getString("Application_Status");

            ResearchApplication researchApplication = new ResearchApplication(applicationId, applicationDate, applicationTitle,
                    applicationSemesterNeeded, applicationHypothesis, applicationComment, applicationTeamId, applicationStatus);
            researchApplicationList.add(researchApplication);
        }
        return researchApplicationList;
    }

    @Override
    public ObservableList<ResearchApplication> getAllApplicationsOf(String studentId) throws SQLException {

        ObservableList <ResearchApplication> researchApplicationList = getAllApplications();
        ObservableList <ResearchApplication> newList = FXCollections.observableArrayList();

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();

        for (ResearchApplication researchApplication : researchApplicationList){
            int teamId = researchApplication.getApplicationTeamId();
            if (teamOp.isMemberOf(teamId, studentId)){
                newList.add(researchApplication);
            }
        }
        return newList;
    }
}
