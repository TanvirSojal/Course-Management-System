package objects;

import java.sql.SQLException;

public interface RegistrationDatabaseOperation {
    boolean insertRegistrationEntry(Registration registration);
    int getLastPrimaryKey() throws SQLException;
}
