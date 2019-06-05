package objects;

import dboperations.StudentDatabaseOperationImplementation;
import users.StudentDatabaseOperation;

import java.sql.SQLException;

public class TeamProposal {
    private int teamId;
    private String teamName;
    private String teamSupervisorId;
    private String team1stMemberId;
    private String team1stMemberName;
    private String team1stMemberStatus;
    private String team2ndMemberId;
    private String team2ndMemberName;
    private String team2ndMemberStatus;
    private String team3rdMemberId;
    private String team3rdMemberName;
    private String team3rdMemberStatus;

    public TeamProposal(int teamId, String teamName, String teamSupervisorId, String team1stMemberId, String team1stMemberName, String team1stMemberStatus, String team2ndMemberId, String team2ndMemberName, String team2ndMemberStatus, String team3rdMemberId, String team3rdMemberName, String team3rdMemberStatus) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamSupervisorId = teamSupervisorId;
        this.team1stMemberId = team1stMemberId;
        this.team1stMemberName = team1stMemberName;
        this.team1stMemberStatus = team1stMemberStatus;
        this.team2ndMemberId = team2ndMemberId;
        this.team2ndMemberName = team2ndMemberName;
        this.team2ndMemberStatus = team2ndMemberStatus;
        this.team3rdMemberId = team3rdMemberId;
        this.team3rdMemberName = team3rdMemberName;
        this.team3rdMemberStatus = team3rdMemberStatus;
    }

    public TeamProposal(Team that) throws SQLException {
        this.teamId = that.getTeamId();
        this.teamName = that.getTeamName();
        this.teamSupervisorId = that.getTeamSupervisorId();
        this.team1stMemberId = that.getTeam1stMemberId();
        this.team1stMemberStatus = that.getTeam1stMemberStatus();
        this.team2ndMemberId = that.getTeam2ndMemberId();
        this.team2ndMemberStatus = that.getTeam2ndMemberStatus();
        this.team3rdMemberId = that.getTeam3rdMemberId();
        this.team3rdMemberStatus = that.getTeam3rdMemberStatus();

        StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();
        this.team1stMemberName = studentOp.getStudent(team1stMemberId).getStudentName();
        this.team2ndMemberName = studentOp.getStudent(team2ndMemberId).getStudentName();
        this.team3rdMemberName = studentOp.getStudent(team3rdMemberId).getStudentName();
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamSupervisorId() {
        return teamSupervisorId;
    }

    public void setTeamSupervisorId(String teamSupervisorId) {
        this.teamSupervisorId = teamSupervisorId;
    }

    public String getTeam1stMemberId() {
        return team1stMemberId;
    }

    public void setTeam1stMemberId(String team1stMemberId) {
        this.team1stMemberId = team1stMemberId;
    }

    public String getTeam1stMemberName() {
        return team1stMemberName;
    }

    public void setTeam1stMemberName(String team1stMemberName) {
        this.team1stMemberName = team1stMemberName;
    }

    public String getTeam1stMemberStatus() {
        return team1stMemberStatus;
    }

    public void setTeam1stMemberStatus(String team1stMemberStatus) {
        this.team1stMemberStatus = team1stMemberStatus;
    }

    public String getTeam2ndMemberId() {
        return team2ndMemberId;
    }

    public void setTeam2ndMemberId(String team2ndMemberId) {
        this.team2ndMemberId = team2ndMemberId;
    }

    public String getTeam2ndMemberName() {
        return team2ndMemberName;
    }

    public void setTeam2ndMemberName(String team2ndMemberName) {
        this.team2ndMemberName = team2ndMemberName;
    }

    public String getTeam2ndMemberStatus() {
        return team2ndMemberStatus;
    }

    public void setTeam2ndMemberStatus(String team2ndMemberStatus) {
        this.team2ndMemberStatus = team2ndMemberStatus;
    }

    public String getTeam3rdMemberId() {
        return team3rdMemberId;
    }

    public void setTeam3rdMemberId(String team3rdMemberId) {
        this.team3rdMemberId = team3rdMemberId;
    }

    public String getTeam3rdMemberName() {
        return team3rdMemberName;
    }

    public void setTeam3rdMemberName(String team3rdMemberName) {
        this.team3rdMemberName = team3rdMemberName;
    }

    public String getTeam3rdMemberStatus() {
        return team3rdMemberStatus;
    }

    public void setTeam3rdMemberStatus(String team3rdMemberStatus) {
        this.team3rdMemberStatus = team3rdMemberStatus;
    }

    @Override
    public String toString() {
        return "TeamProposal{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamSupervisorId='" + teamSupervisorId + '\'' +
                ", team1stMemberId='" + team1stMemberId + '\'' +
                ", team1stMemberName='" + team1stMemberName + '\'' +
                ", team1stMemberStatus='" + team1stMemberStatus + '\'' +
                ", team2ndMemberId='" + team2ndMemberId + '\'' +
                ", team2ndMemberName='" + team2ndMemberName + '\'' +
                ", team2ndMemberStatus='" + team2ndMemberStatus + '\'' +
                ", team3rdMemberId='" + team3rdMemberId + '\'' +
                ", team3rdMemberName='" + team3rdMemberName + '\'' +
                ", team3rdMemberStatus='" + team3rdMemberStatus + '\'' +
                '}';
    }
}
