-- Creating Database Course_Management 
CREATE DATABASE Course_Management;
USE Course_Management; -- Selecting this database for using

-- LOGINS table holds login credentials of an user
CREATE TABLE LOGINS(
    USERNAME VARCHAR(20) PRIMARY KEY,
    PASSWORD VARCHAR(30) NOT NULL, -- currently as plaintext
    USERTYPE VARCHAR(10) NOT NULL
);

-- Teacher table holds information of a Faculty Member
CREATE TABLE TEACHER(
	Teacher_ID VARCHAR(20) PRIMARY KEY,
    Teacher_Name VARCHAR(30) NOT NULL,
    Teacher_Email VARCHAR(30),
    FOREIGN KEY TEACHER(Teacher_ID) REFERENCES LOGINS(USERNAME)
);

-- Student Table holds information of a Student
CREATE TABLE STUDENT(
    Student_ID VARCHAR(20) PRIMARY KEY,
    Student_Name VARCHAR(30) NOT NULL,
    Student_Email VARCHAR(30),
    Student_Blood_Group VARCHAR(10),
    Student_Contact_Number VARCHAR(20),
    Student_Address VARCHAR(60),
    FOREIGN KEY STUDENT(Student_ID) REFERENCES LOGINS(USERNAME)
);

INSERT INTO STUDENT VALUES("123", "test", "test@email.com", NULL, NULL, NULL);

CREATE TABLE COURSE(
    Course_ID INT(10) PRIMARY KEY,
    Course_Code VARCHAR(10) NOT NULL,
    Course_Title VARCHAR(40) NOT NULL,
    Course_Section INT(10)
);

INSERT INTO COURSE VALUES(1, "CSE4000", "Research Methodology", NULL);
INSERT INTO COURSE VALUES(2, "CSE4022", "Advanced Calculus", 1);
INSERT INTO COURSE VALUES(3, "CSE4022", "Advanced Calculus", 2);
INSERT INTO COURSE VALUES(4, "CSE4051", "Advanced Robotics", 1);
INSERT INTO COURSE VALUES(5, "CSE4041", "Advanced Solid Chemistry", 1);

CREATE TABLE REGISTRATION(
    Registration_ID INT(10) PRIMARY KEY,
    Student_ID VARCHAR(20) NOT NULL,
    Course_ID INT(10) NOT NULL,
    FOREIGN KEY (Student_ID) REFERENCES STUDENT(Student_ID),
    FOREIGN KEY (Course_ID) REFERENCES COURSE(Course_ID)
);


CREATE TABLE APPLICATION(
	Application_ID INT(10) PRIMARY KEY AUTO_INCREMENT,
    Application_Date DATE NOT NULL,
    Application_Body VARCHAR(100) NOT NULL,
    Application_Student_0_ID VARCHAR(20) NOT NULL,
    Application_Student_1_ID  VARCHAR(20),
    Application_Student_2_ID VARCHAR(20),
    FOREIGN KEY APPLICATION(Application_Student_0_ID) REFERENCES STUDENT(Student_ID)
);