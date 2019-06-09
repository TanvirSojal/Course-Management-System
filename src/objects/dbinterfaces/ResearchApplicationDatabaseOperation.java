package objects.dbinterfaces;

import javafx.collections.ObservableList;
import objects.ResearchApplication;
import objects.Team;
import users.Teacher;

import java.sql.SQLException;

public interface ResearchApplicationDatabaseOperation {
    int getMaxPrimaryKey() throws SQLException;
    boolean insertResearchApplication(ResearchApplication researchApplication);
    boolean endorseBySupervisor(ResearchApplication researchApplication);
    boolean rejectBySupervisor(ResearchApplication researchApplication);
    boolean acceptByChairman(ResearchApplication researchApplication);
    boolean rejectByChairman(ResearchApplication researchApplication);
    boolean teamExists(int teamId) throws SQLException;
    boolean isEvaluatedBySupervisor(int applicationId) throws SQLException;
    boolean isEvaluatedByChairman(int applicationId) throws SQLException;
    int getSupervisingStudentCount(String teacherId) throws SQLException;
    int getSupervisingTeamCount(String teacherId) throws SQLException;
    ObservableList <ResearchApplication> getAllApplications() throws SQLException;
    ObservableList <ResearchApplication> getAllApplicationsOf(String studentId) throws SQLException;
    ObservableList <ResearchApplication> getAllMentionedApplications(String teacherId) throws SQLException;
    ObservableList <ResearchApplication> getAllEndorsedBySupervisorApplications() throws SQLException;
    ObservableList <ResearchApplication> getAllApprovedByChairmanApplications() throws SQLException;
    ObservableList <ResearchApplication> getAllApprovedByChairmanApplicationsOf(String teacherId) throws SQLException;
    ObservableList <Teacher> getAllMentionedTeachers() throws SQLException;
    ObservableList <Teacher> getAllMentionedTeachersOfUnevaluatedApplications() throws SQLException;
}
