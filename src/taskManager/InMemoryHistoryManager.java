package taskManager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    protected ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        history.add(task);
    }

    public ArrayList<Task> getHistory(){
        return history;
    }
}
