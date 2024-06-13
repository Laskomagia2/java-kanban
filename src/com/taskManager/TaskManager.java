package com.taskManager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void createTask(Task userTask);

    void createSubtask(Subtask userTask);

    void createEpic(Epic epic);

    ArrayList<Task> getListOfTasks();

    ArrayList<Task> getListOfSubtasks();

    ArrayList<Task> getListOfEpics();

    ArrayList<Subtask> getEpicSubtasks(Epic epic);

    Task getTaskById(Integer id);

    Subtask getSubtaskById(Integer id);

    Epic getEpicById(Integer id);

    void updateTask(Task newTask);

    void updateSubtask(Subtask newTask);

    void updateEpic(Epic newEpic);

    void removeTasks();

    void removeSubtasks();

    void removeEpics();

    void removeTasksById(int id);

    void removeSubtasksById(int subId);

    void removeEpicsById(int epicId);

    void removeAllTasks();

    List<Task> getHistory();
}
