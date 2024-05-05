package taskManager;

import tasks.Task;

public class Node {
    public Task item;
    public Node next;
    public Node prev;

    public Node(Node prev, Task element, Node next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
