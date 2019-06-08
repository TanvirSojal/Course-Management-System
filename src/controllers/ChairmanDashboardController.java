package controllers;

import dboperations.ChairmanDatabaseOperationImplementation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import users.Chairman;
import users.dbinterfaces.ChairmanDatabaseOperation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChairmanDashboardController implements Initializable {
    private Chairman chairman; // currently logged in Chairman

    @FXML
    private AnchorPane chairmanDashboardHomePane;
    @FXML
    private AnchorPane chairmanInformationPane;
    @FXML
    private Label chairmanInformationUpdationStatusLabel;
    @FXML
    private Label chairmanInformationNameLabel;
    @FXML
    private Label chairmanInformationIdLabel;
    @FXML
    private Label chairmanInformationOfficeLabel;
    @FXML
    private TextField chairmanInformationEmailField;
    @FXML
    private TextField chairmanInformationBloodGroupField;
    @FXML
    private TextField chairmanInformationContactNumberField;
    @FXML
    private TextField chairmanInformationAddressField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigateToChairmanDashboard(null); // making default page
        try{
            fetchChairmanInformationFromDatabase();

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    @FXML
    private void handleDashboardSignout(ActionEvent actionEvent) throws IOException {
        /*
         * Signing out will take user to login page
         * */
        Parent LoginUIParent = FXMLLoader.load(getClass().getResource("/gui/LoginUI.fxml"));
        Scene LoginUIScene = new Scene(LoginUIParent);

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(LoginUIScene);
        window.show();
    }

    private void fetchChairmanInformationFromDatabase() throws SQLException {
        String chairmanId = LoginUIController.getUsername();
        ChairmanDatabaseOperation teacherOp = new ChairmanDatabaseOperationImplementation();
        chairman = teacherOp.getChairman(chairmanId);

        chairmanInformationNameLabel.setText(chairman.getChairmanName());
        chairmanInformationIdLabel.setText(chairman.getChairmanInitial());
        chairmanInformationOfficeLabel.setText("Department of CSE, QV University");

        chairmanInformationEmailField.setText(chairman.getChairmanEmail());
        chairmanInformationBloodGroupField.setText(chairman.getChairmanBloodGroup());
        chairmanInformationContactNumberField.setText(chairman.getChairmanContactNumber());
        chairmanInformationAddressField.setText(chairman.getChairmanAddress());
    }

    @FXML
    private void navigateToChairmanDashboard(ActionEvent actionEvent) {
        chairmanInformationPane.setVisible(false);

        chairmanDashboardHomePane.setVisible(true);
    }

    @FXML
    private void navigateToChairmanInformation(ActionEvent actionEvent) {
        chairmanInformationUpdationStatusLabel.setText(null);
        chairmanDashboardHomePane.setVisible(false);

        chairmanInformationPane.setVisible(true);
    }

    @FXML
    private void handleChairmanInformationUpdate(ActionEvent actionEvent) {
        String newEmail = chairmanInformationEmailField.getText();
        String newBloodGroup = chairmanInformationBloodGroupField.getText();
        String newContactNumber = chairmanInformationContactNumberField.getText();
        String newAddress = chairmanInformationAddressField.getText();

        ChairmanDatabaseOperation chairmanOp = new ChairmanDatabaseOperationImplementation();

        // flag variables to check if update operations were successful
        boolean emailStatus;
        boolean bloodGroupStatus;
        boolean contactNumberStatus;
        boolean addressStatus;

        emailStatus = chairmanOp.updateChairmanEmail(chairman.getChairmanInitial(), newEmail);
        // If the database operation is performed, we update the student object
        if (emailStatus)
            this.chairman.setChairmanEmail(newEmail);

        bloodGroupStatus = chairmanOp.updateChairmanBloodGroup(chairman.getChairmanInitial(), newBloodGroup);
        // If the database operation is performed, we update the student object
        if (bloodGroupStatus)
            this.chairman.setChairmanBloodGroup(newBloodGroup);

        contactNumberStatus = chairmanOp.updateChairmanContactNumber(chairman.getChairmanInitial(), newContactNumber);
        // If the database operation is performed, we update the student object
        if (contactNumberStatus)
            this.chairman.setChairmanContactNumber(newContactNumber);


        addressStatus = chairmanOp.updateChairmanAddress(chairman.getChairmanInitial(), newAddress);
        // If the database operation is performed, we update the student object
        if (addressStatus)
            this.chairman.setChairmanAddress(newAddress);


        // if all status flags are true then we conclude that the update operations are successful
        if (emailStatus && bloodGroupStatus && contactNumberStatus && addressStatus) {
            //System.out.println("Email updated successfully.");
            chairmanInformationUpdationStatusLabel.setText("Information updated successfully.");
        } else {
            //System.out.println("Updation error!");
            chairmanInformationUpdationStatusLabel.setText("Error updating information. Please recheck!");
        }
    }


}
