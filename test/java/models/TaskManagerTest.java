package models;

import com.taskManager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

abstract class TaskManagerTest<T extends TaskManager> {

    public TaskManagerTest(T manager) {
        this.taskManager = manager;
    }

    protected T taskManager;


    @Test
    void testCreateTask() {
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration taskDuration = Duration.ofHours(24);
        Task task = new Task("А", "Б", taskStartTime, taskDuration);
        task.setTaskId(1234);
        int idBefore = task.getTaskId();
        String nameBefore = task.getName();
        String contextBefore = task.getContext();
        Status statusBefore = task.getStatus();

        taskManager.createTask(task);
        Assertions.assertEquals(idBefore, task.getTaskId());
        Assertions.assertEquals(nameBefore, task.getName());
        Assertions.assertEquals(contextBefore, task.getContext());
        Assertions.assertEquals(statusBefore, task.getStatus());
    }

    @Test
    void testCreateSubtask() {
        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration subtaskDuration = Duration.ofHours(24);
        Duration epicDuration = Duration.ofHours(21);
        Epic epic = new Epic("G", "H", epicStartTime, epicDuration);
        taskManager.createEpic(epic);
        Subtask task = new Subtask("А", "Б", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        task.setTaskId(1234);
        int idBefore = task.getTaskId();
        String nameBefore = task.getName();
        String contextBefore = task.getContext();
        Status statusBefore = task.getStatus();

        taskManager.createTask(task);
        Assertions.assertEquals(idBefore, task.getTaskId());
        Assertions.assertEquals(nameBefore, task.getName());
        Assertions.assertEquals(contextBefore, task.getContext());
        Assertions.assertEquals(statusBefore, task.getStatus());
    }

    @Test
    void testCreateEpic() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration epicDuration = Duration.ofHours(21);
        Epic epic = new Epic("G", "H", epicStartTime, epicDuration);
        epic.setTaskId(1234);
        int idBefore = epic.getTaskId();
        String nameBefore = epic.getName();
        String contextBefore = epic.getContext();
        Status statusBefore = epic.getStatus();

        taskManager.createEpic(epic);
        Assertions.assertEquals(idBefore, epic.getTaskId());
        Assertions.assertEquals(nameBefore, epic.getName());
        Assertions.assertEquals(contextBefore, epic.getContext());
        Assertions.assertEquals(statusBefore, epic.getStatus());
    }

    @Test
    void testGetListOfTasks() {
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 21, 18, 0);
        LocalDateTime task1StartTime = LocalDateTime.of(2024, Month.DECEMBER, 23, 18, 0);
        LocalDateTime task2StartTime = LocalDateTime.of(2024, Month.DECEMBER, 25, 18, 0);
        Duration taskDuration = Duration.ofHours(21);
        Duration task1Duration = Duration.ofHours(22);
        Duration task2Duration = Duration.ofHours(23);

        Task task = new Task("A", "B", taskStartTime, taskDuration);
        Task task1 = new Task("C", "D", task1StartTime, task1Duration);
        Task task2 = new Task("E", "F", task2StartTime, task2Duration);

        ArrayList<Task> currentListOfTasks = new ArrayList<>();
        currentListOfTasks.add(task);
        currentListOfTasks.add(task1);
        currentListOfTasks.add(task2);

        taskManager.createTask(task);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        Assertions.assertArrayEquals(currentListOfTasks.toArray(), taskManager.getListOfTasks().toArray());
    }

    @Test
    void testGetListOfSubtasks() {
        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 21, 18, 0);
        LocalDateTime subtask1StartTime = LocalDateTime.of(2024, Month.DECEMBER, 23, 18, 0);
        LocalDateTime subtask2StartTime = LocalDateTime.of(2024, Month.DECEMBER, 25, 18, 0);
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 25, 18, 0);
        Duration subtaskDuration = Duration.ofHours(3);
        Duration subtask1Duration = Duration.ofHours(5);
        Duration subtask2Duration = Duration.ofHours(6);
        Duration epicDuration = Duration.ofHours(1);

        Epic epic = new Epic("EPIC", "EPIC", epicStartTime, epicDuration);

        taskManager.createEpic(epic);

        Subtask subtask = new Subtask("A", "B", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        Subtask subtask1 = new Subtask("C", "D", epic.getTaskId(), subtask1StartTime, subtask1Duration);
        Subtask subtask2 = new Subtask("E", "F", epic.getTaskId(), subtask2StartTime, subtask2Duration);

        taskManager.createSubtask(subtask);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);


        ArrayList<Task> currentListOfTasks = new ArrayList<>();
        currentListOfTasks.add(subtask);
        currentListOfTasks.add(subtask1);
        currentListOfTasks.add(subtask2);

        Assertions.assertArrayEquals(currentListOfTasks.toArray(), taskManager.getListOfSubtasks().toArray());
    }

    @Test
    void testGetListOfEpics() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 21, 18, 0);

        Duration epicDuration = Duration.ofHours(21);

        Epic epic = new Epic("A", "B", epicStartTime, epicDuration);

        ArrayList<Epic> currentListOfTasks = new ArrayList<>();
        currentListOfTasks.add(epic);

        taskManager.createEpic(epic);

        Assertions.assertArrayEquals(currentListOfTasks.toArray(), taskManager.getListOfEpics().toArray());
    }

    @Test
    void testGetEpicSubtasks() {
        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        LocalDateTime subtask1StartTime = LocalDateTime.of(2024, Month.DECEMBER, 16, 18, 0);
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration subtaskDuration = Duration.ofHours(24);
        Duration subtask1Duration = Duration.ofHours(24);
        Duration epicDuration = Duration.ofHours(21);
        Epic epic = new Epic("G", "H", epicStartTime, epicDuration);
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("А", "Б", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        Subtask subtask1 = new Subtask("A", "B", epic.getTaskId(), subtask1StartTime, subtask1Duration);


        taskManager.createSubtask(subtask);
        taskManager.createSubtask(subtask1);
        ArrayList<Subtask> currentSubtasks = new ArrayList<>();
        currentSubtasks.add(subtask);
        currentSubtasks.add(subtask1);

        Assertions.assertArrayEquals(currentSubtasks.toArray(), taskManager.getEpicSubtasks(epic).toArray());
    }

    @Test
    void testGetTaskById() {
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration taskDuration = Duration.ofHours(24);
        Task task = new Task("А", "Б", taskStartTime, taskDuration);
        task.setTaskId(1234);

        taskManager.createTask(task);

        Assertions.assertEquals(task, taskManager.getTaskById(1234));
    }

    @Test
    void testGetSubtaskById() {
        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration subtaskDuration = Duration.ofHours(24);
        Duration epicDuration = Duration.ofHours(21);
        Epic epic = new Epic("G", "H", epicStartTime, epicDuration);
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("А", "Б", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        subtask.setTaskId(1234);

        taskManager.createSubtask(subtask);

        Assertions.assertEquals(subtask, taskManager.getSubtaskById(1234));
    }

    @Test
    void testGetSEpicById() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration epicDuration = Duration.ofHours(21);
        Epic epic = new Epic("G", "H", epicStartTime, epicDuration);
        epic.setTaskId(1234);

        taskManager.createEpic(epic);

        Assertions.assertEquals(epic, taskManager.getEpicById(1234));
    }

    @Test
    void testUpdateTask() {
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration taskDuration = Duration.ofHours(24);
        Task task = new Task("А", "Б", taskStartTime, taskDuration);
        task.setTaskId(1234);
        taskManager.createTask(task);

        LocalDateTime taskNewStartTime = LocalDateTime.of(2024, Month.DECEMBER, 16, 18, 0);
        Duration taskNewDuration = Duration.ofHours(24);
        Task taskNew = new Task("F", "D", taskNewStartTime, taskNewDuration);
        taskNew.setTaskId(1234);

        taskManager.updateTask(taskNew);

        Assertions.assertEquals(taskNew, taskManager.getTaskById(1234));
    }

    @Test
    void testUpdateSubtask() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration epicDuration = Duration.ofHours(24);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        taskManager.createEpic(epic);

        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration subtaskDuration = Duration.ofHours(24);
        Subtask subtask = new Subtask("А", "Б", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        subtask.setTaskId(1234);
        taskManager.createSubtask(subtask);

        LocalDateTime newSubtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 16, 18, 0);
        Duration newSubtaskDuration = Duration.ofHours(24);
        Subtask newSubtask = new Subtask("F", "D", subtask.getEpicId(), newSubtaskStartTime, newSubtaskDuration);
        newSubtask.setTaskId(1234);

        taskManager.updateSubtask(newSubtask);

        Assertions.assertEquals(newSubtask, taskManager.getSubtaskById(1234));
    }

    @Test
    void testUpdateEpic() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration epicDuration = Duration.ofHours(24);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        epic.setTaskId(1234);
        taskManager.createEpic(epic);

        LocalDateTime newEpicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 16, 18, 0);
        Duration newEpicDuration = Duration.ofHours(24);
        Epic newEpic = new Epic("F", "D", newEpicStartTime, newEpicDuration);
        newEpic.setTaskId(1234);

        taskManager.updateEpic(newEpic);

        Assertions.assertEquals(newEpic, taskManager.getEpicById(1234));
    }

    @Test
    void testRemoveTasks() {
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration taskDuration = Duration.ofHours(24);
        Task task = new Task("A", "B", taskStartTime, taskDuration);
        taskManager.createTask(task);

        taskManager.removeTasks();

        Assertions.assertTrue(taskManager.getListOfTasks().isEmpty());
    }

    @Test
    void testRemoveSubtasks() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration epicDuration = Duration.ofHours(24);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        taskManager.createEpic(epic);

        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration subtaskDuration = Duration.ofHours(24);
        Subtask subtask = new Subtask("А", "Б", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        taskManager.createSubtask(subtask);

        LocalDateTime newSubtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 16, 18, 0);
        Duration newSubtaskDuration = Duration.ofHours(24);
        Subtask newSubtask = new Subtask("F", "D", subtask.getEpicId(), newSubtaskStartTime, newSubtaskDuration);
        taskManager.createSubtask(newSubtask);

        taskManager.removeSubtasks();

        Assertions.assertTrue(taskManager.getListOfSubtasks().isEmpty());
    }

    @Test
    void testRemoveEpics() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration epicDuration = Duration.ofHours(24);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        taskManager.createEpic(epic);

        taskManager.removeEpics();

        Assertions.assertTrue(taskManager.getListOfEpics().isEmpty());
    }

    @Test
    void testRemoveTaskById() {
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration taskDuration = Duration.ofHours(24);
        Task task = new Task("A", "B", taskStartTime, taskDuration);
        taskManager.createTask(task);

        taskManager.removeTasksById(task.getTaskId());

        Assertions.assertNull(taskManager.getTaskById(task.getTaskId()));
    }

    @Test
    void testRemoveSubtaskById() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 14, 18, 0);
        Duration epicDuration = Duration.ofHours(24);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        taskManager.createEpic(epic);

        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 1, 18, 0);
        Duration subtaskDuration = Duration.ofHours(5);
        Subtask subtask = new Subtask("F", "B", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        taskManager.createSubtask(subtask);

        taskManager.removeSubtasksById(subtask.getTaskId());

        Assertions.assertNull(taskManager.getSubtaskById(subtask.getTaskId()));
    }

    @Test
    void testRemoveEpicById() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 5, 18, 0);
        Duration epicDuration = Duration.ofHours(24);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        taskManager.createEpic(epic);

        taskManager.removeEpicsById(epic.getTaskId());

        Assertions.assertNull(taskManager.getEpicById(epic.getTaskId()));
    }

    @Test
    void testRemoveAllTasks() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 5, 18, 0);
        Duration epicDuration = Duration.ofHours(5);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        taskManager.createEpic(epic);

        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 7, 18, 0);
        Duration subtaskDuration = Duration.ofHours(4);
        Subtask subtask = new Subtask("А", "Б", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        taskManager.createSubtask(subtask);

        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 18, 18, 0);
        Duration taskDuration = Duration.ofHours(7);
        Task task = new Task("A", "B", taskStartTime, taskDuration);
        taskManager.createTask(task);

        taskManager.removeAllTasks();

        Assertions.assertTrue(taskManager.getListOfTasks().isEmpty() &&
                taskManager.getListOfSubtasks().isEmpty() && taskManager.getListOfEpics().isEmpty());
    }

    @Test
    void testGetHistory() {
        LocalDateTime epicStartTime = LocalDateTime.of(1, Month.DECEMBER, 1, 1, 1);
        Duration epicDuration = Duration.ofHours(1);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        taskManager.createEpic(epic);

        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 7, 18, 0);
        Duration subtaskDuration = Duration.ofHours(6);
        Subtask subtask = new Subtask("F", "G", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        taskManager.createSubtask(subtask);

        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 18, 18, 0);
        Duration taskDuration = Duration.ofHours(6);
        Task task = new Task("R", "T", taskStartTime, taskDuration);
        taskManager.createTask(task);

        Assertions.assertTrue(taskManager.getHistory().isEmpty());

        taskManager.getEpicById(epic.getTaskId());

        taskManager.getSubtaskById(subtask.getTaskId());

        taskManager.getTaskById(task.getTaskId());

        ArrayList<Task> currentHistory = new ArrayList<>();
        currentHistory.add(epic);
        currentHistory.add(subtask);
        currentHistory.add(task);

        Assertions.assertArrayEquals(currentHistory.toArray(), taskManager.getHistory().toArray());
    }
}
