package objects;

public class Course {
    private int courseId;
    private String courseCode;
    private String courseTitle;
    private int courseSection;

    public Course(int courseId, String courseCode, String courseTitle, int courseSection) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseSection = courseSection;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(int courseSection) {
        this.courseSection = courseSection;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseCode='" + courseCode + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseSection='" + courseSection +
                '}';
    }
}
