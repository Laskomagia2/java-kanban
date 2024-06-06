package com.Test;

import org.junit.jupiter.api.BeforeEach;
import com.taskManager.HistoryManager;
import com.taskManager.InMemoryTaskManager;
import com.taskManager.FileBackedTaskManager;
import com.taskManager.Managers;
import com.taskManager.TaskManager;
import tasks.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class ProgramTest {
    TaskManager taskManager;
    HistoryManager historyManager;
    FileBackedTaskManager backedTaskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
        backedTaskManager = Managers.getFileBacked();
    }

    @Test
    void idTasksEquals() {
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
    void idTasksHairsEquals() {
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
    void testUtilClass() {
        InMemoryTaskManager taskManager1 = (InMemoryTaskManager) Managers.getDefault();
        Task task1 = new Task("А", "Б");
        taskManager1.createTask(task1);
        Assertions.assertNotNull(taskManager1.getListOfTasks().get(0));
    }

    @Test
    void testTaskManager() {
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
    void testTaskID() {
        Task task = new Task("А", "Б");
        task.setTaskId(1234);
        taskManager.createTask(task);
        Assertions.assertEquals(1234, task.getTaskId());
    }

    @Test
    void testCreateTaskInManager() {
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
    void historyTest() {
        Task task1 = new Task("А", "Б");
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void orderHistoryTest() {
        Task task1 = new Task("А", "Б");
        taskManager.createTask(task1);

        Task task2 = new Task("В", "Г");
        taskManager.createTask(task2);

        List<Task> rightHistoryOrder = new LinkedList<>();
        rightHistoryOrder.add(task1);
        rightHistoryOrder.add(task2);


        taskManager.getTaskById(task1.getTaskId());
        taskManager.getTaskById(task2.getTaskId());

        List<Task> currentHistory = taskManager.getHistory();

        Assertions.assertArrayEquals(currentHistory.toArray(), rightHistoryOrder.toArray());
    }

    @Test
    void sameTaskInHistory() {
        Task task1 = new Task("А", "Б");
        taskManager.createTask(task1);

        List<Task> rightHistoryOrder = List.of(task1);

        taskManager.getTaskById(task1.getTaskId());
        taskManager.getTaskById(task1.getTaskId());

        taskManager.getHistory();

        Assertions.assertArrayEquals(taskManager.getHistory().toArray(), rightHistoryOrder.toArray());
    }

    @Test
    void testBackedManager() throws IOException {
        Task t1 = new Task("A", "B");
        Task t2 = new Task("B", "C");

        backedTaskManager.createTask(t1);
        backedTaskManager.createTask(t2);

        File tempFile = File.createTempFile("Tasks", null);
        FileWriter fw = new FileWriter(tempFile);
        fw.write(FileBackedTaskManager.taskToString(t1));
        fw.write(FileBackedTaskManager.taskToString(t2));
        fw.close();

        List<String> currentFileList = new ArrayList<>();
        List<String> tempFileList = new ArrayList<>();

        FileReader currentReader = new FileReader("SavedTasks.csv");
        BufferedReader br = new BufferedReader(currentReader);
        while (br.ready()) {
            String line = br.readLine();
            currentFileList.add(line);
        }
        br.close();

        FileReader tempReader = new FileReader(tempFile);
        BufferedReader brT = new BufferedReader(tempReader);
        while (brT.ready()) {
            String line = brT.readLine();
            tempFileList.add(line);
        }
        brT.close();

        Assertions.assertArrayEquals(new List[]{tempFileList}, new List[]{currentFileList});
    }

    @Test
    void testBackedManagerLoad() throws IOException {
        Task t1 = new Task("A", "B");
        backedTaskManager.createTask(t1);
        Task t2 = new Task("B", "C");
        backedTaskManager.createTask(t2);
        Epic epic = new Epic("V", "Z");
        backedTaskManager.createEpic(epic);
        Subtask sub1 = new Subtask("D", "C", epic.getTaskId());
        backedTaskManager.createSubtask(sub1);

        List<Task> currentFileList = new ArrayList<>();

        currentFileList.addAll(backedTaskManager.getListOfTasks());
        currentFileList.addAll(backedTaskManager.getListOfSubtasks());
        currentFileList.addAll(backedTaskManager.getListOfEpics());

        List<Task> loadedFileList = new ArrayList<>();

        FileBackedTaskManager newFileBackedManager = FileBackedTaskManager.loadFromFile(new File("SavedTasks.csv"));
        loadedFileList.addAll(newFileBackedManager.getListOfTasks());
        loadedFileList.addAll(newFileBackedManager.getListOfSubtasks());
        loadedFileList.addAll(newFileBackedManager.getListOfEpics());


        Assertions.assertArrayEquals(new List[]{loadedFileList}, new List[]{currentFileList});
    }
}
