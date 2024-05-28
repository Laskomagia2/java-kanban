package com.taskManager;

public enum TaskType {
    TASK,
    SUBTASK,
    EPIC;

    public static TaskType typeFinder(String line) {
        if (line.contains("TASK")) {
            return TASK;
        } else if (line.contains("SUBTASK")) {
            return SUBTASK;
        } else if (line.contains("EPIC")) {
            return EPIC;
        }
        return null;
    }
}
