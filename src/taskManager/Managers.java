package taskManager;

import tasks.Task;

public class Managers {
    private Managers(){}
    private static TaskManager taskManager;
    private static HistoryManager historyManager;

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
