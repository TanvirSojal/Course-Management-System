package objects;

import java.time.LocalDate;

public class Deadline {
    private int deadlineId;
    private String deadlineTopic;
    private LocalDate deadlineDate;

    public Deadline(int deadlineId, String deadlineTopic, LocalDate deadlineDate) {
        this.deadlineId = deadlineId;
        this.deadlineTopic = deadlineTopic;
        this.deadlineDate = deadlineDate;
    }

    public int getDeadlineId() {
        return deadlineId;
    }

    public void setDeadlineId(int deadlineId) {
        this.deadlineId = deadlineId;
    }

    public String getDeadlineTopic() {
        return deadlineTopic;
    }

    public void setDeadlineTopic(String deadlineTopic) {
        this.deadlineTopic = deadlineTopic;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    @Override
    public String toString() {
        return "Deadline{" +
                "deadlineId=" + deadlineId +
                ", deadlineTopic='" + deadlineTopic + '\'' +
                ", deadlineDate=" + deadlineDate +
                '}';
    }
}
