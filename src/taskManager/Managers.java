package taskManager;

import tasks.Task;

public class Managers {
    private static TaskManager taskManager;
    private static HistoryManager historyManager;

    public static TaskManager getDefault(){
        if (taskManager == null){
            return new InMemoryTaskManager();
        }
        return taskManager;
    }

    public static HistoryManager getDefaultHistory(){
        if (historyManager == null){
            return new InMemoryHistoryManager();
        }
        return historyManager;
    }
}
