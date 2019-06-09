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
import objects.dbinterfaces.*;
import users.Chairman;
import users.Student;
import users.Teacher;
import users.dbinterfaces.ChairmanDatabaseOperation;
import users.dbinterfaces.StudentDatabaseOperation;
import users.dbinterfaces.TeacherDatabaseOperation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ChairmanDashboardController implements Initializable {
    private Chairman chairman; // currently logged in Chairman

    @FXML
    private Label chairmanIdLabel;
    @FXML
    private Label chairmanNameLabel;

    @FXML
    private AnchorPane chairmanDashboardHomePane;

    // Information page related variables

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

    // Research Proposal page related variables

    @FXML
    private AnchorPane chairmanResearchProposalPane;
    @FXML
    private ObservableList<ResearchApplication> researchProposalList;
    @FXML
    private TableView<ResearchApplication> researchProposalTableView;

    @FXML
    ObservableList <Member> researchTeamMemberList;
    @FXML
    private TableView <Member> researchTeamMemberTableView;
    @FXML
    private Label researchProposalStatusLabel;
    @FXML
    private Label researchTeamNameLabel;
    @FXML
    private Label researchTeamIdLabel;
    @FXML
    private Label researchTeamSupervisorLabel;
    @FXML
    private Label researchTopicLabel;
    @FXML
    private Label researchHypothesisLabel;
    @FXML
    private Label researchCommentLabel;
    @FXML
    private Label researchTeamRequiredSemesterLabel;

    // Administration page related Variables
    @FXML
    private AnchorPane chairmanAdministrationPane;
    @FXML
    private Label deadlineStatusLabel;
    @FXML
    private DatePicker applicationDeadlineDatePicker;
    @FXML
    private DatePicker evaluationDeadlineDatePicker;

    @FXML
    private ObservableList <Student> defaulterStudentList;
    @FXML
    private TableView <Student> defaulterStudentTableView;

    @FXML
    private ObservableList <Teacher> defaulterTeacherList;
    @FXML
    private TableView <Teacher> defaulterTeacherTableView;

    private LocalDate applicationDeadline;
    private LocalDate evaluationDeadline;

    @FXML
    private Label currentApplicationDeadlineLabel;
    @FXML
    private Label currentEvaluationDeadlineLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigateToChairmanDashboard(null); // making default page
        try{
            fetchChairmanInformationFromDatabase();
            fetchResearchProposalsFromDatabase();
            fetchDeadlineInformationFromDatabase();
            fetchDefaulterStudentInformationFromDatabase();
            fetchDefaulterTeacherInformationFromDatabase();

            populateChairmanInformationBar();
            populateResearchProposalTableView();
            populateResearchTeamMemberTableView();
            populateDefaulterStudentTableView();
            populateDefaulterTeacherTableView();

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
        ChairmanDatabaseOperation chairmanOp = new ChairmanDatabaseOperationImplementation();
        chairman = chairmanOp.getChairman(chairmanId);
        System.out.println(chairman);

        chairmanInformationNameLabel.setText(chairman.getChairmanName());
        chairmanInformationIdLabel.setText(chairman.getChairmanInitial());
        chairmanInformationOfficeLabel.setText("Department of CSE, QV University");

        chairmanInformationEmailField.setText(chairman.getChairmanEmail());
        chairmanInformationBloodGroupField.setText(chairman.getChairmanBloodGroup());
        chairmanInformationContactNumberField.setText(chairman.getChairmanContactNumber());
        chairmanInformationAddressField.setText(chairman.getChairmanAddress());
    }

    @FXML
    private void populateChairmanInformationBar(){
        chairmanIdLabel.setText(chairman.getChairmanInitial());
        chairmanNameLabel.setText(chairman.getChairmanName());
    }

    /****************************************************
    *   Navigation related methods
    *****************************************************/

    @FXML
    private void navigateToChairmanDashboard(ActionEvent actionEvent) {
        chairmanInformationPane.setVisible(false);
        chairmanAdministrationPane.setVisible(false);
        chairmanResearchProposalPane.setVisible(false);

        chairmanDashboardHomePane.setVisible(true);
    }

    @FXML
    private void navigateToChairmanInformation(ActionEvent actionEvent) {
        chairmanInformationUpdationStatusLabel.setText(null);
        chairmanDashboardHomePane.setVisible(false);
        chairmanAdministrationPane.setVisible(false);
        chairmanResearchProposalPane.setVisible(false);

        chairmanInformationPane.setVisible(true);
    }

    @FXML
    private void navigateToChairmanResearchProposalPane(ActionEvent actionEvent) {
        clearProposalDetails();

        chairmanDashboardHomePane.setVisible(false);
        chairmanInformationPane.setVisible(false);
        chairmanAdministrationPane.setVisible(false);

        chairmanResearchProposalPane.setVisible(true);
    }

    @FXML
    private void navigateToChairmanAdministrationPane(ActionEvent actionEvent) {
        chairmanInformationUpdationStatusLabel.setText(null);
        chairmanDashboardHomePane.setVisible(false);
        chairmanInformationPane.setVisible(false);
        chairmanResearchProposalPane.setVisible(false);

        deadlineStatusLabel.setText(null);
        chairmanAdministrationPane.setVisible(true);
    }

    /****************************************************
     *   Information page related methods
     *****************************************************/

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


    /****************************************************
     *   Administration Page related methods
     *****************************************************/

    private void fetchDeadlineInformationFromDatabase() throws SQLException {
        DeadlineDatabaseOperation deadlineOp = new DeadlineDatabaseOperationImplementation();
        applicationDeadline = deadlineOp.getApplicationSubmissionDeadline();
        evaluationDeadline = deadlineOp.getApplicationEvaluationDeadline();

        // setting last changed deadline on to date-pickers
        applicationDeadlineDatePicker.setValue(applicationDeadline);
        evaluationDeadlineDatePicker.setValue(evaluationDeadline);

        // setting last changed deadlines on the labels
        if (applicationDeadline != null)
            currentApplicationDeadlineLabel.setText(applicationDeadline.toString());
        else
            currentApplicationDeadlineLabel.setText(null);

        if (evaluationDeadline != null)
            currentEvaluationDeadlineLabel.setText(evaluationDeadline.toString());
        else
            currentEvaluationDeadlineLabel.setText(null);
    }

    private void fetchDefaulterStudentInformationFromDatabase() throws SQLException {
        defaulterStudentList = FXCollections.observableArrayList();
        defaulterStudentTableView.setItems(defaulterStudentList);

        LocalDate currentDate = LocalDate.now();
        if (applicationDeadline == null || !currentDate.isAfter(applicationDeadline))
            return;

        CourseDatabaseOperation courseOp = new CourseDatabaseOperationImplementation();
        Course ResearchMethodology = courseOp.getCourse(1);

        RegistrationDatabaseOperation regOp = new RegistrationDatabaseOperationImplementation();
        ObservableList <Student> researchStudentList = regOp.getAllRegisteredStudents(ResearchMethodology);



        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();
        ObservableList <ResearchApplication> researchApplicationList = researchOp.getAllApplications();

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();

        for (Student student : researchStudentList){
            boolean flag = false;
            for (ResearchApplication researchApplication : researchApplicationList){
                int teamId = researchApplication.getApplicationTeamId();
                if (teamOp.isMemberOf(teamId, student.getStudentId())){
                    flag = true;
                    break;
                }
            }
            if (!flag)
                defaulterStudentList.add(student);
        }
    }

    private void fetchDefaulterTeacherInformationFromDatabase() throws SQLException {
        defaulterTeacherList = FXCollections.observableArrayList();
        defaulterTeacherTableView.setItems(defaulterTeacherList);

        LocalDate currentDate = LocalDate.now();
        if (evaluationDeadline == null  || !currentDate.isAfter(evaluationDeadline))
            return;

        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();
        defaulterTeacherList = researchOp.getAllMentionedTeachersOfUnevaluatedApplications();
        defaulterTeacherTableView.setItems(defaulterTeacherList);
    }

    private void populateDefaulterStudentTableView(){
        TableColumn <Student, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory("studentId"));

        TableColumn <Student, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory("studentName"));

        TableColumn <Student, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory("studentEmail"));

        TableColumn <Student, String> phone = new TableColumn<>("Contact Number");
        phone.setCellValueFactory(new PropertyValueFactory("studentContactNumber"));

        defaulterStudentTableView.getColumns().addAll(id, name, email, phone);
    }

    private void populateDefaulterTeacherTableView(){
        TableColumn <Teacher, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory("teacherInitial"));

        TableColumn <Teacher, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory("teacherName"));

        TableColumn <Teacher, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory("teacherEmail"));

        TableColumn <Teacher, String> phone = new TableColumn<>("Contact Number");
        phone.setCellValueFactory(new PropertyValueFactory("teacherContactNumber"));

        defaulterTeacherTableView.getColumns().addAll(id, name, email, phone);
    }


    @FXML
    private void handleApplicationDeadlineUpdate(ActionEvent actionEvent) throws SQLException {
        LocalDate applicationSubmissionDeadline = applicationDeadlineDatePicker.getValue();
        if (applicationSubmissionDeadline == null){
            deadlineStatusLabel.setText("Set a date first!");
            return;
        }

        DeadlineDatabaseOperation deadlineOp = new DeadlineDatabaseOperationImplementation();
        if (applicationDeadline == null){
            // in database, the id will auto increment, so inserting any number as id placeholder is good
            Deadline deadline = new Deadline(1, "Application_Submission", applicationSubmissionDeadline);
            if (deadlineOp.insertDeadline(deadline)){
                deadlineStatusLabel.setText("Application submission deadline for students updated successfully.");
                fetchDeadlineInformationFromDatabase();
                fetchDefaulterStudentInformationFromDatabase();
            }
            else{
                deadlineStatusLabel.setText("Some error occurred!");
            }
        }
        else{
            if (deadlineOp.updateApplicationDeadline(applicationSubmissionDeadline)){
                deadlineStatusLabel.setText("Application submission deadline for students updated successfully.");
                fetchDeadlineInformationFromDatabase();
                fetchDefaulterStudentInformationFromDatabase();
            }
            else{
                deadlineStatusLabel.setText("Some error occurred!");
            }
        }

    }

    @FXML
    private void handleEvaluationDeadlineUpdate(ActionEvent actionEvent) throws SQLException {
        LocalDate applicationEvaluationDeadline = evaluationDeadlineDatePicker.getValue();
        if (applicationEvaluationDeadline == null){
            deadlineStatusLabel.setText("Set a date first!");
            return;
        }

        DeadlineDatabaseOperation deadlineOp = new DeadlineDatabaseOperationImplementation();
        if (evaluationDeadline == null){
            // in database, the id will auto increment, so inserting any number as id placeholder is good
            Deadline deadline = new Deadline(2, "Application_Evaluation", applicationEvaluationDeadline);
            if (deadlineOp.insertDeadline(deadline)){
                deadlineStatusLabel.setText("Application evaluation deadline for teachers updated successfully.");
                fetchDeadlineInformationFromDatabase();
                fetchDefaulterTeacherInformationFromDatabase();
            }
            else{
                deadlineStatusLabel.setText("Some error occurred!");
            }
        }

        else{
            if (deadlineOp.updateEvaluationDeadline((applicationEvaluationDeadline))){
                deadlineStatusLabel.setText("Application evaluation deadline for teachers updated successfully.");
                fetchDeadlineInformationFromDatabase();
                fetchDefaulterTeacherInformationFromDatabase();
            }
            else{
                deadlineStatusLabel.setText("Some error occurred!");
            }
        }
    }


    /****************************************************
     *   Research Proposal Page related methods
     *****************************************************/

    private void fetchResearchProposalsFromDatabase() throws SQLException {
        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();
        researchProposalList = researchOp.getAllEndorsedBySupervisorApplications();
        researchProposalTableView.setItems(researchProposalList); // setting proposals to corresponding table
    }

    private void clearProposalDetails(){
        researchTeamIdLabel.setText(null);
        researchTeamNameLabel.setText(null);
        researchTeamSupervisorLabel.setText(null);
        researchTeamRequiredSemesterLabel.setText(null);
        researchTopicLabel.setText(null);
        researchHypothesisLabel.setText(null);
        researchCommentLabel.setText(null);
        researchProposalStatusLabel.setText(null);
        researchTeamMemberList.clear();
    }

    private void populateResearchProposalTableView(){
        TableColumn< ResearchApplication, Integer > id = new TableColumn<>("ID");
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
        researchTeamSupervisorLabel.setText(team.getTeamSupervisorId());

        // --> getting members of the team

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

//        TableColumn<Member, Integer> memberConfirmation = new TableColumn<>("Status");
//        memberConfirmation.setCellValueFactory(new PropertyValueFactory("confirmationStatus"));

        researchTeamMemberTableView.setItems(researchTeamMemberList);
        researchTeamMemberTableView.getColumns().addAll(memberSerial, memberStudentId, memberName);
    }


    @FXML
    private void handleAcceptResearchApplication(ActionEvent actionEvent) throws SQLException {
        ResearchApplication researchApplication = researchProposalTableView.getSelectionModel().getSelectedItem();
        if (researchApplication == null){
            researchProposalStatusLabel.setText("Select an application first!");
            return;
        }

        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();

        TeamDatabaseOperation teamOp = new TeamDatabaseOperationImplementation();
        Team team = teamOp.getTeam(researchApplication.getApplicationTeamId());
        int newMemberCount = team.getMemberCount();
        TeacherDatabaseOperation teacherOp = new TeacherDatabaseOperationImplementation();
        Teacher teacher = teacherOp.getTeacher(team.getTeamSupervisorId());
        int existingStudentCount = researchOp.getSupervisingStudentCount(teacher.getTeacherInitial());

        if (existingStudentCount + newMemberCount > 7){
            researchProposalStatusLabel.setText("You can not assign more than 7 students to an individual supervisor.");
            return;
        }

        if (researchOp.isEvaluatedByChairman(researchApplication.getApplicationId())){
            researchProposalStatusLabel.setText("Can not re-evaluate already evaluated application!");
            return;
        }

        if (researchOp.acceptByChairman(researchApplication)){
            fetchResearchProposalsFromDatabase();
            researchProposalStatusLabel.setText("Endorsement of Team (" + researchApplication.getApplicationTeamId() + ") successful");
        }
        else{
            researchProposalStatusLabel.setText("Some error occurred!");
        }
    }

    @FXML
    private void handleRejectResearchApplication(ActionEvent actionEvent) throws SQLException {
        ResearchApplication researchApplication = researchProposalTableView.getSelectionModel().getSelectedItem();
        if (researchApplication == null){
            researchProposalStatusLabel.setText("Select an application first!");
            return;
        }

        ResearchApplicationDatabaseOperation researchOp = new ResearchApplicationDatabaseOperationImplementation();

        if (researchOp.isEvaluatedByChairman(researchApplication.getApplicationId())){
            researchProposalStatusLabel.setText("Can not re-evaluate already evaluated application!");
            return;
        }

        if (researchOp.rejectByChairman(researchApplication)){
            fetchResearchProposalsFromDatabase();
            researchProposalStatusLabel.setText("Rejection of Team (" + researchApplication.getApplicationTeamId() + ") successful");
        }
        else{
            researchProposalStatusLabel.setText("Some error occurred!");
        }
    }
}
