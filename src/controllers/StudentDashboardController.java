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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.*;
import objects.dbinterfaces.*;
import users.*;
import users.dbinterfaces.StudentDatabaseOperation;
import users.dbinterfaces.TeacherDatabaseOperation;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private LocalDate applicationDeadline;

    @FXML
    private Label applicationDeadlineLabel;


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
    private int numberOfTeams;
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
    private ObservableList <TeamProposal> teamProposalList;

    @FXML
    private ObservableList <Teacher> teacherList;
    @FXML
    private Label createTeamStatusLabel;
    @FXML
    private Label teamRemovalStatusLabel;
    @FXML
    private TableView <TeamProposal> teamProposalTableView;
    @FXML
    private Label proposalStatusLabel;

    // team 1 variables
    private Team team1;
    @FXML
    private Label team1IdLabel;
    @FXML
    private Label team1NameLabel;
    @FXML
    private Label team1SupervisorLabel;
    @FXML
    private ObservableList <Member> memberListTeam1;
    @FXML
    private TableView <Member> team1TableView;

    // team 2 variables
    private Team team2;
    @FXML
    private Label team2IdLabel;
    @FXML
    private Label team2NameLabel;
    @FXML
    private Label team2SupervisorLabel;
    @FXML
    private ObservableList <Member> memberListTeam2;
    @FXML
    private TableView <Member> team2TableView;

    // team 3 variables
    private Team team3;
    @FXML
    private Label team3IdLabel;
    @FXML
    private Label team3NameLabel;
    @FXML
    private Label team3SupervisorLabel;
    @FXML
    private ObservableList <Member> memberListTeam3;
    @FXML
    private TableView <Member> team3TableView;

    // Apply for Research page variables
    @FXML
    private AnchorPane researchApplicationPane;
    @FXML
    private Label researchApplicationFormStatusLabel;
    @FXML
    private Label researchTeamNameLabel;
    @FXML
    private Label researchTeamSupervisorLabel;
    @FXML
    private Label researchTeamIdLabel;
    @FXML
    private TableView <Member> researchTeamTableView;
    @FXML
    private TextField researchTopicField;
    @FXML
    private TextField researchCommentField;
    @FXML
    private TextArea researchHypothesisArea;
    @FXML
    private ToggleGroup semesterSelectRadioButtons;
    @FXML
    private Label researchApplicationStatusLabel;
    @FXML
    private RadioButton semesterRadioButton1;
    @FXML
    private RadioButton semesterRadioButton2;
    @FXML
    private TabPane teamTabPane;
    @FXML
    private ComboBox <Team> researchTeamComboBox;
    @FXML
    private ObservableList <Team> researchTeamList;
    @FXML
    private ObservableList <ResearchApplication> researchApplicationList;
    @FXML
    private TableView <ResearchApplication> researchApplicationTableView;
    @FXML
    private Label applicationHistoryStatusLabel;
    @FXML
    private Label researchSupervisorStatusLabel;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        navigateToStudentDashboard(null); // setting "Dashboard" as default home page

        try{
            // clearing labels and fields
            clearTeamInformation();

            // fetching necessary information from database
            fetchStudentInformationFromDatabase();
            fetchCourseInformationFromDatabase();
            fetchDeadlineInformationFromDatabase();
            fetchRegistrationInformationFromDatabase();
            fetchTeacherInformationFromDatabase();
            fetchTeamInformation();
            fetchApplicationInformation();

            // initializing all pages using fetched information
            populateStudentInformationBar();
            populateRegistrationTableView();
            populateRegisteredCoursesTableView();
            populateTeamPage();
            populateTeamTableView();
            populateTeamProposalTableView();
            populateApplicationTableView();
            //populateResearchTeamInformation();

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }

    /*******************************************
     *  Page Navigation related methods
     * ******************************************/
    @FXML
    private void navigateToStudentDashboard(ActionEvent actionEvent) {
        studentInformationPane.setVisible(false);
        studentRegistrationPane.setVisible(false);
        studentTeamPane.setVisible(false);
        researchApplicationPane.setVisible(false);
        studentDashboardHomePane.setVisible(true);
    }

    @FXML
    private void navigateToStudentInformation(ActionEvent actionEvent) {
        studentDashboardHomePane.setVisible(false);
        studentRegistrationPane.setVisible(false);
        studentTeamPane.setVisible(false);
        researchApplicationPane.setVisible(false);
        studentInformationPane.setVisible(true);
        studentInformationUpdationStatusLabel.setText(null);
    }

    @FXML
    private void navigateToRegistration(ActionEvent actionEvent) {
        studentDashboardHomePane.setVisible(false);
        studentInformationPane.setVisible(false);
        studentTeamPane.setVisible(false);
        researchApplicationPane.setVisible(false);
        registrationStatusLabel.setText(null);
        removeCourseStatusLabel.setText(null);
        studentRegistrationPane.setVisible(true);
    }

    @FXML
    private void navigateToStudentTeamPane(ActionEvent actionEvent){
        createTeamStatusLabel.setText(null);
        teamRemovalStatusLabel.setText(null);
        proposalStatusLabel.setText(null);

        studentDashboardHomePane.setVisible(false);
        studentInformationPane.setVisible(false);
        studentRegistrationPane.setVisible(false);
        researchApplicationPane.setVisible(false);
        studentTeamPane.setVisible(true);
    }

    @FXML
    private void navigateToResearchApplicationPane(ActionEvent actionEvent) throws SQLException {
        researchApplicationFormStatusLabel.setText(null);
        applicationHistoryStatusLabel.setText(null);
        clearApplicationStats(null);
        studentDashboardHomePane.setVisible(false);
        studentInformationPane.setVisible(false);
        studentRegistrationPane.setVisible(false);
        studentTeamPane.setVisible(false);
        researchApplicationPane.setVisible(true);
    }

    // Method to get all information about currently logged in Student from database
    private void fetchStudentInformationFromDatabase() throws SQLException {

        // Getting username passed from LoginUI
        studentId = LoginUIController.getUsername();
        System.out.println("User is " + studentId);
        StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();
        student = studentOp.getStudent(studentId);

        studentInformationNameLabel.setText(student.getStudentName());
        studentInformationIdLabel.setText(student.getStudentId());
        studentInformationProgramLabel.setText("BSc in CSE");

        studentInformationEmailField.setText(student.getStudentEmail());
        studentInformationBloodGroupField.setText(student.getStudentBloodGroup());
        studentInformationContactNumberField.setText(student.getStudentContactNumber());
        studentInformationAddressField.setText(student.getStudentAddress());
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
        boolean emailStatus ;
        boolean bloodGroupStatus;
        boolean contactNumberStatus;
        boolean addressStatus;

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
            //System.out.println("Email updated successfully.");
            studentInformationUpdationStatusLabel.setText("Information updated successfully.");
        } else {
            //System.out.println("Updation error!");
            studentInformationUpdationStatusLabel.setText("Error updating information. Please recheck!");
        }
    }


    /*******************************************
     *  Registration Page related methods
     * ******************************************/

    private void fetchDeadlineInformationFromDatabase() throws SQLException {
        DeadlineDatabaseOperation deadlineOp = new DeadlineDatabaseOperationImplementation();
        applicationDeadline = deadlineOp.getApplicationSubmissionDeadline();

        // setting last changed deadlines on the labels
        if (applicationDeadline != null)
            applicationDeadlineLabel.setText(applicationDeadline.toString());
        else
            applicationDeadlineLabel.setText(null);
    }

    private void fetchCourseInformationFromDatabase() throws SQLException{
        courseList = FXCollections.observableArrayList();
        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        courseList = courseOp.getAllCourses();

//        for (Object course : courseList)
//            System.out.println(course.toString());
    }

    private void fetchRegistrationInformationFromDatabase() throws SQLException{
        RegistrationDatabaseOperation regOp = new RegistrationDatabaseOperationImplementation();
        registrationId = regOp.getLastPrimaryKey();
        System.out.println(registrationId);
        registrationId = registrationId + 1; // next primary key

        registeredCourseList = regOp.getAllRegisteredCourses(student);
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

        //System.out.println("Register for " + pickedCourse.toString());

        Registration registration = new Registration(registrationId, studentId, pickedCourse.getCourseId());
        //System.out.println(registration.toString());
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
            //System.out.println(registration.toString() + " is done!");
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

        if (pickedCourse.getCourseId() == 1 && numberOfTeams > 0){
            removeCourseStatusLabel.setText("You can not remove CSE4000 after joining a Research Team.");
            return;
        }

        RegistrationDatabaseOperation regOp = new RegistrationDatabaseOperationImplementation();
        boolean removeStatus = regOp.removeRegistration(pickedCourse, student);
        if (removeStatus){
            registeredCourseList.remove(pickedCourse);
            //System.out.println("Registration cancelled for " + pickedCourse.toString());
            removeCourseStatusLabel.setText("Removed " + pickedCourse.getCourseCode() + " successfully.");
        } else{
            removeCourseStatusLabel.setText("Failed to remove " + pickedCourse.getCourseCode() + "!");
        }
    }

    /*******************************************
     *  Team Page related methods
     * ******************************************/

    private void clearTeamInformation() {
        clearTeam1Information();
        clearTeam2Information();
        clearTeam3Information();

        researchTeamIdLabel.setText(null);
        researchTeamNameLabel.setText(null);
        researchTeamSupervisorLabel.setText(null);
    }

    private void clearTeam1Information(){
        team1IdLabel.setText(null);
        team1NameLabel.setText(null);
        team1SupervisorLabel.setText(null);
    }

    private void clearTeam2Information(){
        team2IdLabel.setText(null);
        team2NameLabel.setText(null);
        team2SupervisorLabel.setText(null);
    }

    private void clearTeam3Information(){
        team3IdLabel.setText(null);
        team3NameLabel.setText(null);
        team3SupervisorLabel.setText(null);
    }

    private void populateTeamPage(){
        team1stMemberId.setText(student.getStudentId());
        System.out.println("Number of teachers: " + teacherList.size());
        supervisorComboBox.setItems(teacherList);
        supervisorComboBox.getSelectionModel().selectFirst();
    }

    // Fetching currentTeam information from Database
    private void fetchTeamInformation() throws SQLException {
        clearTeamInformation();
        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        //int teamId = teamOp.studentMentionedIn(student.getStudentId());
        ObservableList <Team> teamList = teamOp.studentMentionedIn(studentId);

        numberOfTeams = 0;
        memberListTeam1 = FXCollections.observableArrayList();
        memberListTeam2 = FXCollections.observableArrayList();
        memberListTeam3 = FXCollections.observableArrayList();
        teamProposalList = FXCollections.observableArrayList();
        researchTeamList = FXCollections.observableArrayList();

        if (teamList.size() > 0){
            //System.out.println("TeamList Size: " + teamList.size());

            CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
            RegistrationDatabaseOperation regOp = new RegistrationDatabaseOperationImplementation();

            Course ResearchMethodology = courseOp.getCourse(1);

            for (Team team : teamList){

                StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();
                Student student1 = studentOp.getStudent(team.getTeam1stMemberId());
                Student student2 = studentOp.getStudent(team.getTeam2ndMemberId());
                Student student3 = studentOp.getStudent(team.getTeam3rdMemberId());


                if (teamOp.hasConfirmed(team.getTeamId(), student.getStudentId())){

                    numberOfTeams++;
                    researchTeamList.add(team);
                    researchTeamComboBox.setItems(researchTeamList);
                    researchTeamComboBox.getSelectionModel().selectFirst();

                    // check if they are registered for CSE4000
                    boolean hasRegisteredMember1;
                    boolean hasRegisteredMember2;
                    boolean hasRegisteredMember3;

                    String registeredForResearchMember1 = null;
                    String registeredForResearchMember2 = null;
                    String registeredForResearchMember3 = null;

                    if (student1 != null){
                        hasRegisteredMember1 = regOp.exists(ResearchMethodology, student1);
                        if (hasRegisteredMember1)
                            registeredForResearchMember1 = "Yes";
                        else
                            registeredForResearchMember1 = "No";
                    }

                    if (student2 != null){
                        hasRegisteredMember2 = regOp.exists(ResearchMethodology, student2);
                        if (hasRegisteredMember2)
                            registeredForResearchMember2 = "Yes";
                        else
                            registeredForResearchMember2 = "No";
                    }

                    if (student3 != null){
                        hasRegisteredMember3 = regOp.exists(ResearchMethodology, student3);
                        if (hasRegisteredMember3)
                            registeredForResearchMember3 = "Yes";
                        else
                            registeredForResearchMember3 = "No";
                    }


                    // for 1st team slot
                    if (team1IdLabel.getText() == null){
                        //System.out.println("Team ID: "+ String.valueOf(team.getTeamId()));
                        // for current Team
                        team1IdLabel.setText(String.valueOf(team.getTeamId())); // setting Team ID
                        team1NameLabel.setText(team.getTeamName()); // setting Team Name
                        team1SupervisorLabel.setText(team.getTeamSupervisorId()); // setting Supervisor initial

                        // for research Team
                        researchTeamIdLabel.setText(String.valueOf(team.getTeamId())); // setting Team ID
                        researchTeamNameLabel.setText(team.getTeamName()); // setting Team Name
                        researchTeamSupervisorLabel.setText(team.getTeamSupervisorId()); // setting Supervisor initial

                        team1 = new Team(team);

                        if (student1 != null){

                            Member member1 = new Member(1, student1.getStudentId(), student1.getStudentName(), registeredForResearchMember1, team.getTeam1stMemberStatus());
                            System.out.println(member1);
                            memberListTeam1.add(member1);
                        }

                        if (student2 != null){
                            Member member2 = new Member(2, student2.getStudentId(), student2.getStudentName(), registeredForResearchMember2, team.getTeam2ndMemberStatus());
                            //System.out.println(member2);
                            memberListTeam1.add(member2);
                        }

                        if (student3 != null){

                            Member member3 = new Member(3, student3.getStudentId(), student3.getStudentName(), registeredForResearchMember3, team.getTeam3rdMemberStatus());
                            //System.out.println(member3);
                            memberListTeam1.add(member3);
                        }

                        team1TableView.setItems(memberListTeam1);
                    }
                    // for 2nd team slot
                    else if (team2IdLabel.getText() == null){
                       // System.out.println("Team ID: "+ String.valueOf(team.getTeamId()));
                        // for current Team
                        team2IdLabel.setText(String.valueOf(team.getTeamId())); // setting Team ID
                        team2NameLabel.setText(team.getTeamName()); // setting Team Name
                        team2SupervisorLabel.setText(team.getTeamSupervisorId()); // setting Supervisor initial

                        // for research Team
                        researchTeamIdLabel.setText(String.valueOf(team.getTeamId())); // setting Team ID
                        researchTeamNameLabel.setText(team.getTeamName()); // setting Team Name
                        researchTeamSupervisorLabel.setText(team.getTeamSupervisorId()); // setting Supervisor initial

                        team2 = new Team(team);

                        if (student1 != null){
                            Member member1 = new Member(1, student1.getStudentId(), student1.getStudentName(), registeredForResearchMember1, team.getTeam1stMemberStatus());
                            //System.out.println(member1);
                            memberListTeam2.add(member1);
                        }

                        if (student2 != null){
                            Member member2 = new Member(2, student2.getStudentId(), student2.getStudentName(), registeredForResearchMember2, team.getTeam2ndMemberStatus());
                            //System.out.println(member2);
                            memberListTeam2.add(member2);
                        }

                        if (student3 != null){
                            Member member3 = new Member(3, student3.getStudentId(), student3.getStudentName(), registeredForResearchMember3, team.getTeam3rdMemberStatus());
                            //System.out.println(member3);
                            memberListTeam2.add(member3);
                        }

                        team2TableView.setItems(memberListTeam2);
                        //researchTeamTableView.setItems(memberListTeam2);
                    }

                    // for 3rd team slot
                    else if (team3IdLabel.getText() == null){
                        //System.out.println("Team ID: "+ String.valueOf(team.getTeamId()));
                        // for current Team
                        team3IdLabel.setText(String.valueOf(team.getTeamId())); // setting Team ID
                        team3NameLabel.setText(team.getTeamName()); // setting Team Name
                        team3SupervisorLabel.setText(team.getTeamSupervisorId()); // setting Supervisor initial

                        // for research Team
                        researchTeamIdLabel.setText(String.valueOf(team.getTeamId())); // setting Team ID
                        researchTeamNameLabel.setText(team.getTeamName()); // setting Team Name
                        researchTeamSupervisorLabel.setText(team.getTeamSupervisorId()); // setting Supervisor initial

                        team3 = new Team(team);


                        if (student1 != null){
                            Member member1 = new Member(1, student1.getStudentId(), student1.getStudentName(), registeredForResearchMember1, team.getTeam1stMemberStatus());
                            //System.out.println(member1);
                            memberListTeam3.add(member1);
                        }

                        if (student2 != null){
                            Member member2 = new Member(2, student2.getStudentId(), student2.getStudentName(), registeredForResearchMember2, team.getTeam2ndMemberStatus());
                            //System.out.println(member2);
                            memberListTeam3.add(member2);
                        }

                        if (student3 != null){
                            Member member3 = new Member(3, student3.getStudentId(), student3.getStudentName(), registeredForResearchMember3, team.getTeam3rdMemberStatus());
                            //System.out.println(member3);
                            memberListTeam3.add(member3);
                        }

                        team3TableView.setItems(memberListTeam3);
                        //researchTeamTableView.setItems(memberListTeam3);
                    }
                }
                else{
                    TeamProposal teamProposal = new TeamProposal(team);

                    teamProposalList.add(teamProposal);
                }
            }
        } else {
            team1NameLabel.setText(null);

        }

        teamProposalTableView.setItems(teamProposalList);
        //populateTeamTableView();
        System.out.println("Number of teams: " + numberOfTeams);
    }

    // populating teacherList ObservableList from Database
    private void fetchTeacherInformationFromDatabase() throws SQLException {
        TeacherDatabaseOperation teacherOp = new TeacherDatabaseOperationImplementation();
        teacherList = teacherOp.getAllTeachers();
    }

    // Team of current student
    private void populateTeamTableView(){
        populateTeam1TableView();
        populateTeam2TableView();
        populateTeam3TableView();
    }


    private void populateTeam1TableView(){
        TableColumn<Member, Integer> memberSerial1 = new TableColumn<>("Serial");
        memberSerial1.setCellValueFactory(new PropertyValueFactory("serial"));

        TableColumn<Member, Integer> memberStudentId1 = new TableColumn<>("Student ID");
        memberStudentId1.setCellValueFactory(new PropertyValueFactory("studentId"));

        TableColumn<Member, Integer> memberName1 = new TableColumn<>("Name");
        memberName1.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn<Member, Integer> memberConfirmation1 = new TableColumn<>("Status");
        memberConfirmation1.setCellValueFactory(new PropertyValueFactory("confirmationStatus"));
        System.out.println("List 1 Size: " + memberListTeam1.size());
        team1TableView.setItems(memberListTeam1);
        team1TableView.getColumns().addAll(memberSerial1, memberStudentId1, memberName1, memberConfirmation1);
    }

    private void populateTeam2TableView(){
        TableColumn<Member, Integer> memberSerial = new TableColumn<>("Serial");
        memberSerial.setCellValueFactory(new PropertyValueFactory("serial"));

        TableColumn<Member, Integer> memberStudentId = new TableColumn<>("Student ID");
        memberStudentId.setCellValueFactory(new PropertyValueFactory("studentId"));

        TableColumn<Member, Integer> memberName = new TableColumn<>("Name");
        memberName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn<Member, Integer> memberConfirmation = new TableColumn<>("Status");
        memberConfirmation.setCellValueFactory(new PropertyValueFactory("confirmationStatus"));
        team2TableView.setItems(memberListTeam2);
        team2TableView.getColumns().addAll(memberSerial, memberStudentId, memberName, memberConfirmation);
    }

    private void populateTeam3TableView(){
        TableColumn<Member, Integer> memberSerial = new TableColumn<>("Serial");
        memberSerial.setCellValueFactory(new PropertyValueFactory("serial"));

        TableColumn<Member, Integer> memberStudentId = new TableColumn<>("Student ID");
        memberStudentId.setCellValueFactory(new PropertyValueFactory("studentId"));

        TableColumn<Member, Integer> memberName = new TableColumn<>("Name");
        memberName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn<Member, Integer> memberConfirmation = new TableColumn<>("Status");
        memberConfirmation.setCellValueFactory(new PropertyValueFactory("confirmationStatus"));
        System.out.println("List 3 Size: " + memberListTeam3.size());
        team3TableView.setItems(memberListTeam3);
        team3TableView.getColumns().addAll(memberSerial, memberStudentId, memberName, memberConfirmation);
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
        member2Name.setCellValueFactory(new PropertyValueFactory("team2ndMemberName"));
        TableColumn < TeamProposal, Integer > member2Status = new TableColumn<>("Member 2 Status");
        member2Status.setCellValueFactory(new PropertyValueFactory("team2ndMemberStatus"));
        // Member 3
        TableColumn < TeamProposal, Integer > member3Id = new TableColumn<>("Member 3 ID");
        member3Id.setCellValueFactory(new PropertyValueFactory("team3rdMemberId"));
        TableColumn < TeamProposal, Integer > member3Name = new TableColumn<>("Member 3 Name");
        member3Name.setCellValueFactory(new PropertyValueFactory("team3rdMemberName"));
        TableColumn < TeamProposal, Integer > member3Status = new TableColumn<>("Member 3 Status");
        member3Status.setCellValueFactory(new PropertyValueFactory("team3rdMemberStatus"));

        teamProposalTableView.setItems(teamProposalList);
        teamProposalTableView.getColumns().addAll(teamId, teamName, supervisorId, member1Id, member1Name, member1Status, member2Id, member2Name, member2Status, member3Id, member3Name, member3Status);
    }

    @FXML
    private void handleCreateTeam(ActionEvent actionEvent) throws SQLException {
        teamRemovalStatusLabel.setText(null);

        System.out.println("Number of teams: " + numberOfTeams);
        if (numberOfTeams >= 3){
            createTeamStatusLabel.setText("Team limit 3 is reached. Delete existing team(s) to add more team(s).");
            return;
        }

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();

        int teamId = teamOp.getMaxPrimaryKey() + 1;
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
        //System.out.println("2nd student " + studentOp.getStudent(team2ndMemberId));
        //System.out.println("3rd student " + studentOp.getStudent(team3rdMemberId));
        if ((team2ndMemberId != null && studentOp.getStudent(team2ndMemberId) == null) || (team3rdMemberId != null && studentOp.getStudent(team3rdMemberId) == null)){
            createTeamStatusLabel.setText("One or more students do not exist.");
            return;
        }

        if (team1stMemberId.equals(team2ndMemberId) || (team1stMemberId.equals(team3rdMemberId) || (team2ndMemberId != null && team2ndMemberId.equals(team3rdMemberId)))){
            //System.out.println(team1stMemberId + " " + team2ndMemberId + " " + team3rdMemberId);
            createTeamStatusLabel.setText("Duplicate Student ID Found! Please recheck.");
            return;
        }

        // checking if the members are registered for "Research Methodology" course
        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        Course ResearchMethodology = courseOp.getCourse(1); // courseId of "Research Methodology" course is 1

        RegistrationDatabaseOperation regOp = new RegistrationDatabaseOperationImplementation();

        String notRegisteredMembers = "";
        if (team1stMemberId != null && studentOp.getStudent(team1stMemberId) != null && !regOp.exists(ResearchMethodology, studentOp.getStudent(team1stMemberId))){
            notRegisteredMembers += "1";
        }
        if (team2ndMemberId != null && studentOp.getStudent(team2ndMemberId) != null && !regOp.exists(ResearchMethodology, studentOp.getStudent(team2ndMemberId))){
            if (notRegisteredMembers.length() > 0)
                notRegisteredMembers += ", 2";
            else
                notRegisteredMembers += "2";
        }
        if (team3rdMemberId != null && studentOp.getStudent(team3rdMemberId) != null && !regOp.exists(ResearchMethodology, studentOp.getStudent(team3rdMemberId))){
            if (notRegisteredMembers.length() > 0)
                notRegisteredMembers += ", 3";
            else
                notRegisteredMembers += "3";
        }

        if (notRegisteredMembers.length() > 0){
            createTeamStatusLabel.setText("Member(s) (" + notRegisteredMembers + ") did not register for CSE4000 : ResearchMethodology.");
            return;
        }

        // Team object is created to insert into database
        Team newTeam = new Team(teamId, teamName, teamSupervisorId,
                team1stMemberId, team1stMemberStatus,
                team2ndMemberId, team2ndMemberStatus,
                team3rdMemberId, team3rdMemberStatus);

        boolean insertStatus = teamOp.insertTeam(newTeam);
        if (insertStatus){ // if insertion is successful we push the member in the memberListTeam1
            fetchTeamInformation();
            createTeamStatusLabel.setText("Team created Successfully!");
        }

    }

    @FXML
    private void handleTeamDelete(ActionEvent actionEvent) throws SQLException {

        createTeamStatusLabel.setText(null);
        proposalStatusLabel.setText(null);

        int currentTab = teamTabPane.getSelectionModel().getSelectedIndex();

        if ((currentTab == 0 && team1IdLabel.getText() == null) || (currentTab == 1 && team2IdLabel.getText() == null) || (currentTab == 2 && team3IdLabel.getText() == null)){
            teamRemovalStatusLabel.setText("There is no team to delete.");
            return;
        }

        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();

        // Deleting team 1
        if (currentTab == 0){
            TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
            if (!teamOp.isTeamLeaderOf(Integer.parseInt(team1IdLabel.getText()), student.getStudentId())){ // if the student deleting team1 is not the leader, he can not delete
                teamRemovalStatusLabel.setText("You can only delete team you are Team Leader of.");
                return;
            }

            if ((team1.getTeam2ndMemberId() != null && teamOp.hasConfirmed(team1.getTeamId(), team1.getTeam2ndMemberId())) || (team1.getTeam3rdMemberId() != null && teamOp.hasConfirmed(team1.getTeamId(), team1.getTeam3rdMemberId()))){
                teamRemovalStatusLabel.setText("Can not delete. One or more members have confirmed.");
                return;
            }

            if (researchOp.teamExists(team1.getTeamId())){
                teamRemovalStatusLabel.setText("Can not remove team that was selected for application.");
                return;
            }

            if (teamOp.deleteTeam(team1.getTeamId())){ // student
                teamRemovalStatusLabel.setText("Team deleted successfully.");

                // to avoid garbage in the team table
                clearMemberLists();

                clearTeamInformation();
                fetchTeamInformation();
            } else {
                teamRemovalStatusLabel.setText("Some error occurred!");
            }
        }

        else if (currentTab == 1){
            TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
            if (!teamOp.isTeamLeaderOf(Integer.parseInt(team2IdLabel.getText()), student.getStudentId())){ // if the student deleting team2 is not the leader, he can not delete
                teamRemovalStatusLabel.setText("You can only delete team you are Team Leader of.");
                return;
            }
            System.out.println("Current Team: " + team2.toString());
            if ((team2.getTeam2ndMemberId() != null && teamOp.hasConfirmed(team2.getTeamId(), team2.getTeam2ndMemberId())) || (team2.getTeam3rdMemberId() != null && teamOp.hasConfirmed(team2.getTeamId(), team2.getTeam3rdMemberId()))){
                teamRemovalStatusLabel.setText("Can not delete. One or more members have confirmed.");
                return;
            }
            if (teamOp.deleteTeam(team2.getTeamId())){ // student
                teamRemovalStatusLabel.setText("Team deleted successfully.");

                // to avoid garbage in the team table
                clearMemberLists();

                clearTeamInformation();
                fetchTeamInformation();
            } else {
                teamRemovalStatusLabel.setText("Some error occurred!");
            }
        }
        else {
            TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
            if (!teamOp.isTeamLeaderOf(Integer.parseInt(team3IdLabel.getText()), student.getStudentId())){ // if the student deleting team2 is not the leader, he can not delete
                teamRemovalStatusLabel.setText("You can only delete team you are Team Leader of.");
                return;
            }
            //System.out.println("Current Team: " + team3.toString());
            if ((team3.getTeam2ndMemberId() != null && teamOp.hasConfirmed(team3.getTeamId(), team3.getTeam2ndMemberId())) || (team3.getTeam3rdMemberId() != null && teamOp.hasConfirmed(team3.getTeamId(), team3.getTeam3rdMemberId()))){
                teamRemovalStatusLabel.setText("Can not delete. One or more members have confirmed.");
                return;
            }
            if (teamOp.deleteTeam(team3.getTeamId())){ // student
                teamRemovalStatusLabel.setText("Team deleted successfully.");

                // to avoid garbage in the team table
                clearMemberLists();

                clearTeamInformation();
                fetchTeamInformation();
            } else {
                teamRemovalStatusLabel.setText("Some error occurred!");
            }
        }
        // clearing all memberLists since we will update them again from database
        //System.out.println("Sizes: " + memberListTeam1.size() + " " + memberListTeam2.size() + " " + memberListTeam3.size());
    }


    private void clearMemberLists(){
        memberListTeam1.clear();
        memberListTeam2.clear();
        memberListTeam3.clear();
    }

    // current student confirms a team join proposal
    @FXML
    private void handleConfirmTeamProposal(ActionEvent actionEvent) throws SQLException {
        // clearing other status fields
        createTeamStatusLabel.setText(null);
        teamRemovalStatusLabel.setText(null);

        if (numberOfTeams >= 3){
            proposalStatusLabel.setText("Team limit 3 is reached. Delete existing team(s) to add more team(s).");
            return;
        }

        TeamProposal teamProposal = teamProposalTableView.getSelectionModel().getSelectedItem();
        if (teamProposal == null){
            proposalStatusLabel.setText("You must select a team to confirm/reject.");
            return;
        }
        Team team = new Team(teamProposal);

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        if (teamOp.confirmTeam(student.getStudentId(), team)){
            proposalStatusLabel.setText("Congratulations! You are now a member of Team (" + team.getTeamId() + ").");
            fetchTeamInformation();
        }
    }

    // current student rejects a team join proposal
    @FXML
    private void handleRejectTeamProposal(ActionEvent actionEvent) throws SQLException {

        createTeamStatusLabel.setText(null);
        teamRemovalStatusLabel.setText(null);

        TeamProposal teamProposal = teamProposalTableView.getSelectionModel().getSelectedItem();
        if (teamProposal == null){
            proposalStatusLabel.setText("You must select a team to confirm/reject.");
            return;
        }

        Team team = new Team(teamProposal);

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        if (teamOp.rejectTeam(student.getStudentId(), team)){
            proposalStatusLabel.setText("Selected Team (" + team.getTeamId() + ") Proposal is rejected.");
            fetchTeamInformation();
        }
    }

    /*******************************************
     *  "Apply for Research" page related methods
     * ******************************************/

     private void populateResearchTeamInformation() throws SQLException {
         researchTeamIdLabel.setText(team1IdLabel.getText());
         researchTeamNameLabel.setText(team1NameLabel.getText());
         researchTeamSupervisorLabel.setText(team1SupervisorLabel.getText());

         TableColumn<Member, Integer> memberSerial = new TableColumn<>("Serial");
         memberSerial.setCellValueFactory(new PropertyValueFactory("serial"));

         TableColumn<Member, Integer> memberStudentId = new TableColumn<>("Student ID");
         memberStudentId.setCellValueFactory(new PropertyValueFactory("studentId"));

         TableColumn<Member, Integer> memberName = new TableColumn<>("Name");
         memberName.setCellValueFactory(new PropertyValueFactory("name"));

         TableColumn<Member, Integer> memberConfirmation = new TableColumn<>("Status");
         memberConfirmation.setCellValueFactory(new PropertyValueFactory("confirmationStatus"));

         researchTeamTableView.setItems(memberListTeam1);
         researchTeamTableView.getColumns().addAll(memberSerial, memberStudentId, memberName, memberConfirmation);
     }

     private void fetchApplicationInformation() throws SQLException {
         ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();
         researchApplicationList = researchOp.getAllApplicationsOf(student.getStudentId());
     }

     private void populateApplicationTableView(){
         TableColumn < ResearchApplication, Integer > id = new TableColumn<>("ID");
         id.setCellValueFactory(new PropertyValueFactory("applicationId"));

         TableColumn < ResearchApplication, LocalDate > date = new TableColumn<>("Date");
         date.setCellValueFactory(new PropertyValueFactory("applicationDate"));

         TableColumn < ResearchApplication, String > topic = new TableColumn<>("Topic");
         topic.setCellValueFactory(new PropertyValueFactory("applicationTitle"));

         TableColumn < ResearchApplication, Integer > teamId = new TableColumn<>("Team ID");
         teamId.setCellValueFactory(new PropertyValueFactory("applicationTeamId"));

         TableColumn < ResearchApplication, String > status = new TableColumn<>("Status");
         status.setCellValueFactory(new PropertyValueFactory("applicationStatus"));

         System.out.println(researchApplicationList.size());
         researchApplicationTableView.setItems(researchApplicationList);
         researchApplicationTableView.getColumns().addAll(id, date, topic, teamId, status);
     }

    @FXML
    private void displayResearchApplicationInformation(ActionEvent actionEvent) throws SQLException {
         if (researchApplicationTableView.getSelectionModel().getSelectedItem() == null){
            applicationHistoryStatusLabel.setText("Select a row first!");
            return;
         }
         ResearchApplication researchApplication = researchApplicationTableView.getSelectionModel().getSelectedItem();
         researchTopicField.setText(researchApplication.getApplicationTitle());
         TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();

         researchTeamComboBox.setValue(teamOp.getTeam(researchApplication.getApplicationTeamId()));
         researchHypothesisArea.setText(researchApplication.getApplicationHypothesis());
         researchCommentField.setText(researchApplication.getApplicationComment());
         if (researchApplication.getApplicationSemesterNeeded() == 1)
             semesterRadioButton1.fire();
         else
             semesterRadioButton2.fire();
         researchTeamIdLabel.setText(String.valueOf(researchApplication.getApplicationTeamId()));

         if (teamOp.getTeam(researchApplication.getApplicationTeamId()).getTeamName() != null)
                researchTeamNameLabel.setText(teamOp.getTeam(researchApplication.getApplicationTeamId()).getTeamName());

         researchTeamSupervisorLabel.setText(teamOp.getTeam(researchApplication.getApplicationTeamId()).getTeamSupervisorId());
         researchApplicationStatusLabel.setText(researchApplication.getApplicationStatus());

         if (researchApplication.getApplicationStatus().equals("Chairman_Accepted")){
             researchSupervisorStatusLabel.setTextFill(Color.BLUE);
             researchSupervisorStatusLabel.setText("Endorsed");

             researchApplicationStatusLabel.setTextFill(Color.GREEN);
             researchApplicationStatusLabel.setText("Accepted");
         }

         else if (researchApplication.getApplicationStatus().equals("Chairman_Rejected")){
            researchSupervisorStatusLabel.setTextFill(Color.BLUE);
            researchSupervisorStatusLabel.setText("Endorsed");

            researchApplicationStatusLabel.setTextFill(Color.RED);
            researchApplicationStatusLabel.setText("Rejected");
        }

         else if (researchApplication.getApplicationStatus().equals("Supervisor_Accepted")){
             researchSupervisorStatusLabel.setTextFill(Color.BLUE);
             researchSupervisorStatusLabel.setText("Endorsed");

             researchApplicationStatusLabel.setTextFill(Color.ORANGE);
             researchApplicationStatusLabel.setText("Pending");
         }

         else if (researchApplication.getApplicationStatus().equals("Supervisor_Rejected")){
             researchSupervisorStatusLabel.setTextFill(Color.RED);
             researchSupervisorStatusLabel.setText("Rejected");

             researchApplicationStatusLabel.setTextFill(Color.RED);
             researchApplicationStatusLabel.setText("Dismissed");
         }

         else{
             researchSupervisorStatusLabel.setTextFill(Color.ORANGE);
             researchSupervisorStatusLabel.setText("Pending");

             researchApplicationStatusLabel.setTextFill(Color.ORANGE);
             researchApplicationStatusLabel.setText("Pending");
         }
    }

    @FXML
    private void handleApplyForResearch(ActionEvent actionEvent) throws SQLException {
         LocalDate currentDate = LocalDate.now();
         if (applicationDeadline != null && currentDate.isAfter(applicationDeadline)){
             researchApplicationFormStatusLabel.setText("Application period is over. Please try again in next semester.");
             return;
         }

         // can not make more than 3 applications
        if (researchApplicationList.size() >= 3){
            researchApplicationFormStatusLabel.setText("3 applications are sent on your behalf. You can not send more.");
            return;
        }

        // must be part of a team to apply
        if (researchTeamComboBox.getSelectionModel().getSelectedItem() == null){
            researchApplicationFormStatusLabel.setText("You have to be part of a team to apply. Visit Team page to form or Join a team.");
            return;
        }

        int applicationTeamId = researchTeamComboBox.getSelectionModel().getSelectedItem().getTeamId();
        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();

        // all member of the team must confirm
        if (!teamOp.hasConfirmedAll(applicationTeamId)){
            researchApplicationFormStatusLabel.setText("All member of your team has to confirm before you can apply.");
            return;
        }

            // required fields being checked here
        if (researchTopicField.getText().length() == 0){
            researchApplicationFormStatusLabel.setText("Research topic can not be empty.");
            return;
        }

        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();

        int applicationId = researchOp.getMaxPrimaryKey() + 1;

        LocalDate applicationDate = LocalDate.now();

        String applicationTitle = researchTopicField.getText();
        int applicationSemesterNeeded = 0; // depends on which radio button is checked(1, 2)
        if (semesterRadioButton1.isSelected())
            applicationSemesterNeeded = 1;
        else
            applicationSemesterNeeded = 2;
        System.out.println(applicationSemesterNeeded);

        String applicationHypothesis = researchHypothesisArea.getText();
        //System.out.println(applicationHypothesis);

        String applicationComment = researchCommentField.getText();
        String applicationStatus = "Pending";

        ResearchApplication researchApplication = new ResearchApplication(applicationId, applicationDate, applicationTitle,
                applicationSemesterNeeded, applicationHypothesis, applicationComment, applicationTeamId, applicationStatus);

        if (researchOp.insertResearchApplication(researchApplication)){
            researchApplicationList.add(researchApplication);
            researchApplicationFormStatusLabel.setText("Application sent successfully! Do not forget to check confirmation status.");
        }
        else{
            researchApplicationFormStatusLabel.setText("There was an error sending your application. Please retry.");
        }

        clearApplicationStats(null);
    }

    @FXML
    private void handleResetResearchPageFields(ActionEvent actionEvent) {
         researchTopicField.setText(null);
         researchHypothesisArea.setText(null);
         researchCommentField.setText(null);
         
    }

    @FXML
    private void clearApplicationStats(ActionEvent actionEvent) {
        researchTeamIdLabel.setText(null);
        researchTeamNameLabel.setText(null);
        researchTeamSupervisorLabel.setText(null);
        researchSupervisorStatusLabel.setText(null);
        researchApplicationStatusLabel.setText(null);
        applicationHistoryStatusLabel.setText(null);
    }
}
