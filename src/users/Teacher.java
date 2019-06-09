package users;

import java.util.Objects;

public class Teacher {
    private String teacherInitial;
    private String teacherName;
    private String teacherEmail;
    private String teacherBloodGroup;
    private String teacherContactNumber;
    private String teacherAddress;

    public Teacher(String teacherInitial, String teacherName, String teacherEmail, String teacherBloodGroup, String teacherContactNumber, String teacherAddress) {
        this.teacherInitial = teacherInitial;
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherBloodGroup = teacherBloodGroup;
        this.teacherContactNumber = teacherContactNumber;
        this.teacherAddress = teacherAddress;
    }

    public String getTeacherInitial() {
        return teacherInitial;
    }

    public void setTeacherInitial(String teacherInitial) {
        this.teacherInitial = teacherInitial;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherBloodGroup() {
        return teacherBloodGroup;
    }

    public void setTeacherBloodGroup(String teacherBloodGroup) {
        this.teacherBloodGroup = teacherBloodGroup;
    }

    public String getTeacherContactNumber() {
        return teacherContactNumber;
    }

    public void setTeacherContactNumber(String teacherContactNumber) {
        this.teacherContactNumber = teacherContactNumber;
    }

    public String getTeacherAddress() {
        return teacherAddress;
    }

    public void setTeacherAddress(String teacherAddress) {
        this.teacherAddress = teacherAddress;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)",
                            this.teacherName,
                            this.teacherInitial);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(teacherInitial, teacher.teacherInitial) &&
                Objects.equals(teacherName, teacher.teacherName) &&
                Objects.equals(teacherEmail, teacher.teacherEmail) &&
                Objects.equals(teacherBloodGroup, teacher.teacherBloodGroup) &&
                Objects.equals(teacherContactNumber, teacher.teacherContactNumber) &&
                Objects.equals(teacherAddress, teacher.teacherAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherInitial, teacherName, teacherEmail, teacherBloodGroup, teacherContactNumber, teacherAddress);
    }
}
