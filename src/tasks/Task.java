package tasks;

import com.taskManager.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected String name;
    protected String context;
    protected Status status;
    protected int taskId;
    protected TaskType taskType;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(String name, String context, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.context = context;
        this.status = Status.NEW;
        this.taskType = TaskType.TASK;
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        if (name != null) {
            hash = name.hashCode();
        }
        if (context != null) {
            hash += context.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskId, task.taskId);

    }

    public void setTaskId(int id) {
        taskId = id;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getName() {
        return this.name;
    }

    public String getContext() {
        return this.context;
    }

    public Status getStatus() {
        return status;
    }

    public int getTaskId() {
        return taskId;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public  LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    @Override
    public String toString() {

        String result = "Task{" +
                "name='" + name + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskStatus=" + status + '\'';

        if (context != null) {
            result = result + ", extraInfo.length=" + context.length();
        } else {
            result = result + ", extraInfo=null";
        }

        return result + '}';
    }
}
