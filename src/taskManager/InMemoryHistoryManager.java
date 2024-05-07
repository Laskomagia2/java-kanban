package taskManager;

import tasks.Task;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private  final DoubleLinkedList handMadeLinkedList = new DoubleLinkedList();

    @Override
    public void add(Task task) {
        if (handMadeLinkedList.getHistoryHashMap().containsKey(task.getTaskId())) {
            remove(task.getTaskId());
        }
        handMadeLinkedList.linkLast(task);
    }

    @Override
    public void remove(int id) {
        handMadeLinkedList.removeNode(id);
        handMadeLinkedList.getHistoryHashMap().remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return handMadeLinkedList.getTasks();
    }
}
