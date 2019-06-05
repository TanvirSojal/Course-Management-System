package objects;

public class Member {
    private int serial;
    private String studentId;
    private String name;
    private String confirmationStatus;

    public Member(int serial, String studentId, String name, String confirmationStatus) {
        this.serial = serial;
        this.studentId = studentId;
        this.name = name;
        this.confirmationStatus = confirmationStatus;
    }

    public int getSerial() {

        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(String confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    @Override
    public String toString() {
        return "Member{" +
                "serial=" + serial +
                ", studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", confirmationStatus='" + confirmationStatus + '\'' +
                '}';
    }
}
