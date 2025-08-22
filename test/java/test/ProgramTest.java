package test;

import com.taskManager.*;
import org.junit.jupiter.api.BeforeEach;
import tasks.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
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
        LocalDateTime firstTaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration taskFirstDuration = Duration.ofHours(24);
        Task t1 = new Task("A", "B", firstTaskStartTime, taskFirstDuration);
        taskManager.createTask(t1);
        Task complTask1 = taskManager.getTaskById(t1.getTaskId());
        int t1Id = complTask1.getTaskId();

        LocalDateTime secondTaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration taskSecondDuration = Duration.ofHours(24);
        Task t2 = new Task("A", "B", secondTaskStartTime, taskSecondDuration);
        taskManager.createTask(t2);
        Task complTask2 = taskManager.getTaskById(t2.getTaskId());
        int t2Id = complTask2.getTaskId();

        Assertions.assertEquals(t1Id, t2Id, "Мимо мимо");
    }

    @Test
    void idTasksHairsEquals() {
        LocalDateTime firstTaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration taskFirstDuration = Duration.ofHours(24);
        Epic t1 = new Epic("A", "B", firstTaskStartTime, taskFirstDuration);
        taskManager.createEpic(t1);
        Epic complEpic1 = taskManager.getEpicById(t1.getTaskId());
        int t1Id = complEpic1.getTaskId();

        LocalDateTime secondTaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration taskSecondDuration = Duration.ofHours(24);
        Epic t2 = new Epic("A", "B", secondTaskStartTime, taskSecondDuration);
        taskManager.createEpic(t2);
        Epic complEpic2 = taskManager.getEpicById(t2.getTaskId());
        int t2Id = complEpic2.getTaskId();

        Assertions.assertEquals(t1Id, t2Id, "Мимо мимо");
    }

    @Test
    void testUtilClass() {
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 20, 18, 0);
        Duration taskDuration = Duration.ofHours(24);
        InMemoryTaskManager taskManager1 = (InMemoryTaskManager) Managers.getDefault();
        Task task1 = new Task("А", "Б", taskStartTime, taskDuration);
        taskManager1.createTask(task1);
        Assertions.assertNotNull(taskManager1.getListOfTasks().get(0));
    }

    @Test
    void testTaskManager() {
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration taskDuration = Duration.ofHours(24);
        Task task = new Task("А", "Б", taskStartTime, taskDuration);
        taskManager.createTask(task);

        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 1, 18, 0);
        Duration epicDuration = Duration.ofHours(24);
        Epic epic = new Epic("А", "Б", epicStartTime, epicDuration);
        taskManager.createEpic(epic);

        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 20, 18, 0);
        Duration subtaskDuration = Duration.ofHours(24);
        Subtask sub1 = new Subtask("В", "Г", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        taskManager.createSubtask(sub1);

        Assertions.assertEquals(task, taskManager.getTaskById(task.getTaskId()));
        Assertions.assertEquals(epic, taskManager.getEpicById(epic.getTaskId()));
        Assertions.assertEquals(sub1, taskManager.getSubtaskById(sub1.getTaskId()));
    }

    @Test
    void testTaskID() {
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration taskDuration = Duration.ofHours(24);
        Task task = new Task("А", "Б", taskStartTime, taskDuration);
        task.setTaskId(1234);
        taskManager.createTask(task);
        Assertions.assertEquals(1234, task.getTaskId());
    }

    @Test
    void testCreateTaskInManager() {
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
    void historyTest() {
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 20, 18, 0);
        Duration taskDuration = Duration.ofHours(24);
        Task task1 = new Task("А", "Б", taskStartTime, taskDuration);
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        Assertions.assertNotNull(history, "История не пустая.");
        Assertions.assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void orderHistoryTest() {
        LocalDateTime taskFirstStartTime = LocalDateTime.of(2024, Month.DECEMBER, 19, 18, 0);
        Duration taskFirstDuration = Duration.ofHours(24);
        Task task1 = new Task("А", "Б", taskFirstStartTime, taskFirstDuration);
        taskManager.createTask(task1);

        LocalDateTime taskSecondStartTime = LocalDateTime.of(2024, Month.DECEMBER, 21, 18, 0);
        Duration taskSecondDuration = Duration.ofHours(24);
        Task task2 = new Task("В", "Г", taskSecondStartTime, taskSecondDuration);
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
        LocalDateTime taskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 20, 18, 0);
        Duration taskDuration = Duration.ofHours(24);
        Task task1 = new Task("А", "Б", taskStartTime, taskDuration);
        taskManager.createTask(task1);

        List<Task> rightHistoryOrder = List.of(task1);

        taskManager.getTaskById(task1.getTaskId());
        taskManager.getTaskById(task1.getTaskId());

        taskManager.getHistory();

        Assertions.assertArrayEquals(taskManager.getHistory().toArray(), rightHistoryOrder.toArray());
    }

    @Test
    void testBackedManager() throws IOException {
        LocalDateTime taskFirstStartTime = LocalDateTime.of(2024, Month.DECEMBER, 20, 18, 0);
        LocalDateTime taskSecondStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        Duration taskFirstDuration = Duration.ofHours(24);
        Duration taskSecondDuration = Duration.ofHours(24);
        Task t1 = new Task("A", "B", taskFirstStartTime, taskFirstDuration);
        Task t2 = new Task("B", "C", taskSecondStartTime, taskSecondDuration);

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
        LocalDateTime taskFirstStartTime = LocalDateTime.of(2024, Month.DECEMBER, 20, 18, 0);
        LocalDateTime taskSecondStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 26, 18, 0);
        Duration taskFirstDuration = Duration.ofHours(20);
        Duration taskSecondDuration = Duration.ofHours(23);
        Duration epicDuration = Duration.ofHours(21);
        Duration subtaskDuration = Duration.ofHours(20);

        Task t1 = new Task("A", "B", taskFirstStartTime, taskFirstDuration);
        backedTaskManager.createTask(t1);
        Task t2 = new Task("B", "C", taskSecondStartTime, taskSecondDuration);
        Epic epic = new Epic("V", "Z", epicStartTime, epicDuration);
        backedTaskManager.createEpic(epic);
        Subtask sub1 = new Subtask("D", "C", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        backedTaskManager.createTask(t2);
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

    @Test
    void testEpicStatus() {
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        LocalDateTime subtaskFirstStartTime = LocalDateTime.of(2024, Month.DECEMBER, 26, 18, 0);
        LocalDateTime subtaskSecondStartTime = LocalDateTime.of(2024, Month.DECEMBER, 28, 18, 0);
        Duration epicDuration = Duration.ofHours(21);
        Duration subtaskFirstDuration = Duration.ofHours(20);
        Duration subtaskSecondDuration = Duration.ofHours(20);

        Epic epic = new Epic("V", "Z", epicStartTime, epicDuration);
        taskManager.createEpic(epic);
        Subtask sub1 = new Subtask("D", "C", epic.getTaskId(), subtaskFirstStartTime, subtaskFirstDuration);
        taskManager.createSubtask(sub1);
        Subtask sub2 = new Subtask("S", "F", epic.getTaskId(), subtaskSecondStartTime, subtaskSecondDuration);
        taskManager.createSubtask(sub2);

        Assertions.assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(epic.getTaskId()).getStatus());

        sub1.setStatus("DONE");
        taskManager.updateSubtask(sub1);
        sub2.setStatus("DONE");
        taskManager.updateSubtask(sub2);

        Assertions.assertEquals(Status.DONE, taskManager.getEpicById(epic.getTaskId()).getStatus());

        sub1.setStatus("NEW");
        taskManager.updateSubtask(sub1);

        Assertions.assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(epic.getTaskId()).getStatus());

        sub1.setStatus("IN_PROGRESS");
        taskManager.updateSubtask(sub1);
        sub2.setStatus("IN_PROGRESS");
        taskManager.updateSubtask(sub2);

        Assertions.assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(epic.getTaskId()).getStatus());
    }

    
}
