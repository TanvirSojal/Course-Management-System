package objects.dbinterfaces;

import objects.Deadline;

import java.sql.SQLException;
import java.time.LocalDate;

public interface DeadlineDatabaseOperation {
    boolean insertDeadline(Deadline deadline);
    boolean updateApplicationDeadline(LocalDate date);
    boolean updateEvaluationDeadline(LocalDate date);
    boolean removeAllDeadline();
    LocalDate getApplicationSubmissionDeadline() throws SQLException;
    LocalDate getApplicationEvaluationDeadline() throws SQLException;
}
