package controllers;

import dboperations.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.*;
import users.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {

    @FXML
    private Label studentNameLabel;
    @FXML
    private Label studentIdLabel;
    @FXML
    private AnchorPane studentDashboardHomePane;
    @FXML
    private AnchorPane studentInformationPane;
    @FXML
    private TextField studentInformationEmailField;
    @FXML
    private TextField studentInformationBloodGroupField;
    @FXML
    private TextField studentInformationContactNumberField;
    @FXML
    private TextField studentInformationAddressField;

    /*
    * Following are the data of the student fetched from database
    * */

    private Student student;
    private Team currentTeam;

    @FXML
    private Label studentInformationUpdationStatusLabel;
    @FXML
    private Label studentInformationNameLabel;
    @FXML
    private Label studentInformationIdLabel;
    @FXML
    private Label studentInformationProgramLabel;

    /*
    * This variables are used throughout
    * */
    private String studentId;
    private int registrationId;

    @FXML
    private AnchorPane studentRegistrationPane;

    // Course picker table
    @FXML
    private ObservableList <Course> courseList;
    @FXML
    private TableView <Course> registrationTableView;
    @FXML
    private Label registrationStatusLabel;

    // Registered courses table
    @FXML
    private ObservableList < Course > registeredCourseList;
    @FXML
    private TableView <Course> registeredCoursesTableView;
    @FXML
    private Label removeCourseStatusLabel;

    // Team page variables
    @FXML
    private AnchorPane studentTeamPane;
    @FXML
    private Label team1stMemberId;
    @FXML
    private ComboBox <Teacher>  supervisorComboBox;
    @FXML
    private TextField team3rdMemberIdField;
    @FXML
    private TextField team2ndMemberIdField;
    @FXML
    private TextField teamNameField;
    @FXML
    private ObservableList <Member> memberList;
    @FXML
    private ObservableList <TeamProposal> teamProposalList;

    @FXML
    private ObservableList <Teacher> teacherList;
    @FXML
    private TableView <Member> teamTableView;
    @FXML
    private Label createTeamStatusLabel;
    @FXML
    private Label teamRemovalStatusLabel;
    @FXML
    private Label teamNameLabel;
    @FXML
    private Label supervisorLabel;
    @FXML
    private TableView <TeamProposal> teamProposalTableView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        navigateToStudentDashboard(null); // setting "Dashboard" as default home page

        try{
            fetchStudentInformationFromDatabase();
            fetchCourseInformationFromDatabase();
            fetchRegistrationInformationFromDatabase();
            fetchTeacherInformationFromDatabase();
            fetchTeamInformation();
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        // initializing all pages
        populateStudentInformationBar();
        populateRegistrationTableView();
        populateRegisteredCoursesTableView();
        populateTeamPage();
        populateTeamTableView();
        populateTeamProposalTableView();
    }

    /*******************************************
     *  Page Navigation related methods
     * ******************************************/
    @FXML
    private void navigateToStudentDashboard(ActionEvent actionEvent) {
        studentInformationPane.setVisible(false);
        studentRegistrationPane.setVisible(false);
        studentTeamPane.setVisible(false);
        studentDashboardHomePane.setVisible(true);
    }

    @FXML
    private void navigateToStudentInformation(ActionEvent actionEvent) {
        studentDashboardHomePane.setVisible(false);
        studentRegistrationPane.setVisible(false);
        studentTeamPane.setVisible(false);
        studentInformationPane.setVisible(true);
        studentInformationUpdationStatusLabel.setText(null);

        studentInformationNameLabel.setText(student.getStudentName());
        studentInformationIdLabel.setText(student.getStudentId());
        studentInformationProgramLabel.setText("BSc in CSE");

        studentInformationEmailField.setText(student.getStudentEmail());
        studentInformationBloodGroupField.setText(student.getStudentBloodGroup());
        studentInformationContactNumberField.setText(student.getStudentContactNumber());
        studentInformationAddressField.setText(student.getStudentAddress());
    }

    @FXML
    private void navigateToRegistration(ActionEvent actionEvent) {
        studentDashboardHomePane.setVisible(false);
        studentInformationPane.setVisible(false);
        studentTeamPane.setVisible(false);
        registrationStatusLabel.setText(null);
        removeCourseStatusLabel.setText(null);
        studentRegistrationPane.setVisible(true);
    }

    @FXML
    private void navigateToStudentTeamPane(ActionEvent actionEvent){
        createTeamStatusLabel.setText(null);
        teamRemovalStatusLabel.setText(null);

        studentDashboardHomePane.setVisible(false);
        studentInformationPane.setVisible(false);
        studentRegistrationPane.setVisible(false);
        studentTeamPane.setVisible(true);
    }

    // Method to get all information about currently logged in Student from database
    private void fetchStudentInformationFromDatabase() throws SQLException {

        // Getting username passed from LoginUI
        studentId = LoginUIController.getUsername();
        System.out.println("User is " + studentId);
        StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();
        student = studentOp.getStudent(studentId);
    }

    @FXML
    private void populateStudentInformationBar(){
        studentNameLabel.setText(student.getStudentName());
        studentIdLabel.setText(student.getStudentId());
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

    /*******************************************
    *  Student Information Page related methods
    * ******************************************/
    @FXML
    private void handleStudentInformationUpdate(ActionEvent actionEvent) {
        String newEmail = studentInformationEmailField.getText();
        String newBloodGroup = studentInformationBloodGroupField.getText();
        String newContactNumber = studentInformationContactNumberField.getText();
        String newAddress = studentInformationAddressField.getText();

        StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();

        // flag variables to check if update operations were successful
        boolean emailStatus = true;
        boolean bloodGroupStatus = true;
        boolean contactNumberStatus = true;
        boolean addressStatus = true;

        emailStatus = studentOp.updateStudentEmail(studentId, newEmail);
        // If the database operation is performed, we update the student object
        if (emailStatus)
            this.student.setStudentEmail(newEmail);

        bloodGroupStatus = studentOp.updateStudentBloodGroup(studentId, newBloodGroup);
        // If the database operation is performed, we update the student object
        if (bloodGroupStatus)
            this.student.setStudentBloodGroup(newBloodGroup);

        contactNumberStatus = studentOp.updateStudentContactNumber(studentId, newContactNumber);
        // If the database operation is performed, we update the student object
        if (contactNumberStatus)
            this.student.setStudentContactNumber(newContactNumber);


        addressStatus = studentOp.updateStudentAddress(studentId, newAddress);
        // If the database operation is performed, we update the student object
        if (addressStatus)
            this.student.setStudentAddress(newAddress);


        // if all status flags are true then we conclude that the update operations are successful
        if (emailStatus && bloodGroupStatus && contactNumberStatus && addressStatus) {
            System.out.println("Email updated successfully.");
            studentInformationUpdationStatusLabel.setText("Information updated successfully.");
        } else {
            System.out.println("Updation error!");
            studentInformationUpdationStatusLabel.setText("Error updating information. Please recheck!");
        }
    }

    /*******************************************
     *  Registration Page related methods
     * ******************************************/
    private void fetchCourseInformationFromDatabase() throws SQLException{
        courseList = FXCollections.observableArrayList();
        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        courseList = courseOp.getAllCourses();

        for (Object course : courseList)
            System.out.println(course.toString());
    }

    private void fetchRegistrationInformationFromDatabase() throws SQLException{
        RegistrationDatabaseOperation regOp = new RegistrationDatabaseOperationImplementation();
        registrationId = regOp.getLastPrimaryKey();
        System.out.println(registrationId);
        registrationId = registrationId + 1; // next primary key

        registeredCourseList = regOp.getAllRegisteredCourses(student);
        System.out.println("Registered courses:");
        for (Object course : courseList)
            System.out.println(course.toString());
    }

    private void populateRegistrationTableView(){
        TableColumn<Course, Integer> courseId = new TableColumn<>("ID");
        courseId.setCellValueFactory(new PropertyValueFactory("courseId"));
        // Course Title Column
        TableColumn<Course, String> courseCode = new TableColumn<>("Course Code");
        courseCode.setCellValueFactory(new PropertyValueFactory("courseCode"));

        TableColumn<Course, String> courseTitle = new TableColumn<>("Course Title");
        courseTitle.setCellValueFactory(new PropertyValueFactory("courseTitle"));

        TableColumn<Course, Integer> courseSection = new TableColumn<>("Section");
        courseSection.setCellValueFactory(new PropertyValueFactory("courseSection"));

        registrationTableView.setItems(courseList);
        registrationTableView.getColumns().addAll(courseId, courseCode, courseTitle, courseSection);
    }

    private void populateRegisteredCoursesTableView(){
        TableColumn<Course, Integer> courseId = new TableColumn<>("ID");
        courseId.setCellValueFactory(new PropertyValueFactory("courseId"));
        // Course Title Column
        TableColumn<Course, String> courseCode = new TableColumn<>("Course Code");
        courseCode.setCellValueFactory(new PropertyValueFactory("courseCode"));

        TableColumn<Course, String> courseTitle = new TableColumn<>("Course Title");
        courseTitle.setCellValueFactory(new PropertyValueFactory("courseTitle"));

        TableColumn<Course, Integer> courseSection = new TableColumn<>("Section");
        courseSection.setCellValueFactory(new PropertyValueFactory("courseSection"));

        registeredCoursesTableView.setItems(registeredCourseList);
        registeredCoursesTableView.getColumns().addAll(courseId, courseCode, courseTitle, courseSection);
    }

    @FXML
    private void handleCourseRegistration(ActionEvent actionEvent) throws SQLException {
        removeCourseStatusLabel.setText(null); // clearing other fields

        Course pickedCourse = registrationTableView.getSelectionModel().getSelectedItem();
        if (pickedCourse == null){
            registrationStatusLabel.setText("Select a course first!");
            return;
        }

        System.out.println("Register for " + pickedCourse.toString());

        Registration registration = new Registration(registrationId, studentId, pickedCourse.getCourseId());
        System.out.println(registration.toString());
        RegistrationDatabaseOperation regOp = new RegistrationDatabaseOperationImplementation();
        boolean exists = regOp.exists(pickedCourse, student);

        if (exists){
            registrationStatusLabel.setText("Already registered for " + pickedCourse.getCourseCode());
            return;
        }

        boolean regStatus = regOp.insertRegistrationEntry(registration);
        if (regStatus){
            registrationId++; // increment registrationId to be primary key for the next entry
            registeredCourseList.add(pickedCourse);
            System.out.println(registration.toString() + " is done!");
            registrationStatusLabel.setText("Registration for " + pickedCourse.getCourseCode() + " successful.");
        } else{
            registrationStatusLabel.setText("Failed to add " + pickedCourse.getCourseCode() + "!");
        }
    }

    @FXML
    private void handleCourseRemoval(ActionEvent actionEvent) {
        registrationStatusLabel.setText(null); // clearing other fields

        Course pickedCourse = registeredCoursesTableView.getSelectionModel().getSelectedItem();
        if (pickedCourse == null){
            removeCourseStatusLabel.setText("Select a course first!");
            return;
        }
        RegistrationDatabaseOperation regOp = new RegistrationDatabaseOperationImplementation();
        boolean removeStatus = regOp.removeRegistration(pickedCourse, student);
        if (removeStatus){
            registeredCourseList.remove(pickedCourse);
            System.out.println("Registration cancelled for " + pickedCourse.toString());
            removeCourseStatusLabel.setText("Removed " + pickedCourse.getCourseCode() + " successfully.");
        } else{
            removeCourseStatusLabel.setText("Failed to remove " + pickedCourse.getCourseCode() + "!");
        }
    }

    /*******************************************
     *  Team Page related methods
     * ******************************************/
    private void populateTeamPage(){
        team1stMemberId.setText(student.getStudentId());
        supervisorComboBox.setItems(teacherList);
        supervisorComboBox.getSelectionModel().selectFirst();

    }

    // Fetching currentTeam information from Database
    private void fetchTeamInformation() throws SQLException {
        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        //int teamId = teamOp.studentMentionedIn(student.getStudentId());
        ObservableList <Team> teamList = teamOp.studentMentionedIn(studentId);
        teamProposalList = FXCollections.observableArrayList();
        if (teamList.size() > 0){
            System.out.println("TeamList Size: " + teamList.size());
            for (Team team : teamList){


                StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();
                Student student1 = studentOp.getStudent(team.getTeam1stMemberId());
                Student student2 = studentOp.getStudent(team.getTeam2ndMemberId());
                Student student3 = studentOp.getStudent(team.getTeam3rdMemberId());

                if (teamOp.hasConfirmed(team.getTeamId(), student.getStudentId())){
                    teamNameLabel.setText(team.getTeamName()); // setting Team Name
                    supervisorLabel.setText(team.getTeamSupervisorId()); // setting Supervisor initial

                    currentTeam = new Team(team);
                    System.out.println("CurrentTeam: " + currentTeam);
                    memberList = FXCollections.observableArrayList();

                    //System.out.println("Members: " + team.getTeam1stMemberId() + " " + team.getTeam2ndMemberId() + " " + team.getTeam3rdMemberId());


//                    System.out.println("Student 1: " + student1);
//                    System.out.println("Student 2: " + student2);
//                    System.out.println("Student 3: " + student3);

                    if (student1 != null){
                        Member member1 = new Member(1, student1.getStudentId(), student1.getStudentName(), team.getTeam1stMemberStatus());
                        //System.out.println(member1);
                        memberList.add(member1);
                    }

                    if (student2 != null){
                        Member member2 = new Member(2, student2.getStudentId(), student2.getStudentName(), team.getTeam2ndMemberStatus());
                        //System.out.println(member2);
                        memberList.add(member2);
                    }

                    if (student3 != null){
                        Member member3 = new Member(3, student3.getStudentId(), student3.getStudentName(), team.getTeam3rdMemberStatus());
                        //System.out.println(member3);
                        memberList.add(member3);
                    }
                    System.out.println(memberList.size());
                    teamTableView.setItems(memberList);
                }
                else{
                    TeamProposal teamProposal = new TeamProposal(team);
                    teamProposalList.add(teamProposal);
                }
            }
        } else {
            teamNameLabel.setText(null);

        }
        System.out.println("Proposal Count: " + teamProposalList.size());
        teamProposalTableView.setItems(teamProposalList);
    }

    // populating teacherList ObservableList from Database
    private void fetchTeacherInformationFromDatabase() throws SQLException {
        TeacherDatabaseOperation teacherOp = new TeacherDatabaseOperationImplementation();
        teacherList = teacherOp.getAllTeachers();
    }

    // Team of current student
    private void populateTeamTableView(){
        TableColumn<Member, Integer> memberSerial = new TableColumn<>("Serial");
        memberSerial.setCellValueFactory(new PropertyValueFactory("serial"));

        TableColumn<Member, Integer> memberStudentId = new TableColumn<>("Student ID");
        memberStudentId.setCellValueFactory(new PropertyValueFactory("studentId"));

        TableColumn<Member, Integer> memberName = new TableColumn<>("Name");
        memberName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn<Member, Integer> memberConfirmation = new TableColumn<>("Status");
        memberConfirmation.setCellValueFactory(new PropertyValueFactory("confirmationStatus"));

        teamTableView.setItems(memberList);
        teamTableView.getColumns().addAll(memberSerial, memberStudentId, memberName, memberConfirmation);
    }

    // Team proposals are put in this table
    private void populateTeamProposalTableView(){
        TableColumn < TeamProposal, Integer > teamId = new TableColumn<>("ID");
        teamId.setCellValueFactory(new PropertyValueFactory("teamId"));
        TableColumn < TeamProposal, Integer > teamName = new TableColumn<>("Name");
        teamName.setCellValueFactory(new PropertyValueFactory("teamName"));
        TableColumn < TeamProposal, Integer > supervisorId = new TableColumn<>("Supervisor");
        supervisorId.setCellValueFactory(new PropertyValueFactory("teamSupervisorId"));

        // Member 1
        TableColumn < TeamProposal, Integer > member1Id = new TableColumn<>("Member 1 ID");
        member1Id.setCellValueFactory(new PropertyValueFactory("team1stMemberId"));
        TableColumn < TeamProposal, Integer > member1Name = new TableColumn<>("Member 1 Name");
        member1Name.setCellValueFactory(new PropertyValueFactory("team1stMemberName"));
        TableColumn < TeamProposal, Integer > member1Status = new TableColumn<>("Member 1 Status");
        member1Status.setCellValueFactory(new PropertyValueFactory("team1stMemberStatus"));
        // Member 2
        TableColumn < TeamProposal, Integer > member2Id = new TableColumn<>("Member 2 ID");
        member2Id.setCellValueFactory(new PropertyValueFactory("team2ndMemberId"));
        TableColumn < TeamProposal, Integer > member2Name = new TableColumn<>("Member 2 Name");
        member1Name.setCellValueFactory(new PropertyValueFactory("team2ndMemberName"));
        TableColumn < TeamProposal, Integer > member2Status = new TableColumn<>("Member 2 Status");
        member2Status.setCellValueFactory(new PropertyValueFactory("team2ndMemberStatus"));
        // Member 3
        TableColumn < TeamProposal, Integer > member3Id = new TableColumn<>("Member 3 ID");
        member3Id.setCellValueFactory(new PropertyValueFactory("team3rdMemberId"));
        TableColumn < TeamProposal, Integer > member3Name = new TableColumn<>("Member 3 Name");
        member1Name.setCellValueFactory(new PropertyValueFactory("team3rdMemberName"));
        TableColumn < TeamProposal, Integer > member3Status = new TableColumn<>("Member 3 Status");
        member3Status.setCellValueFactory(new PropertyValueFactory("team3rdMemberStatus"));

        teamProposalTableView.setItems(teamProposalList);
        teamProposalTableView.getColumns().addAll(teamId, teamName, supervisorId, member1Id, member1Name, member1Status, member2Id, member2Name, member2Status, member3Id, member3Name, member3Status);
    }

    @FXML
    private void handleCreateTeam(ActionEvent actionEvent) throws SQLException {
        teamRemovalStatusLabel.setText(null);

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        int teamId = teamOp.getLastPrimaryKey() + 1;
        System.out.println("New Team ID: " + teamId);
        String teamName = teamNameField.getText();
        String teamSupervisorId = supervisorComboBox.getSelectionModel().getSelectedItem().getTeacherInitial();

        String team1stMemberId = student.getStudentId(); // current student is 1st Member
        String team1stMemberStatus = "Confirmed"; // One proposing is confirmed by default

        String team2ndMemberId = team2ndMemberIdField.getText();
        String team2ndMemberStatus = "Pending"; // 2nd member is pending by default
        if (team2ndMemberId.length() == 0) {
            team2ndMemberId = null;
            team2ndMemberStatus = null;
        }

        String team3rdMemberId = team3rdMemberIdField.getText();
        String team3rdMemberStatus = "Pending"; // 3rd member is pending by default
        if (team3rdMemberId.length() == 0) {
            team3rdMemberId = null;
            team3rdMemberStatus = null;
        }

        StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();
        System.out.println("2nd student " + studentOp.getStudent(team2ndMemberId));
        System.out.println("3rd student " + studentOp.getStudent(team3rdMemberId));
        if ((team2ndMemberId != null && studentOp.getStudent(team2ndMemberId) == null) || (team3rdMemberId != null && studentOp.getStudent(team3rdMemberId) == null)){
            createTeamStatusLabel.setText("One or more students do not exist.");
            return;
        }

        if (team1stMemberId.equals(team2ndMemberId) || (team1stMemberId.equals(team3rdMemberId) || (team2ndMemberId != null && team2ndMemberId.equals(team3rdMemberId)))){
            System.out.println(team1stMemberId + " " + team2ndMemberId + " " + team3rdMemberId);
            createTeamStatusLabel.setText("Duplicate Student ID Found! Please recheck.");
            return;
        }

        String studentExists = "";

//        if (teamOp.studentExists(team1stMemberId)) { createTeamStatusLabel.setText("Member 1 already belongs to other currentTeam."); }
//        if (teamOp.studentExists(team2ndMemberId)) { createTeamStatusLabel.setText("\nMember 1 already belongs to other currentTeam."); }
//        if (teamOp.studentExists(team3rdMemberId)) { createTeamStatusLabel.setText("\nMember 1 already belongs to other currentTeam."); }

        System.out.println(studentExists);

        if (studentExists.length() > 0){
            createTeamStatusLabel.setText("Member " + studentExists + " already exists in other currentTeam.");
            return;
        }
        // Team object is created to insert into database
        Team newTeam = new Team(teamId, teamName, teamSupervisorId,
                team1stMemberId, team1stMemberStatus,
                team2ndMemberId, team2ndMemberStatus,
                team3rdMemberId, team3rdMemberStatus);

        boolean insertStatus = teamOp.insertTeam(newTeam);
        if (insertStatus){ // if insertion is successful we push the member in the memberList
            fetchTeamInformation();
            createTeamStatusLabel.setText("Team created Successfully!");
        }

    }

    @FXML
    private void handleTeamDelete(ActionEvent actionEvent) throws SQLException {
        createTeamStatusLabel.setText(null);

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        if (!teamOp.isTeamLeader(student.getStudentId())){ // if the student deleting currentTeam is not the leader, he can not delete
            teamRemovalStatusLabel.setText("You do not have permission to delete this currentTeam.");
            return;
        }
        System.out.println("Current Team: " + currentTeam.toString());
        if ((currentTeam.getTeam2ndMemberId() != null && teamOp.hasConfirmed(currentTeam.getTeamId(), currentTeam.getTeam2ndMemberId())) || (currentTeam.getTeam3rdMemberId() != null && teamOp.hasConfirmed(currentTeam.getTeamId(), currentTeam.getTeam3rdMemberId()))){
            teamRemovalStatusLabel.setText("Can not delete. One or more members have confirmed.");
            return;
        }
        if (teamOp.deleteTeam(currentTeam.getTeamId())){ // student
            teamRemovalStatusLabel.setText("Team deleted successfully.");
            memberList.clear();
        } else {
            teamRemovalStatusLabel.setText("Some error occurred!");
        }
    }
}
