package test;

import org.junit.jupiter.api.BeforeEach;
import taskManager.InMemoryHistoryManager;
import taskManager.InMemoryTaskManager;
import taskManager.Managers;
import tasks.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProgramTest {
    InMemoryTaskManager taskManager;
    InMemoryHistoryManager historyManager;
    @BeforeEach
    void beforeEach() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
        historyManager = (InMemoryHistoryManager) Managers.getDefaultHistory();
    }

    @Test
    void idTasksEquals(){
        Task t1 = new Task("A", "B");
        taskManager.createTask(t1);
        Task complTask1 = taskManager.getTaskById(t1.getTaskId());
        int t1Id = complTask1.getTaskId();

        Task t2 = new Task("A", "B");
        taskManager.createTask(t2);
        Task complTask2 = taskManager.getTaskById(t2.getTaskId());
        int t2Id = complTask2.getTaskId();

        Assertions.assertEquals(t1Id, t2Id, "Мимо мимо");
    }

    @Test
    void idTasksHairsEquals(){
        Epic t1 = new Epic("A", "B");
        taskManager.createEpic(t1);
        Epic complEpic1 = taskManager.getEpicById(t1.getTaskId());
        int t1Id = complEpic1.getTaskId();

        Epic t2 = new Epic("A", "B");
        taskManager.createEpic(t2);
        Epic complEpic2 = taskManager.getEpicById(t2.getTaskId());
        int t2Id = complEpic2.getTaskId();

        Assertions.assertEquals(t1Id, t2Id, "Мимо мимо");
    }

    @Test
    void testUtilClass(){
        InMemoryTaskManager taskManager1 = (InMemoryTaskManager) Managers.getDefault();
        Task task1 = new Task("А", "Б");
        taskManager1.createTask(task1);
        Assertions.assertNotNull(taskManager1.getListOfTasks().get(0));
    }

    @Test
    void testTaskManager(){
        Task task = new Task("А", "Б");
        taskManager.createTask(task);

        Epic epic = new Epic("А", "Б");
        taskManager.createEpic(epic);

        Subtask sub1 = new Subtask("В", "Г", epic.getTaskId());
        taskManager.createSubtask(sub1);

        Assertions.assertEquals(task, taskManager.getTaskById(task.getTaskId()));
        Assertions.assertEquals(epic, taskManager.getEpicById(epic.getTaskId()));
        Assertions.assertEquals(sub1, taskManager.getSubtaskById(sub1.getTaskId()));
    }

    @Test
    void testTaskID(){
        Task task = new Task("А", "Б");
        task.setTaskId(1234);
        taskManager.createTask(task);
        Assertions.assertEquals(1234, task.getTaskId());
    }

    @Test
    void testCreateTaskInManager(){
        Task task = new Task("А", "Б");
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
    void historyTest(){
        Task task1 = new Task("А", "Б");
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(1, history.size(), "История не пустая.");
    }
}
