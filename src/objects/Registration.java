package objects;

public class Registration {
    private int registrationId;
    private String studentId;
    private int courseId;

    public Registration(int registrationId, String studentId, int courseId) {
        this.registrationId = registrationId;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId=" + registrationId +
                ", studentId='" + studentId + '\'' +
                ", courseId=" + courseId +
                '}';
    }
}
