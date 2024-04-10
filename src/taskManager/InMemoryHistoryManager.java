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
        if (history.size() >=10 ){
            history.remove(0);
            history.add(task);
        } else {
            history.add(task);
        }
    }

    public ArrayList<Task> getHistory(){
        return history;
    }
}
