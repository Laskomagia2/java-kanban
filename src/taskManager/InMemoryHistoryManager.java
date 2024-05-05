package taskManager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    DoubleLinkedList handMadeLinkedList = new DoubleLinkedList();

    @Override
    public void add(Task task) {
        if(handMadeLinkedList.historyHashMap.containsKey(task.getTaskId())){
            handMadeLinkedList.removeNode(handMadeLinkedList.historyHashMap.get(task.getTaskId()));
            handMadeLinkedList.historyHashMap.remove(task.getTaskId());
            handMadeLinkedList.historyHashMap.put(task.getTaskId(), handMadeLinkedList.linkLast(task));
        } else {
            handMadeLinkedList.historyHashMap.put(task.getTaskId(), handMadeLinkedList.linkLast(task));
        }
    }

    @Override
    public void remove(int id){
        handMadeLinkedList.removeNode(handMadeLinkedList.historyHashMap.get(id));
        handMadeLinkedList.historyHashMap.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return handMadeLinkedList.getTasks();
    }

    public static class DoubleLinkedList {
        protected Map <Integer, Node> historyHashMap = new HashMap<>();
        public Node head;
        public Node tail;

        public Node linkLast (Task task) {
            Node node = null;
            if (task != null) {
                if(this.tail == null) {
                    Node added = new Node(null ,task, null);
                    node = added;
                    this.tail = added;
                    this.head = added;
                } else {
                    Node added = new Node(this.tail ,task, null);
                    node = added;
                    this.tail.next = added;
                    this.tail = added;
                }
            }
            return node;
        }

        public List<Task> getTasks(){
            List<Task> tasks = new ArrayList<>();
            Node node = head;
            while (node != null) {
                tasks.add(node.item);
                node = node.next;
            }
            return tasks;
        }

        public void removeNode(Node node) {
            if (node == null) {
                return;
            }

            if (node == head) {
                if(node.next == null) {
                    head = null;
                    tail = null;
                } else {
                    head = node.next;
                    node.next.prev = null;
                }
            } else if (node == tail){
                tail = node.prev;
                node.prev.next = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            node.item = null;
        }
    }
}
