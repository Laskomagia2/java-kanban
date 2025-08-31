package tasks;

import com.taskmanager.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private final Integer epicId;

    public Subtask(String name, String context, Integer epicId, LocalDateTime startTime, Duration duration) {
        super(name, context, startTime, duration);
        this.epicId = epicId;
        this.taskType = TaskType.SUBTASK;
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {

        String result = "Subtask{" +
                "name='" + name + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskStatus=" + status + '\'' +
                ", EpicId: " + getEpicId();

        if (context != null) {
            result = result + ", extraInfo.length=" + context.length();
        } else {
            result = result + ", extraInfo=null";
        }

        return result + '}';
    }
}
