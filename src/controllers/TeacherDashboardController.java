package controllers;

import dboperations.TeacherDatabaseOperationImplementation;
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
import users.Teacher;
import users.dbinterfaces.TeacherDatabaseOperation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class TeacherDashboardController implements Initializable {

    private Teacher teacher; // currently logged in Teacher

    @FXML
    private Label teacherNameLabel;
    @FXML
    private Label teacherIdLabel;
    @FXML
    private AnchorPane teacherDashboardHomePane;
    @FXML
    private AnchorPane teacherInformationPane;
    @FXML
    private Label teacherInformationNameLabel;
    @FXML
    private Label teacherInformationIdLabel;
    @FXML
    private TextField teacherInformationEmailField;
    @FXML
    private TextField teacherInformationBloodGroupField;
    @FXML
    private TextField teacherInformationContactNumberField;
    @FXML
    private TextField teacherInformationAddressField;
    @FXML
    private Label teacherInformationUpdationStatusLabel;
    @FXML
    private Label teacherInformationOfficeLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigateToTeacherDashboard(null); // setting "Teacher Dashboard" the default page
        try{
            // fetching data from database
            fetchTeacherInformationFromDatabase();
            System.out.println(teacher);
            // populating GUI elements
            populateTeacherInformationBar();
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

    private void fetchTeacherInformationFromDatabase() throws SQLException {
        String teacherId = LoginUIController.getUsername();
        TeacherDatabaseOperation teacherOp = new TeacherDatabaseOperationImplementation();
        teacher = teacherOp.getTeacher(teacherId);

        teacherInformationNameLabel.setText(teacher.getTeacherName());
        teacherInformationIdLabel.setText(teacher.getTeacherInitial());
        teacherInformationOfficeLabel.setText("Department of CSE, QV University");

        teacherInformationEmailField.setText(teacher.getTeacherEmail());
        teacherInformationBloodGroupField.setText(teacher.getTeacherBloodGroup());
        teacherInformationContactNumberField.setText(teacher.getTeacherContactNumber());
        teacherInformationAddressField.setText(teacher.getTeacherAddress());
    }

    @FXML
    private void populateTeacherInformationBar(){
        teacherIdLabel.setText(teacher.getTeacherInitial());
        teacherNameLabel.setText(teacher.getTeacherName());
    }


    /***************************************
    *  Navigation related method are below
    ****************************************/
    @FXML
    private void navigateToTeacherDashboard(ActionEvent actionEvent) {
        teacherInformationPane.setVisible(false);

        teacherDashboardHomePane.setVisible(true);
    }

    @FXML
    private void navigateToTeacherInformation(ActionEvent actionEvent) {
        teacherInformationUpdationStatusLabel.setText(null);
        teacherDashboardHomePane.setVisible(false);

        teacherInformationPane.setVisible(true);
    }

    @FXML
    private void handleTeacherInformationUpdate(ActionEvent actionEvent) {
        String newEmail = teacherInformationEmailField.getText();
        String newBloodGroup = teacherInformationBloodGroupField.getText();
        String newContactNumber = teacherInformationContactNumberField.getText();
        String newAddress = teacherInformationAddressField.getText();

        TeacherDatabaseOperation teacherOp = new TeacherDatabaseOperationImplementation();

        // flag variables to check if update operations were successful
        boolean emailStatus;
        boolean bloodGroupStatus;
        boolean contactNumberStatus;
        boolean addressStatus;

        emailStatus = teacherOp.updateTeacherEmail(teacher.getTeacherInitial(), newEmail);
        // If the database operation is performed, we update the student object
        if (emailStatus)
            this.teacher.setTeacherEmail(newEmail);

        bloodGroupStatus = teacherOp.updateTeacherBloodGroup(teacher.getTeacherInitial(), newBloodGroup);
        // If the database operation is performed, we update the student object
        if (bloodGroupStatus)
            this.teacher.setTeacherBloodGroup(newBloodGroup);

        contactNumberStatus = teacherOp.updateTeacherContactNumber(teacher.getTeacherInitial(), newContactNumber);
        // If the database operation is performed, we update the student object
        if (contactNumberStatus)
            this.teacher.setTeacherContactNumber(newContactNumber);


        addressStatus = teacherOp.updateTeacherAddress(teacher.getTeacherInitial(), newAddress);
        // If the database operation is performed, we update the student object
        if (addressStatus)
            this.teacher.setTeacherAddress(newAddress);


        // if all status flags are true then we conclude that the update operations are successful
        if (emailStatus && bloodGroupStatus && contactNumberStatus && addressStatus) {
            //System.out.println("Email updated successfully.");
            teacherInformationUpdationStatusLabel.setText("Information updated successfully.");
        } else {
            //System.out.println("Updation error!");
            teacherInformationUpdationStatusLabel.setText("Error updating information. Please recheck!");
        }
    }
}
