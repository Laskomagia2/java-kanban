package models;

import com.taskmanager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programexceptions.DateTimeIntersectionException;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class InMemoryManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    public InMemoryManagerTest() {
        super(new InMemoryTaskManager());
    }

    @Test
    void testCheckEpicStatus() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 5, 18, 0);
        Duration epicDuration = Duration.ofHours(24);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        taskManager.createEpic(epic);

        Assertions.assertEquals(Status.NEW, epic.getStatus());

        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 7, 18, 0);
        Duration subtaskDuration = Duration.ofHours(24);
        Subtask subtask = new Subtask("А", "Б", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        taskManager.createSubtask(subtask);

        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subtask.setStatus("DONE");
        taskManager.updateSubtask(subtask);

        Assertions.assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    void testCheckEpicTime() {
        LocalDateTime epicStartTime = LocalDateTime.of(1, Month.DECEMBER, 1, 1, 1);
        Duration epicDuration = Duration.ofHours(24);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        taskManager.createEpic(epic);

        LocalDateTime currentEpicStartTime = LocalDateTime.of(1, 1, 1, 1, 1, 1);

        Assertions.assertEquals(currentEpicStartTime, epic.getStartTime());

        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 7, 18, 0);
        Duration subtaskDuration = Duration.ofHours(24);
        Subtask subtask = new Subtask("А", "Б", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        taskManager.createSubtask(subtask);

        currentEpicStartTime = subtask.getStartTime();
        LocalDateTime currentEpicEndTime = subtask.getEndTime();

        Assertions.assertEquals(currentEpicStartTime, epic.getStartTime());
        Assertions.assertEquals(currentEpicEndTime, epic.getEndTime());
    }

    @Test
    void testDateTimeIntersection() {
        Assertions.assertThrows(DateTimeIntersectionException.class, () -> {
            LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 18, 15, 0);
            Duration taskDuration = Duration.ofHours(20);
            Task task = new Task("R", "T", taskStartTime, taskDuration);
            taskManager.createTask(task);

            LocalDateTime task1StartTime = LocalDateTime.of(2024, Month.DECEMBER, 18, 18, 0);
            Duration task1Duration = Duration.ofHours(20);
            Task task1 = new Task("Q", "W", task1StartTime, task1Duration);
            taskManager.createTask(task1);
        });
    }
}
