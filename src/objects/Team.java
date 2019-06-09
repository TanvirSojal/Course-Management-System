package objects;

public class Team {
    private int teamId;
    private String teamName;
    private String teamSupervisorId;
    private String team1stMemberId;
    private String team1stMemberStatus;
    private String team2ndMemberId;
    private String team2ndMemberStatus;
    private String team3rdMemberId;
    private String team3rdMemberStatus;

    public Team(int teamId, String teamName, String teamSupervisorId, String team1stMemberId, String team1stMemberStatus, String team2ndMemberId, String team2ndMemberStatus, String team3rdMemberId, String team3rdMemberStatus) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamSupervisorId = teamSupervisorId;
        this.team1stMemberId = team1stMemberId;
        this.team1stMemberStatus = team1stMemberStatus;
        this.team2ndMemberId = team2ndMemberId;
        this.team2ndMemberStatus = team2ndMemberStatus;
        this.team3rdMemberId = team3rdMemberId;
        this.team3rdMemberStatus = team3rdMemberStatus;
    }

    public Team(Team that){
        this.teamId = that.getTeamId();
        this.teamName = that.getTeamName();
        this.teamSupervisorId = that.getTeamSupervisorId();
        this.team1stMemberId = that.getTeam1stMemberId();
        this.team1stMemberStatus = that.getTeam1stMemberStatus();
        this.team2ndMemberId = that.getTeam2ndMemberId();
        this.team2ndMemberStatus = that.getTeam2ndMemberStatus();
        this.team3rdMemberId = that.getTeam3rdMemberId();
        this.team3rdMemberStatus = that.getTeam3rdMemberStatus();
    }

    public Team(TeamProposal that){
        this.teamId = that.getTeamId();
        this.teamName = that.getTeamName();
        this.teamSupervisorId = that.getTeamSupervisorId();
        this.team1stMemberId = that.getTeam1stMemberId();
        this.team1stMemberStatus = that.getTeam1stMemberStatus();
        this.team2ndMemberId = that.getTeam2ndMemberId();
        this.team2ndMemberStatus = that.getTeam2ndMemberStatus();
        this.team3rdMemberId = that.getTeam3rdMemberId();
        this.team3rdMemberStatus = that.getTeam3rdMemberStatus();
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

    public String getTeam1stMemberStatus() {
        return team1stMemberStatus;
    }

    public String getTeam2ndMemberId() {
        return team2ndMemberId;
    }

    public void setTeam1stMemberStatus(String team1stMemberStatus) {
        this.team1stMemberStatus = team1stMemberStatus;
    }

    public void setTeam2ndMemberId(String team2ndMemberId) {
        this.team2ndMemberId = team2ndMemberId;
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

    public String getTeam3rdMemberStatus() {
        return team3rdMemberStatus;
    }

    public void setTeam3rdMemberStatus(String team3rdMemberStatus) {
        this.team3rdMemberStatus = team3rdMemberStatus;
    }

    public int getMemberCount() {
        int memberCount = 0;
        if (team1stMemberId != null)
            memberCount++;
        if (team2ndMemberId != null)
            memberCount++;
        if (team3rdMemberId != null)
            memberCount++;
        return memberCount;
    }

    @Override
    public String toString() {

        return String.format("(%d) : %s : %d Member(s) : %s", this.teamId, this.teamName, this.getMemberCount(), this.teamSupervisorId);
    }
}
