package com.taskManager;

public class Managers {
    private Managers() {

    }

    public static FileBackedTaskManager getFileBacked() {
        return new FileBackedTaskManager();
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
