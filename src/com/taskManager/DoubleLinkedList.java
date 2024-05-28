package com.taskManager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoubleLinkedList {
    private final Map<Integer, Node> historyHashMap = new HashMap<>();
    private Node head;
    private Node tail;

    public boolean containsKeyInList(Integer taskId) {
        return historyHashMap.containsKey(taskId);
    }

    public void linkLast(Task task) {
        Node node = null;
        if (task != null) {
            if (this.tail == null) {
                Node added = new Node(null, task, null);
                node = added;
                this.tail = added;
                this.head = added;
            } else {
                Node added = new Node(this.tail, task, null);
                node = added;
                this.tail.next = added;
                this.tail = added;
            }
            historyHashMap.put(task.getTaskId(), node);
        }
        return;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node node = head;
        while (node != null) {
            tasks.add(node.item);
            node = node.next;
        }
        return tasks;
    }

    public void removeNode(Integer taskId) {
        Node node = historyHashMap.get(taskId);
        if (node == null) {
            return;
        }

        if (node == head) {
            if (node.next == null) {
                head = null;
                tail = null;
            } else {
                head = node.next;
                node.next.prev = null;
            }
        } else if (node == tail) {
            tail = node.prev;
            node.prev.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.item = null;
        historyHashMap.remove(taskId);
    }

    private static class Node {
        Task item;
        Node next;
        Node prev;

        public Node(Node prev, Task element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}

