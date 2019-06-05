package objects;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface TeamDatabaseOperation {
    boolean insertTeam(Team team);
    boolean deleteTeam(int teamId) throws SQLException;
    boolean studentExists(String studentId) throws SQLException;
    boolean isTeamLeader(String studentId) throws SQLException;
    boolean hasConfirmed(int teamId, String studentId) throws SQLException;
    ObservableList <Team> studentMentionedIn(String studentId) throws SQLException;
    Team getTeam(int teamId) throws SQLException;
    int getLastPrimaryKey() throws SQLException;
}
