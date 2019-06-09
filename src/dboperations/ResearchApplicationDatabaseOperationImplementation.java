package dboperations;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.ResearchApplication;
import objects.Team;
import objects.dbinterfaces.ResearchApplicationDatabaseOperation;
import objects.dbinterfaces.TeamDatabaseOperation;
import users.Teacher;
import users.dbinterfaces.TeacherDatabaseOperation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public boolean endorseBySupervisor(ResearchApplication researchApplication) {
        String endorseQuery = String.format("UPDATE APPLICATION SET Application_Status='Supervisor_Accepted' WHERE Application_ID=%d",
                                            researchApplication.getApplicationId());

        Connection connection = DBConnection.getConnection();
        //System.out.println(endorseQuery);
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(endorseQuery);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean rejectBySupervisor(ResearchApplication researchApplication) {
        String endorseQuery = String.format("UPDATE APPLICATION SET Application_Status='Supervisor_Rejected' WHERE Application_ID=%d",
                researchApplication.getApplicationId());

        Connection connection = DBConnection.getConnection();
        //System.out.println(endorseQuery);
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(endorseQuery);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean acceptByChairman(ResearchApplication researchApplication) {
        String endorseQuery = String.format("UPDATE APPLICATION SET Application_Status='Chairman_Accepted' WHERE Application_ID=%d",
                researchApplication.getApplicationId());

        Connection connection = DBConnection.getConnection();
        //System.out.println(endorseQuery);
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(endorseQuery);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean rejectByChairman(ResearchApplication researchApplication) {
        String endorseQuery = String.format("UPDATE APPLICATION SET Application_Status='Chairman_Rejected' WHERE Application_ID=%d",
                researchApplication.getApplicationId());

        Connection connection = DBConnection.getConnection();
        //System.out.println(endorseQuery);
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(endorseQuery);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean teamExists(int teamId) throws SQLException {
        String query = String.format("SELECT Application_Title FROM APPLICATION WHERE Application_Team_ID=%d",
                                    teamId);
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return (resultSet.next());
    }

    @Override
    public boolean isEvaluatedBySupervisor(int applicationId) throws SQLException {
        String query = String.format("SELECT Application_Title FROM APPLICATION WHERE Application_ID=%d " +
                                    "AND (Application_Status='Supervisor_Accepted' OR Application_Status='Supervisor_Rejected')",
                                    applicationId);
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return (resultSet.next());
    }

    @Override
    public boolean isEvaluatedByChairman(int applicationId) throws SQLException {
        String query = String.format("SELECT Application_Title FROM APPLICATION WHERE Application_ID=%d " +
                        "AND (Application_Status='Chairman_Accepted' OR Application_Status='Chairman_Rejected')",
                        applicationId);
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return (resultSet.next());
    }

    @Override
    public int getSupervisingStudentCount(String teacherId) throws SQLException {
        ObservableList <ResearchApplication> researchApplicationList = getAllApprovedByChairmanApplications();

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        int studentCount = 0;

        for (ResearchApplication researchApplication : researchApplicationList){
            Team team = teamOp.getTeam(researchApplication.getApplicationTeamId());
            if (team.getTeamSupervisorId().equals(teacherId)){
                studentCount += team.getMemberCount();
            }
        }
        return studentCount;
    }

    @Override
    public int getSupervisingTeamCount(String teacherId) throws SQLException {
        ObservableList <ResearchApplication> researchApplicationList = getAllApprovedByChairmanApplications();

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        int teamCount = 0;

        for (ResearchApplication researchApplication : researchApplicationList){
            Team team = teamOp.getTeam(researchApplication.getApplicationTeamId());
            if (team.getTeamSupervisorId().equals(teacherId)){
                teamCount++;
            }
        }
        return teamCount;
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

    @Override
    public ObservableList<ResearchApplication> getAllMentionedApplications(String teacherId) throws SQLException {
        ObservableList <ResearchApplication> researchApplicationList = getAllApplications();
        ObservableList <ResearchApplication> newList = FXCollections.observableArrayList();

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();

        for (ResearchApplication researchApplication : researchApplicationList){
            int teamId = researchApplication.getApplicationTeamId();
            Team team = teamOp.getTeam(teamId);
            // if the application sending team's supervisor is current teacher, then add to list
            if (team.getTeamSupervisorId().equals(teacherId)){
                newList.add(researchApplication);
            }
        }
        return newList;
    }

    @Override
    public ObservableList<ResearchApplication> getAllEndorsedBySupervisorApplications() throws SQLException {
        ObservableList <ResearchApplication> researchApplicationList = FXCollections.observableArrayList();
        String query = String.format("SELECT * FROM APPLICATION WHERE Application_Status='Supervisor_Accepted' OR Application_Status='Chairman_Accepted' OR Application_Status='Chairman_Rejected'");

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

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
    public ObservableList<ResearchApplication> getAllApprovedByChairmanApplications() throws SQLException {
        ObservableList <ResearchApplication> researchApplicationList = FXCollections.observableArrayList();
        String query = String.format("SELECT * FROM APPLICATION WHERE Application_Status='Chairman_Accepted'");

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);
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
        System.out.println("Accepted"+researchApplicationList.size());
        return researchApplicationList;
    }

    @Override
    public ObservableList<ResearchApplication> getAllApprovedByChairmanApplicationsOf(String teacherId) throws SQLException {
        ObservableList <ResearchApplication> researchApplicationList = FXCollections.observableArrayList();
        String query = String.format("SELECT * FROM APPLICATION WHERE Application_Status='Chairman_Accepted'");

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(query);

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();

        while(resultSet.next()){

            int applicationTeamId = resultSet.getInt("Application_Team_ID");
            // if the supervisor nominated in this application is given Teacher, then add to list
            if (teamOp.getTeam(applicationTeamId).getTeamSupervisorId().equals(teacherId)){
                int applicationId = resultSet.getInt("Application_ID");
                LocalDate applicationDate = resultSet.getDate("Application_Date").toLocalDate();
                String applicationTitle = resultSet.getString("Application_Title");
                int applicationSemesterNeeded = resultSet.getInt("Application_Semester_Needed");
                String applicationHypothesis = resultSet.getString("Application_Hypothesis");
                String applicationComment = resultSet.getString("Application_Comment");

                String applicationStatus = resultSet.getString("Application_Status");

                ResearchApplication researchApplication = new ResearchApplication(applicationId, applicationDate, applicationTitle,
                        applicationSemesterNeeded, applicationHypothesis, applicationComment, applicationTeamId, applicationStatus);
                researchApplicationList.add(researchApplication);
            }
        }
        System.out.println("Accepted"+researchApplicationList.size());
        return researchApplicationList;
    }

    @Override
    public ObservableList<Teacher> getAllMentionedTeachers() throws SQLException {
        String query = String.format("SELECT (DISTINCT Team_ID) FROM APPLICATION");
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        TeacherDatabaseOperation teacherOp = new TeacherDatabaseOperationImplementation();

        ObservableList <Teacher> teacherList = FXCollections.observableArrayList();

        while(resultSet.next()){
            int teamId = resultSet.getInt("Application_Team_ID");
            Team team = teamOp.getTeam(teamId);
            Teacher teacher = teacherOp.getTeacher(team.getTeamSupervisorId());
            teacherList.add(teacher);
        }
        return teacherList;
    }

    @Override
    public ObservableList<Teacher> getAllMentionedTeachersOfUnevaluatedApplications() throws SQLException {
        String query = String.format("SELECT DISTINCT Application_Team_ID FROM APPLICATION WHERE Application_Status='Pending'");
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        TeacherDatabaseOperation teacherOp = new TeacherDatabaseOperationImplementation();

        List <Teacher> teacherList = new ArrayList<>();

        while(resultSet.next()){
            int teamId = resultSet.getInt("Application_Team_ID");
            Team team = teamOp.getTeam(teamId);
            Teacher teacher = teacherOp.getTeacher(team.getTeamSupervisorId());
            teacherList.add(teacher);
        }

        teacherList = teacherList.stream().distinct().collect(Collectors.toList()); // removing duplicates
        System.out.println("New " + FXCollections.observableArrayList(teacherList).size());
        return FXCollections.observableArrayList(teacherList);
    }
}
