package models;

import com.taskmanager.FileBackedTaskManager;
import com.taskmanager.Managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{
    FileBackedTaskManagerTest () {
        super(Managers.getFileBacked());
    }

    @Test
    void saveAndLoadTest() {
        LocalDateTime taskFirstStartTime = LocalDateTime.of(2024, Month.DECEMBER, 20, 18, 0);
        LocalDateTime taskSecondStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        LocalDateTime epicStartTime = LocalDateTime.of(2024, Month.DECEMBER, 24, 18, 0);
        LocalDateTime subtaskStartTime = LocalDateTime.of(2024, Month.DECEMBER, 26, 18, 0);
        Duration taskFirstDuration = Duration.ofHours(20);
        Duration taskSecondDuration = Duration.ofHours(23);
        Duration epicDuration = Duration.ofHours(21);
        Duration subtaskDuration = Duration.ofHours(20);

        Task t1 = new Task("A", "B", taskFirstStartTime, taskFirstDuration);
        taskManager.createTask(t1);
        Task t2 = new Task("B", "C", taskSecondStartTime, taskSecondDuration);
        Epic epic = new Epic("V", "Z", epicStartTime, epicDuration);
        taskManager.createEpic(epic);
        Subtask sub1 = new Subtask("D", "C", epic.getTaskId(), subtaskStartTime, subtaskDuration);
        taskManager.createTask(t2);
        taskManager.createSubtask(sub1);

        List<Task> currentFileList = new ArrayList<>();

        currentFileList.addAll(taskManager.getListOfTasks());
        currentFileList.addAll(taskManager.getListOfSubtasks());
        currentFileList.addAll(taskManager.getListOfEpics());

        List<Task> loadedFileList = new ArrayList<>();

        FileBackedTaskManager newFileBackedManager = FileBackedTaskManager.loadFromFile(new File("SavedTasks.csv"));
        loadedFileList.addAll(newFileBackedManager.getListOfTasks());
        loadedFileList.addAll(newFileBackedManager.getListOfSubtasks());
        loadedFileList.addAll(newFileBackedManager.getListOfEpics());

        Assertions.assertArrayEquals(new List[]{loadedFileList}, new List[]{currentFileList});
    }


}
