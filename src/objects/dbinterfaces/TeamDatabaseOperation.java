package objects.dbinterfaces;

import javafx.collections.ObservableList;
import objects.Team;

import java.sql.SQLException;

public interface TeamDatabaseOperation {
    boolean insertTeam(Team team);
    boolean deleteTeam(int teamId) throws SQLException;
    boolean studentExists(String studentId) throws SQLException;
    boolean isTeamLeaderOf(int teamId, String studentId) throws SQLException;
    boolean isMemberOf(int teamId, String studentId) throws SQLException;
    boolean hasConfirmed(int teamId, String studentId) throws SQLException;
    boolean hasConfirmedAll(int teamId) throws SQLException;
    boolean rejectTeam(String studentId, Team team);
    boolean confirmTeam(String studentId, Team team);
    ObservableList <Team> studentMentionedIn(String studentId) throws SQLException;
    Team getTeam(int teamId) throws SQLException;
    int getMaxPrimaryKey() throws SQLException;
}
