package objects.dbinterfaces;

import javafx.collections.ObservableList;
import objects.ResearchApplication;

import java.sql.SQLException;

public interface ResearchApplicationDatabaseOperation {
    int getMaxPrimaryKey() throws SQLException;
    boolean insertResearchApplication(ResearchApplication researchApplication);
    ObservableList <ResearchApplication> getAllApplications() throws SQLException;
    ObservableList <ResearchApplication> getAllApplicationsOf(String studentId) throws SQLException;
}
