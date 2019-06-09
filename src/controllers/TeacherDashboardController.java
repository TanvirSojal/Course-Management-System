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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.Course;
import objects.Member;
import objects.ResearchApplication;
import objects.Team;
import objects.dbinterfaces.*;
import users.Student;
import users.Teacher;
import users.dbinterfaces.StudentDatabaseOperation;
import users.dbinterfaces.TeacherDatabaseOperation;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class TeacherDashboardController implements Initializable {

    private Teacher teacher; // currently logged in Teacher

    @FXML
    private Label teacherNameLabel;
    @FXML
    private Label teacherIdLabel;

    // anchor panes
    @FXML
    private AnchorPane teacherDashboardHomePane;
    @FXML
    private AnchorPane teacherInformationPane;
    @FXML
    private AnchorPane teacherResearchProposalPane;

    private LocalDate evaluationDeadline;

    // teacher information page variables
    @FXML
    private Label evaluationDeadlineLabel;
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

    // research proposal page variables
    @FXML
    private ObservableList <ResearchApplication> researchProposalList;
    @FXML
    private TableView <ResearchApplication> researchProposalTableView;

    @FXML
    private ObservableList <Member> researchTeamMemberList;
    @FXML
    private TableView <Member> researchTeamMemberTableView;
    @FXML
    private Label researchProposalStatusLabel;
    @FXML
    private Label researchTeamNameLabel;
    @FXML
    private Label researchTeamIdLabel;
    @FXML
    private Label researchTopicLabel;
    @FXML
    private Label researchHypothesisLabel;
    @FXML
    private Label researchCommentLabel;
    @FXML
    private Label researchTeamRequiredSemesterLabel;
    
    // "My Research Team" page related variables
    @FXML
    private AnchorPane teacherResearchStudentPane;

    @FXML
    private ObservableList <ResearchApplication> approvedProposalList;
    @FXML
    private TableView <ResearchApplication> approvedProposalTableView;

    @FXML
    private Label approvedProposalStatusLabel;

    @FXML
    private ObservableList <Member> approvedTeamMemberList;
    @FXML
    private TableView <Member> approvedTeamMemberTableView;


    @FXML
    private Label approvedTeamRequiredSemesterLabel;
    @FXML
    private Label approvedTeamNameLabel;
    @FXML
    private Label approvedTeamIdLabel;
    @FXML
    private Label supervisingStudentCountLabel;
    @FXML
    private Label supervisingTeamCountLabel;

    private int supervisingStudentCount;
    private int supervisingTeamCount;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigateToTeacherDashboard(null); // setting "Teacher Dashboard" the default page
        try{
            // fetching data from database
            fetchTeacherInformationFromDatabase();
            fetchResearchProposalsFromDatabase();
            fetchApprovedResearchProposalsFromDatabase();
            fetchDeadlineInformationFromDatabase();
            System.out.println(teacher);

            // populating GUI elements
            populateTeacherInformationBar();
            populateResearchProposalTableView();
            populateResearchTeamMemberTableView();

            populateApprovedProposalTableView();
            populateApprovedTeamMemberTableView();
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
        teacherResearchProposalPane.setVisible(false);
        teacherResearchStudentPane.setVisible(false);

        teacherDashboardHomePane.setVisible(true);
    }

    @FXML
    private void navigateToTeacherInformation(ActionEvent actionEvent) {
        teacherInformationUpdationStatusLabel.setText(null);

        teacherDashboardHomePane.setVisible(false);
        teacherResearchProposalPane.setVisible(false);
        teacherResearchStudentPane.setVisible(false);

        teacherInformationPane.setVisible(true);
    }

    @FXML
    private void navigateToTeacherResearchProposalPane(ActionEvent actionEvent) {
        researchProposalStatusLabel.setText(null);
        clearProposalDetails();

        teacherDashboardHomePane.setVisible(false);
        teacherInformationPane.setVisible(false);
        teacherResearchStudentPane.setVisible(false);

        teacherResearchProposalPane.setVisible(true);
    }

    @FXML
    private void navigateToTeacherResearchStudentsPane(ActionEvent actionEvent){
        approvedProposalStatusLabel.setText(null);
        clearApprovedProposalDetails();

        teacherDashboardHomePane.setVisible(false);
        teacherInformationPane.setVisible(false);
        teacherResearchProposalPane.setVisible(false);

        teacherResearchStudentPane.setVisible(true);
    }

    /******************************************************
     *  "Teacher Information" page related method are below
     ******************************************************/

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

    /******************************************************
     *  "Research Proposal" page related method are below
     *****************************************************/

    private void fetchDeadlineInformationFromDatabase() throws SQLException {
        DeadlineDatabaseOperation deadlineOp = new DeadlineDatabaseOperationImplementation();
        evaluationDeadline = deadlineOp.getApplicationEvaluationDeadline();

        // setting last changed deadlines on the labels
        if (evaluationDeadline != null)
            evaluationDeadlineLabel.setText(evaluationDeadline.toString());
        else
            evaluationDeadlineLabel.setText(null);
    }

    private void fetchResearchProposalsFromDatabase() throws SQLException {
        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();
        researchProposalList = researchOp.getAllMentionedApplications(teacher.getTeacherInitial());
        researchProposalTableView.setItems(researchProposalList); // setting proposals to corresponding table

    }

    private void populateResearchProposalTableView(){
        TableColumn < ResearchApplication, Integer > id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory("applicationId"));

        TableColumn < ResearchApplication, LocalDate> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory("applicationDate"));

        TableColumn < ResearchApplication, String > topic = new TableColumn<>("Topic");
        topic.setCellValueFactory(new PropertyValueFactory("applicationTitle"));

        TableColumn < ResearchApplication, Integer > teamId = new TableColumn<>("Team ID");
        teamId.setCellValueFactory(new PropertyValueFactory("applicationTeamId"));

        TableColumn < ResearchApplication, String > status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory("applicationStatus"));

        System.out.println(researchProposalList.size());
        //researchProposalTableView.setItems(researchProposalList);
        researchProposalTableView.getColumns().addAll(id, date, topic, teamId, status);
    }
    
    private void clearProposalDetails(){
        researchTeamIdLabel.setText(null);
        researchTeamNameLabel.setText(null);
        researchTeamRequiredSemesterLabel.setText(null);
        researchTopicLabel.setText(null);
        researchHypothesisLabel.setText(null);
        researchCommentLabel.setText(null);
        researchTeamMemberList.clear();
    }

    @FXML
    private void displayResearchApplicationInformation(ActionEvent actionEvent) throws SQLException {
        if (researchProposalTableView.getSelectionModel().getSelectedItem() == null){
            researchProposalStatusLabel.setText("Select a row first!");
            return;
        }

        researchProposalStatusLabel.setText(null); // clearing any previous message

        ResearchApplication researchApplication = researchProposalTableView.getSelectionModel().getSelectedItem();
        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        Team team = teamOp.getTeam(researchApplication.getApplicationTeamId());


        // populating team information
        researchTeamIdLabel.setText(String.valueOf(team.getTeamId()));
        researchTeamNameLabel.setText(team.getTeamName());

        // --> getting members of the team

        //researchTeamMemberTableView.getColumns().removeAll();
        getMembers(team); // populating researchTeamMemberTableView with selected team
        System.out.println(researchTeamMemberList.size());
        //populateResearchTeamMemberTableView();
        // populating research attribute labels
        researchTeamRequiredSemesterLabel.setText(String.valueOf(researchApplication.getApplicationSemesterNeeded()));
        researchTopicLabel.setText(researchApplication.getApplicationTitle());
        researchHypothesisLabel.setText(researchApplication.getApplicationHypothesis());
        researchCommentLabel.setText(researchApplication.getApplicationComment());
    }

    private void getMembers(Team team) throws SQLException {
        researchTeamMemberList = FXCollections.observableArrayList();

        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        RegistrationDatabaseOperation regOp = new RegistrationDatabaseOperationImplementation();

        Course ResearchMethodology = courseOp.getCourse(1);

        StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();
        Student student1 = studentOp.getStudent(team.getTeam1stMemberId());
        Student student2 = studentOp.getStudent(team.getTeam2ndMemberId());
        Student student3 = studentOp.getStudent(team.getTeam3rdMemberId());

        if (student1 != null){
            boolean hasRegisteredMember = regOp.exists(ResearchMethodology, student1);
            String registeredForResearchMember;
            if (hasRegisteredMember)
                registeredForResearchMember = "Yes";
            else
                registeredForResearchMember = "No";
            Member member1 = new Member(1, student1.getStudentId(), student1.getStudentName(), registeredForResearchMember, team.getTeam1stMemberStatus());
            researchTeamMemberList.add(member1);
        }

        if (student2 != null){
            boolean hasRegisteredMember = regOp.exists(ResearchMethodology, student2);
            String registeredForResearchMember;
            if (hasRegisteredMember)
                registeredForResearchMember = "Yes";
            else
                registeredForResearchMember = "No";
            Member member2 = new Member(1, student2.getStudentId(), student2.getStudentName(), registeredForResearchMember, team.getTeam2ndMemberStatus());
            researchTeamMemberList.add(member2);
        }

        if (student3 != null){
            boolean hasRegisteredMember = regOp.exists(ResearchMethodology, student2);
            String registeredForResearchMember;
            if (hasRegisteredMember)
                registeredForResearchMember = "Yes";
            else
                registeredForResearchMember = "No";
            Member member3 = new Member(1, student3.getStudentId(), student3.getStudentName(), registeredForResearchMember, team.getTeam3rdMemberStatus());
            researchTeamMemberList.add(member3);
        }
        researchTeamMemberTableView.setItems(researchTeamMemberList);
    }

    private void populateResearchTeamMemberTableView(){
        researchTeamMemberList = FXCollections.observableArrayList();
        TableColumn<Member, Integer> memberSerial = new TableColumn<>("Serial");
        memberSerial.setCellValueFactory(new PropertyValueFactory("serial"));

        TableColumn<Member, Integer> memberStudentId = new TableColumn<>("Student ID");
        memberStudentId.setCellValueFactory(new PropertyValueFactory("studentId"));

        TableColumn<Member, Integer> memberName = new TableColumn<>("Name");
        memberName.setCellValueFactory(new PropertyValueFactory("name"));

        researchTeamMemberTableView.setItems(researchTeamMemberList);
        researchTeamMemberTableView.getColumns().addAll(memberSerial, memberStudentId, memberName);
    }

    @FXML
    private void handleEndorseResearchApplication(ActionEvent actionEvent) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        if (evaluationDeadline != null && currentDate.isAfter(evaluationDeadline)){ // check for deadline
            researchProposalStatusLabel.setText("Evaluation deadline is over. Can not endorse/reject application now.");
            return;
        }

        ResearchApplication researchApplication = researchProposalTableView.getSelectionModel().getSelectedItem();
        if (researchApplication == null){
            researchProposalStatusLabel.setText("Select an application first!");
            return;
        }

        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();

        if (researchOp.isEvaluatedBySupervisor(researchApplication.getApplicationId()) || researchOp.isEvaluatedByChairman(researchApplication.getApplicationId())){
            researchProposalStatusLabel.setText("Can not re-evaluate already evaluated application!");
            return;
        }

        if (researchOp.endorseBySupervisor(researchApplication)){
            fetchResearchProposalsFromDatabase();
            researchProposalStatusLabel.setText("Endorsement of Team (" + researchApplication.getApplicationTeamId() + ") successful");
        }
        else{
            researchProposalStatusLabel.setText("Some error occurred!");
        }

    }

    @FXML
    private void handleRejectResearchApplication(ActionEvent actionEvent) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        if (evaluationDeadline != null && currentDate.isAfter(evaluationDeadline)){ // check for deadline
            researchProposalStatusLabel.setText("Evaluation deadline is over. Can not endorse/reject application now.");
            return;
        }

        ResearchApplication researchApplication = researchProposalTableView.getSelectionModel().getSelectedItem();
        if (researchApplication == null){
            researchProposalStatusLabel.setText("Select an application first!");
            return;
        }

        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();

        //System.out.println(researchOp.isEvaluatedBySupervisor(researchApplication.getApplicationId()));
        //System.out.println(researchOp.isEvaluatedByChairman(researchApplication.getApplicationId()));

        if (researchOp.isEvaluatedBySupervisor(researchApplication.getApplicationId()) || researchOp.isEvaluatedByChairman(researchApplication.getApplicationId())){
            researchProposalStatusLabel.setText("Can not re-evaluate already evaluated application!");
            return;
        }

        if (researchOp.rejectBySupervisor(researchApplication)){
            fetchResearchProposalsFromDatabase();
            researchProposalStatusLabel.setText("Rejection of Team (" + researchApplication.getApplicationTeamId() + ") successful");
        }
        else{
            researchProposalStatusLabel.setText("Some error occurred!");
        }
    }

    /******************************************************
     *  "My Research Students" page related method are below
     *****************************************************/

    private void fetchApprovedResearchProposalsFromDatabase() throws SQLException {
        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();
        approvedProposalList = researchOp.getAllApprovedByChairmanApplicationsOf(teacher.getTeacherInitial());
        approvedProposalTableView.setItems(approvedProposalList); // setting proposals to corresponding table
        supervisingStudentCount = researchOp.getSupervisingStudentCount(teacher.getTeacherInitial());
        supervisingTeamCount = researchOp.getSupervisingTeamCount(teacher.getTeacherInitial());

        supervisingStudentCountLabel.setText(String.valueOf(supervisingStudentCount));
        supervisingTeamCountLabel.setText(String.valueOf(supervisingTeamCount));
    }

    private void populateApprovedProposalTableView(){
        TableColumn < ResearchApplication, Integer > id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory("applicationId"));

        TableColumn < ResearchApplication, LocalDate> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory("applicationDate"));

        TableColumn < ResearchApplication, String > topic = new TableColumn<>("Topic");
        topic.setCellValueFactory(new PropertyValueFactory("applicationTitle"));

        TableColumn < ResearchApplication, Integer > teamId = new TableColumn<>("Team ID");
        teamId.setCellValueFactory(new PropertyValueFactory("applicationTeamId"));

        TableColumn < ResearchApplication, String > status = new TableColumn<>("Status");
        status.setCellValueFactory(new PropertyValueFactory("applicationStatus"));

        System.out.println(researchProposalList.size());
        //researchProposalTableView.setItems(researchProposalList);
        approvedProposalTableView.getColumns().addAll(id, date, topic, teamId, status);
    }

    @FXML
    private void clearApprovedProposalDetails(){
        approvedTeamIdLabel.setText(null);
        approvedTeamNameLabel.setText(null);
        approvedTeamRequiredSemesterLabel.setText(null);
        approvedProposalStatusLabel.setText(null);
        approvedTeamMemberTableView.getItems().removeAll();
    }

    @FXML
    private void displayApprovedTeamInformation(ActionEvent actionEvent) throws SQLException {
        if (approvedProposalTableView.getSelectionModel().getSelectedItem() == null){
            approvedProposalStatusLabel.setText("Select a row first!");
            return;
        }

        approvedProposalStatusLabel.setText(null); // clearing any previous message

        ResearchApplication researchApplication = approvedProposalTableView.getSelectionModel().getSelectedItem();
        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        Team team = teamOp.getTeam(researchApplication.getApplicationTeamId());


        // populating team information
        approvedTeamIdLabel.setText(String.valueOf(team.getTeamId()));
        approvedTeamNameLabel.setText(team.getTeamName());

        // --> getting members of the team

        //researchTeamMemberTableView.getColumns().removeAll();
        getApprovedMembers(team); // populating researchTeamMemberTableView with selected team
        //populateResearchTeamMemberTableView();
        // populating research attribute labels
        approvedTeamRequiredSemesterLabel.setText(String.valueOf(researchApplication.getApplicationSemesterNeeded()));
    }

    private void getApprovedMembers(Team team) throws SQLException {
        approvedTeamMemberList = FXCollections.observableArrayList();

        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        RegistrationDatabaseOperation regOp = new RegistrationDatabaseOperationImplementation();

        Course ResearchMethodology = courseOp.getCourse(1);

        StudentDatabaseOperation studentOp = new StudentDatabaseOperationImplementation();
        Student student1 = studentOp.getStudent(team.getTeam1stMemberId());
        Student student2 = studentOp.getStudent(team.getTeam2ndMemberId());
        Student student3 = studentOp.getStudent(team.getTeam3rdMemberId());

        if (student1 != null){
            boolean hasRegisteredMember = regOp.exists(ResearchMethodology, student1);
            String registeredForResearchMember;
            if (hasRegisteredMember)
                registeredForResearchMember = "Yes";
            else
                registeredForResearchMember = "No";
            Member member1 = new Member(1, student1.getStudentId(), student1.getStudentName(), registeredForResearchMember, team.getTeam1stMemberStatus());
            approvedTeamMemberList.add(member1);
        }

        if (student2 != null){
            boolean hasRegisteredMember = regOp.exists(ResearchMethodology, student2);
            String registeredForResearchMember;
            if (hasRegisteredMember)
                registeredForResearchMember = "Yes";
            else
                registeredForResearchMember = "No";
            Member member2 = new Member(1, student2.getStudentId(), student2.getStudentName(), registeredForResearchMember, team.getTeam2ndMemberStatus());
            approvedTeamMemberList.add(member2);
        }

        if (student3 != null){
            boolean hasRegisteredMember = regOp.exists(ResearchMethodology, student2);
            String registeredForResearchMember;
            if (hasRegisteredMember)
                registeredForResearchMember = "Yes";
            else
                registeredForResearchMember = "No";
            Member member3 = new Member(1, student3.getStudentId(), student3.getStudentName(), registeredForResearchMember, team.getTeam3rdMemberStatus());
            approvedTeamMemberList.add(member3);
        }
        approvedTeamMemberTableView.setItems(approvedTeamMemberList);
    }

    private void populateApprovedTeamMemberTableView(){
        approvedTeamMemberList = FXCollections.observableArrayList();
        TableColumn<Member, Integer> memberSerial = new TableColumn<>("Serial");
        memberSerial.setCellValueFactory(new PropertyValueFactory("serial"));

        TableColumn<Member, Integer> memberStudentId = new TableColumn<>("Student ID");
        memberStudentId.setCellValueFactory(new PropertyValueFactory("studentId"));

        TableColumn<Member, Integer> memberName = new TableColumn<>("Name");
        memberName.setCellValueFactory(new PropertyValueFactory("name"));

        approvedTeamMemberTableView.setItems(approvedTeamMemberList);
        approvedTeamMemberTableView.getColumns().addAll(memberSerial, memberStudentId, memberName);
    }
}
