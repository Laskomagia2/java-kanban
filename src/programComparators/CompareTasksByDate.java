package programComparators;

import tasks.Task;

import java.util.Comparator;

public class CompareTasksByDate implements Comparator<Task> {
    public int compare(Task t1, Task t2) {
        return t1.getStartTime().compareTo(t2.getStartTime());
    }
}
