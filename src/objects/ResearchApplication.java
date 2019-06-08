package objects;

import java.time.LocalDate;

public class ResearchApplication {
    private int applicationId;
    private LocalDate applicationDate;
    private String applicationTitle;
    private int applicationSemesterNeeded;
    private String applicationHypothesis;
    private String applicationComment;
    private int applicationTeamId;
    private String applicationStatus;

    public ResearchApplication(int applicationId, LocalDate applicationDate, String applicationTitle, int applicationSemesterNeeded, String applicationHypothesis, String applicationComment, int applicationTeamId, String applicationStatus) {
        this.applicationId = applicationId;
        this.applicationDate = applicationDate;
        this.applicationTitle = applicationTitle;
        this.applicationSemesterNeeded = applicationSemesterNeeded;
        this.applicationHypothesis = applicationHypothesis;
        this.applicationComment = applicationComment;
        this.applicationTeamId = applicationTeamId;
        this.applicationStatus = applicationStatus;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicationTitle() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    public int getApplicationSemesterNeeded() {
        return applicationSemesterNeeded;
    }

    public void setApplicationSemesterNeeded(int applicationSemesterNeeded) {
        this.applicationSemesterNeeded = applicationSemesterNeeded;
    }

    public String getApplicationHypothesis() {
        return applicationHypothesis;
    }

    public void setApplicationHypothesis(String applicationHypothesis) {
        this.applicationHypothesis = applicationHypothesis;
    }

    public String getApplicationComment() {
        return applicationComment;
    }

    public void setApplicationComment(String applicationComment) {
        this.applicationComment = applicationComment;
    }

    public int getApplicationTeamId() {
        return applicationTeamId;
    }

    public void setApplicationTeamId(int applicationTeamId) {
        this.applicationTeamId = applicationTeamId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public String toString() {
        return "ResearchApplication{" +
                "applicationId=" + applicationId +
                ", applicationDate=" + applicationDate +
                ", applicationTitle='" + applicationTitle + '\'' +
                ", applicationSemesterNeeded=" + applicationSemesterNeeded +
                ", applicationHypothesis='" + applicationHypothesis + '\'' +
                ", applicationComment='" + applicationComment + '\'' +
                ", applicationTeamId=" + applicationTeamId +
                ", applicationStatus='" + applicationStatus + '\'' +
                '}';
    }
}
