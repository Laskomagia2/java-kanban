package tasks;

import com.taskmanager.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasks;

    protected LocalDateTime endTime;

    public Epic(String name, String context, LocalDateTime startTime, Duration duration) {
        super(name, context, startTime, duration);
        subtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
        this.startTime = LocalDateTime.MIN;
        setEndTime(startTime);
        this.duration = Duration.ZERO;
    }

    public void addSubtask(Integer subtaskId) {
        subtasks.add(subtaskId);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {

        String result = "Epic{" +
                "name='" + name + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskStatus=" + status + '\'' +
                ", subtasks=" + subtasks + '\'';

        if (context != null) {
            result = result + ", extraInfo.length=" + context.length();
        } else {
            result = result + ", extraInfo=null";
        }

        return result + '}';
    }
}
