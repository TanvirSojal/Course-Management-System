package users.dbinterfaces;

import javafx.collections.ObservableList;
import users.Chairman;

import java.sql.SQLException;

public interface ChairmanDatabaseOperation {
    //ObservableList<Chairman> getAllTeachers() throws SQLException;
    Chairman getChairman(String chairmanId) throws SQLException;
    boolean updateChairmanEmail(String chairmanId, String email);
    boolean updateChairmanBloodGroup(String chairmanId, String bloodGroup);
    boolean updateChairmanContactNumber(String chairmanId, String contactNumber);
    boolean updateChairmanAddress(String chairmanId, String address);
}
