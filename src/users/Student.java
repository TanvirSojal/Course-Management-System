package users;

public class Student {
    private String studentId;
    private String studentName;
    private String studentEmail;
    private String studentBloodGroup;
    private String studentContactNumber;
    private String studentAddress;

    public Student(String studentId, String studentName, String studentEmail, String studentBloodGroup, String studentContactNumber, String studentAddress) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentBloodGroup = studentBloodGroup;
        this.studentContactNumber = studentContactNumber;
        this.studentAddress = studentAddress;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentBloodGroup() {
        return studentBloodGroup;
    }

    public void setStudentBloodGroup(String studentBloodGroup) {
        this.studentBloodGroup = studentBloodGroup;
    }

    public String getStudentContactNumber() {
        return studentContactNumber;
    }

    public void setStudentContactNumber(String studentContactNumber) {
        this.studentContactNumber = studentContactNumber;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentName='" + studentName + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentEmail='" + studentEmail + '\'' +
                ", studentBloodGroup='" + studentBloodGroup + '\'' +
                ", studentContactNumber='" + studentContactNumber + '\'' +
                ", studentAddress='" + studentAddress + '\'' +
                '}';
    }
}
