package com.taskManager;

import programComparators.CompareTasksByDate;
import programexceptions.DateTimeIntersectionException;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private static int taskId = 0;
    protected final CompareTasksByDate dateComparator = new CompareTasksByDate();


    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();

    protected final TreeSet<Task> sortedTasksByDate = new TreeSet<>(dateComparator);
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void createTask(Task userTask) {
        if (userTask.getTaskId() == 0) {
            userTask.setTaskId(userTask.hashCode());
        }
        tasks.put(userTask.getTaskId(), userTask);
        if (userTask.getStartTime() != null) {
            try {
                if (isTimeIntersection(userTask)) {
                    throw new DateTimeIntersectionException();
                }
                sortedTasksByDate.add(userTask);
            } catch (DateTimeIntersectionException ex) {
                System.out.println("DateTime intersection: " + ex.getMessage());
                throw ex;
            }
        }
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
        checkEpicTime(userTask.getEpicId());
        if (userTask.getStartTime() != null) {
            try {
                if (isTimeIntersection(userTask)) {
                    throw new DateTimeIntersectionException();
                }
                sortedTasksByDate.add(userTask);
            } catch (DateTimeIntersectionException ex) {
                System.out.println("DateTime intersection: " + ex.getMessage());
                throw ex;
            }
        }
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

    public ArrayList<Task> getListOfAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.addAll(getListOfTasks());
        allTasks.addAll(getListOfSubtasks());
        allTasks.addAll(getListOfEpics());
        return allTasks;
    }

    @Override
    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtasks().stream()
                .map(subtasks::get)
                .collect(Collectors.toCollection(ArrayList::new));
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

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(sortedTasksByDate);
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
            checkEpicTime(epics.get(newTask.getEpicId()).getTaskId());
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (newEpic != null) {
            epics.put(newEpic.getTaskId(), newEpic);
            checkEpicTime(newEpic.getTaskId());
        }
    }

    @Override
    public void removeTasks() {
        tasks.keySet()
                .forEach(historyManager::remove);
        tasks.clear();
    }

    @Override
    public void removeSubtasks() {
        subtasks.keySet()
                .forEach(historyManager::remove);
        subtasks.clear();
        epics.values()
                .forEach(epic -> {
                    epic.getSubtasks().clear();
                    checkEpicStatus(epic.getTaskId());
                    checkEpicTime(epic.getTaskId());
                });
    }

    @Override
    public void removeEpics() {
        epics.keySet()
                .forEach(historyManager::remove);
        epics.clear();
        subtasks.keySet()
                .forEach(historyManager::remove);
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
        epics.get(epicId).getSubtasks().remove((Integer) subId);
        checkEpicStatus(epicId);
        checkEpicTime(epicId);
    }

    @Override
    public void removeEpicsById(int epicId) {
        epics.get(epicId).getSubtasks()
                .forEach(subtask -> {
                    subtasks.remove(subtask);
                    historyManager.remove(subtask);
                });
        epics.remove(epicId);
        historyManager.remove(epicId);
    }

    @Override
    public void removeAllTasks() {
        removeTasks();
        removeEpics();
    }

    protected void checkEpicStatus(int epicId) {
        if (epics.get(epicId).getSubtasks().isEmpty()) {
            epics.get(epicId).setStatus("NEW");
        } else {
            boolean anyNotDone = epics.get(epicId).getSubtasks().stream()
                    .anyMatch(subId -> subtasks.get(subId).getStatus() != Status.DONE);
            if (anyNotDone) {
                epics.get(epicId).setStatus("IN_PROGRESS");
            } else {
                epics.get(epicId).setStatus("DONE");
            }
        }
    }

    protected void checkEpicTime(int epicId) {
        Epic thisEpic = epics.get(epicId);
        if (thisEpic.getSubtasks().size() == 1) {
            Subtask firstSubtaskInEpic = subtasks.get(thisEpic.getSubtasks().get(0));
            thisEpic.setStartTime(firstSubtaskInEpic.getStartTime());
            thisEpic.setDuration(thisEpic.getDuration().plus(firstSubtaskInEpic.getDuration()));
            thisEpic.setEndTime(firstSubtaskInEpic.getStartTime().plus(thisEpic.getDuration()));
        } else if (thisEpic.getSubtasks().isEmpty()) {
            thisEpic.setStartTime(LocalDateTime.of(1,1,1,0,0));
            thisEpic.setEndTime(LocalDateTime.of(1,1,1,0,0));
            thisEpic.setDuration(Duration.ZERO);
        } else {
            thisEpic.getSubtasks()
                    .forEach(subId -> thisEpic.setDuration(thisEpic.getDuration()
                            .plus(subtasks.get(subId).getDuration())));
            thisEpic.setEndTime(thisEpic.getStartTime().plus(thisEpic.getDuration()));
        }
        checkEpicStatus(epicId);
    }

    protected boolean isTimeIntersection(Task task) {
        return getPrioritizedTasks().stream()
                .anyMatch(currentTask -> (currentTask.getStartTime().isBefore(task.getEndTime())
                        && task.getStartTime().isBefore(currentTask.getEndTime())));
    }
}
