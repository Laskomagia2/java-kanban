package com.taskManager;

import tasks.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private static int taskId = 0;

    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();

    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void createTask(Task userTask) {
        if (userTask.getTaskId() == 0) {
            taskId = userTask.hashCode();
            userTask.setTaskId(taskId);
        }
        tasks.put(userTask.getTaskId(), userTask);
    }

    @Override
    public void createSubtask(Subtask userTask) {
        if (userTask.getTaskId() == 0) {
            taskId = userTask.hashCode();
            userTask.setTaskId(taskId);
        }
        epics.get(userTask.getEpicId()).addSubtask(userTask.getTaskId());
        subtasks.put(userTask.getTaskId(), userTask);
        checkEpicStatus(epics.get(userTask.getEpicId()).getTaskId());
    }

    @Override
    public void createEpic(Epic epic) {
        if (epic.getTaskId() == 0) {
            taskId = epic.hashCode();
            epic.setTaskId(taskId);
        }
        epics.put(epic.getTaskId(), epic);
    }

    @Override
    public ArrayList<Task> getListOfTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Task> getListOfSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Task> getListOfEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        ArrayList<Subtask> currentSubtasks = new ArrayList<>();
        for (Integer id : epic.getSubTasks()) {
            currentSubtasks.add(subtasks.get(id));
        }
        return currentSubtasks;
    }

    @Override
    public Task getTaskById(Integer id) {
        if (tasks.get(id) != null) {
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        }
        return null;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        if (subtasks.get(id) != null) {
            historyManager.add(subtasks.get(id));
            return subtasks.get(id);
        }
        return null;
    }

    @Override
    public Epic getEpicById(Integer id) {
        if (epics.get(id) != null) {
            historyManager.add(epics.get(id));
            return epics.get(id);
        }
        return null;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void updateTask(Task newTask) {
        if (newTask != null) {
            tasks.put(newTask.getTaskId(), newTask);
        }
    }

    @Override
    public void updateSubtask(Subtask newTask) {
        if (newTask != null) {
            subtasks.put(newTask.getTaskId(), newTask);
            checkEpicStatus(epics.get(newTask.getEpicId()).getTaskId());
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (newEpic != null) {
            epics.put(newEpic.getTaskId(), newEpic);
        }
    }

    @Override
    public void removeTasks() {
        for (Integer id : tasks.keySet()) {
            historyManager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public void removeSubtasks() {
        for (Integer id : subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubTasks().clear();
            checkEpicStatus(epic.getTaskId());
        }

    }

    @Override
    public void removeEpics() {
        for (Integer id : epics.keySet()) {
            historyManager.remove(id);
        }
        epics.clear();
        for (Integer id : subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
    }

    @Override
    public void removeTasksById(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeSubtasksById(int subId) {
        int epicId = subtasks.get(subId).getEpicId();
        subtasks.remove(subId);
        historyManager.remove(subId);
        epics.get(epicId).getSubTasks().remove(subId);
        checkEpicStatus(epicId);
    }

    @Override
    public void removeEpicsById(int epicId) {
        for (Integer subId : epics.get(epicId).getSubTasks()) {
            subtasks.remove(subId);
            historyManager.remove(subId);
        }
        epics.remove(epicId);
        historyManager.remove(epicId);
    }

    public void checkEpicStatus(int epicId) {
        if (epics.get(epicId).getSubTasks().isEmpty()) {
            epics.get(epicId).setStatus("NEW");
        } else {
            for (Integer subId : epics.get(epicId).getSubTasks()) {
                if (subtasks.get(subId).getStatus() != Status.DONE) {
                    epics.get(epicId).setStatus("IN_PROGRESS");
                    return;
                }
            }
            epics.get(epicId).setStatus("DONE");
        }
    }

}
