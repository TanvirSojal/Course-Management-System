package dboperations;

import dbconnection.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Team;
import objects.dbinterfaces.TeamDatabaseOperation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeamDatabaseOperationImplementation implements TeamDatabaseOperation {

    @Override
    public boolean insertTeam(Team team) {
        String insertQuery = String.format("INSERT INTO TEAM VALUES(%d, '%s', '%s', '%s', '%s'",
                                            team.getTeamId(),
                                            team.getTeamName(),
                                            team.getTeamSupervisorId(),
                                            team.getTeam1stMemberId(),
                                            team.getTeam1stMemberStatus());
        if (team.getTeam2ndMemberId() == null){
            insertQuery += String.format(", NULL, NULL");
        } else {
            insertQuery += String.format(", '%s', '%s'",
                                        team.getTeam2ndMemberId(),
                                        team.getTeam2ndMemberStatus());
        }

        if (team.getTeam3rdMemberId() == null){
            insertQuery += String.format(", NULL, NULL)");
        } else {
            insertQuery += String.format(", '%s', '%s')",
                    team.getTeam3rdMemberId(),
                    team.getTeam3rdMemberStatus());
        }

        System.out.println("Inserting Team: " + insertQuery);
        Connection connection = DBConnection.getConnection();

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteTeam(int teamId) throws SQLException {
        String deleteQuery = String.format("DELETE FROM TEAM WHERE Team_ID=%d",
                                            teamId);
        Connection connection = DBConnection.getConnection();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteQuery);
            return true;
        } catch(SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean studentExists(String studentId) throws SQLException {
        String query = String.format("SELECT Team_ID FROM TEAM, STUDENT WHERE STUDENT.Student_ID = '222'" +
                                    "AND ((TEAM.Team_Member_1_ID = STUDENT.Student_ID AND TEAM.Team_Member_1_Status = 'Confirmed')" +
                                    "OR (TEAM.Team_Member_2_ID = STUDENT.Student_ID AND TEAM.Team_Member_2_Status = 'Confirmed')" +
                                    "OR (TEAM.Team_Member_3_ID = STUDENT.Student_ID AND TEAM.Team_Member_3_Status = 'Confirmed'))",
                                    studentId);

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        return (resultSet.next());
    }

    @Override
    public boolean isTeamLeaderOf(int teamId, String studentId) throws SQLException {
        String query = String.format("SELECT TEAM_ID FROM TEAM, STUDENT WHERE " +
                                    "STUDENT.Student_ID = '%s' AND TEAM.Team_ID=%d AND STUDENT.Student_ID = TEAM.Team_Member_1_ID",
                                    studentId,
                                    teamId);
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return (resultSet.next());
    }

    @Override
    public boolean isMemberOf(int teamId, String studentId) throws SQLException {
        String query = String.format("SELECT TEAM_ID FROM TEAM, STUDENT WHERE " +
                        "STUDENT.Student_ID = '%s' AND TEAM.Team_ID=%d " +
                        "AND (STUDENT.Student_ID = TEAM.Team_Member_1_ID " +
                        "OR STUDENT.Student_ID = TEAM.Team_Member_1_ID " +
                        "OR STUDENT.Student_ID = TEAM.Team_Member_3_ID)",
                studentId,
                teamId);
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return (resultSet.next());
    }

    // Method to check if a student confirmed joining given team_id
    @Override
    public boolean hasConfirmed(int teamId, String studentId) throws SQLException {
        String query = String.format("SELECT Team_ID FROM TEAM, STUDENT WHERE TEAM.Team_ID = %d AND STUDENT.Student_ID = '%s' " +
                "AND ((TEAM.Team_Member_1_ID = STUDENT.Student_ID AND TEAM.Team_Member_1_Status = 'Confirmed') " +
                "OR (TEAM.Team_Member_2_ID = STUDENT.Student_ID AND TEAM.Team_Member_2_Status = 'Confirmed') " +
                "OR (TEAM.Team_Member_3_ID = STUDENT.Student_ID AND TEAM.Team_Member_3_Status = 'Confirmed'))",
                teamId,
                studentId);

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        return (resultSet.next());
    }

    @Override
    public boolean hasConfirmedAll(int teamId) throws SQLException {
        String query = String.format("SELECT Team_Name FROM TEAM WHERE Team_ID = %d " +
                "AND (TEAM.Team_Member_1_ID IS NULL OR Team_Member_1_Status = 'Confirmed') " +
                "AND (TEAM.Team_Member_2_ID IS NULL OR Team_Member_2_Status = 'Confirmed') " +
                "AND (TEAM.Team_Member_3_ID IS NULL OR Team_Member_3_Status = 'Confirmed')",
                teamId);
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet  = statement.executeQuery(query);
        return (resultSet.next());
    }

    @Override
    public boolean rejectTeam(String studentId, Team team) {
        String rejectQuery = "";
        if (studentId.equals(team.getTeam1stMemberId())){
            rejectQuery = String.format("UPDATE TEAM SET Team_Member_1_Status = 'Rejected' WHERE Team_ID = %d",
                                        team.getTeamId());
        }
        else if (studentId.equals(team.getTeam2ndMemberId())){
            rejectQuery = String.format("UPDATE TEAM SET Team_Member_2_Status = 'Rejected' WHERE Team_ID = %d",
                    team.getTeamId());
        }
        else if (studentId.equals(team.getTeam3rdMemberId())){
            rejectQuery = String.format("UPDATE TEAM SET Team_Member_3_Status = 'Rejected' WHERE Team_ID = %d",
                    team.getTeamId());
        }
        else{
            return false;
        }

        Connection connection = DBConnection.getConnection();

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(rejectQuery);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean confirmTeam(String studentId, Team team) {
        String confirmQuery = "";
        if (studentId.equals(team.getTeam1stMemberId())){
            confirmQuery = String.format("UPDATE TEAM SET Team_Member_1_Status = 'Confirmed' WHERE Team_ID = %d",
                    team.getTeamId());
        }
        else if (studentId.equals(team.getTeam2ndMemberId())){
            confirmQuery = String.format("UPDATE TEAM SET Team_Member_2_Status = 'Confirmed' WHERE Team_ID = %d",
                    team.getTeamId());
        }
        else if (studentId.equals(team.getTeam3rdMemberId())){
            confirmQuery = String.format("UPDATE TEAM SET Team_Member_3_Status = 'Confirmed' WHERE Team_ID = %d",
                    team.getTeamId());
        }
        else{
            return false;
        }

        Connection connection = DBConnection.getConnection();

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(confirmQuery);
            return true;
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return false;
    }

    // Returns all the team proposals where student name is mentioned and the status is "Pending"
    // If the student rejects the proposal the status will be "Rejected" and those proposals will not be fetched
    @Override
    public ObservableList<Team> studentMentionedIn(String studentId) throws SQLException {
        String query = String.format("SELECT TEAM.Team_ID, TEAM.Team_Name, TEAM.Team_Supervisor_ID, TEAM.Team_Member_1_ID, TEAM.Team_Member_1_Status, TEAM.Team_Member_2_ID, TEAM.Team_Member_2_Status, TEAM.Team_Member_3_ID, TEAM.Team_Member_3_Status" +
                        " FROM TEAM, STUDENT " +
                        "WHERE STUDENT.Student_ID = '%s' " +
                        "AND (TEAM.Team_Member_1_ID = STUDENT.Student_ID AND TEAM.Team_Member_1_Status!='Rejected'" +
                        "OR TEAM.Team_Member_2_ID = STUDENT.Student_ID AND TEAM.Team_Member_2_Status!='Rejected'" +
                        "OR TEAM.Team_Member_3_ID = STUDENT.Student_ID AND TEAM.Team_Member_3_Status!='Rejected')",
                studentId);

        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ObservableList <Team> teamList = FXCollections.observableArrayList();

        while (resultSet.next()){
            int teamId = resultSet.getInt("Team_ID");
            String teamName = resultSet.getString("Team_Name");
            String teamSuperVisorId = resultSet.getString("Team_Supervisor_ID");
            String team1stMemberId = resultSet.getString("Team_Member_1_ID");
            String team1stMemberStatus = resultSet.getString("Team_Member_1_Status");
            String team2ndMemberId = resultSet.getString("Team_Member_2_ID");
            String team2ndMemberStatus = resultSet.getString("Team_Member_2_Status");
            String team3rdMemberId = resultSet.getString("Team_Member_3_ID");
            String team3rdMemberStatus = resultSet.getString("Team_Member_3_Status");
            Team team = new Team(teamId, teamName, teamSuperVisorId, team1stMemberId, team1stMemberStatus, team2ndMemberId, team2ndMemberStatus, team3rdMemberId, team3rdMemberStatus);
            teamList.add(team);
        }

        return teamList;
    }

    @Override
    public Team getTeam(int teamId) throws SQLException {
        String getQuery = String.format("SELECT * FROM TEAM WHERE Team_ID='%s'",
                                        teamId);
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getQuery);

        if (resultSet.next()){
            String teamName = resultSet.getString("Team_Name");
            String teamSuperVisorId = resultSet.getString("Team_Supervisor_ID");
            String team1stMemberId = resultSet.getString("Team_Member_1_ID");
            String team1stMemberStatus = resultSet.getString("Team_Member_1_Status");
            String team2ndMemberId = resultSet.getString("Team_Member_2_ID");
            String team2ndMemberStatus = resultSet.getString("Team_Member_2_Status");
            String team3rdMemberId = resultSet.getString("Team_Member_3_ID");
            String team3rdMemberStatus = resultSet.getString("Team_Member_3_Status");

            Team team = new Team(teamId, teamName, teamSuperVisorId, team1stMemberId, team1stMemberStatus, team2ndMemberId, team2ndMemberStatus, team3rdMemberId, team3rdMemberStatus);
            return team;
        }
        return null;
    }

    @Override
    public int getMaxPrimaryKey() throws SQLException {
        String getMaxPrimaryKey = String.format("SELECT MAX(Team_ID) FROM TEAM");
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
