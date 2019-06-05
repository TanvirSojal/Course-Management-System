package controllers;

import dboperations.UserDatabaseOperationImplementation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import users.LoginInfo;
import users.User;
import users.UserDatabaseOperation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginUIController implements Initializable {

    @FXML
    private TextField signupUsernameField;
    @FXML
    private PasswordField signupPasswordField;
    @FXML
    private ComboBox <String> signupUserTypeComboBox;

    private ObservableList < String > userTypes;
    @FXML
    private Label signupStatusLabel;
    @FXML
    private TextField loginUsernameField;
    @FXML
    private PasswordField loginPasswordField;
    @FXML
    private Label loginStatusLabel;
    @FXML
    private TextField signupEmailField;
    @FXML
    private TextField signupNameField;

    private static String passableUsername; // it has getter method

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userTypes = FXCollections.observableArrayList("Chairman", "Teacher", "Student");
        signupUserTypeComboBox.setItems(userTypes);
        signupUserTypeComboBox.getSelectionModel().selectLast();
    }

    @FXML
    private void handleSignUpResetButton(javafx.event.ActionEvent actionEvent) {
        signupNameField.clear();
        signupEmailField.clear();
        signupUsernameField.clear();
        signupPasswordField.clear();
        signupStatusLabel.setText(null);
        signupUserTypeComboBox.getSelectionModel().selectLast();
    }

    @FXML
    private void handleSignUpButton(javafx.event.ActionEvent actionEvent) {
        /*
        * Getting all the values from textfields and combobox if "Signup" page
        * */
        String username = signupUsernameField.getText();
        String password = signupPasswordField.getText();
        String userType = signupUserTypeComboBox.getValue();
        String name = signupNameField.getText();
        String email = signupEmailField.getText();

        // If the required fields are not empty, then we insert into database
        if (!username.isEmpty()  && !password.isEmpty() && userType != null){
            signupStatusLabel.setText(null); // remove any previous error message
            User user = new User(name, email, username, password, userType);
            UserDatabaseOperation userops = new UserDatabaseOperationImplementation();
            if (userops.insertUser(user)){
                signupStatusLabel.setText("Signed Up successfully!");
            }
            else{
                signupStatusLabel.setText("Sign Up failed!");
            }
        }
        else{
            System.out.println("User insertion error!");
            signupStatusLabel.setText("Invalid Credentials/Required Field(s) are empty.");
        }
    }

    @FXML
    private void handleLoginButton(ActionEvent actionEvent) {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();

        if (!username.isEmpty() && !password.isEmpty()){
            loginStatusLabel.setText(null);
            //User user = new User(name, email, username, password, "temp");
            LoginInfo logins = new LoginInfo(username, password);
            UserDatabaseOperation userops = new UserDatabaseOperationImplementation();
            /*
            * If login credentials are valid, we change scene to corresponding user's dashboard
            * */
            if (userops.verifyUserLogin(logins)){
                System.out.println("Verified");
                passableUsername = logins.getUsername();

                try {
                    Parent StudentDashboardParent = FXMLLoader.load(getClass().getResource("/gui/StudentDashboard.fxml"));
                    Scene StudentDashboardScene = new Scene(StudentDashboardParent);

                    Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    window.setScene(StudentDashboardScene);
                    window.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("User insertion error!");
                loginStatusLabel.setText("Invalid credentials!");
            }
        }
        else{
            System.out.println("Empty fields!");
            loginStatusLabel.setText("Required fields can not be empty!");
        }
    }

    @FXML
    private void handleLoginResetButton(ActionEvent actionEvent) {
        loginUsernameField.clear();
        loginPasswordField.clear();
    }

    public static String getUsername(){
        return passableUsername;
    }
}
