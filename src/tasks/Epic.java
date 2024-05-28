package tasks;

import taskManager.TaskType;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasks;

    public Epic(String name, String context) {
        super(name, context);
        subtasks = new ArrayList<>();
        this.taskType = TaskType.EPIC;
    }

    public void addSubtask(Integer subtaskId) {
        subtasks.add(subtaskId);
    }

    public ArrayList<Integer> getSubTasks() {
        return subtasks;
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
