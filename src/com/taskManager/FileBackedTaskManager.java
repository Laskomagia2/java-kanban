package com.taskManager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private Path path = Paths.get("SavedTasks.csv");

    public FileBackedTaskManager() {
    }

    public FileBackedTaskManager(String path) {
        this.path = Paths.get(path);
    }

    @Override
    public void createTask(Task userTask) {
        super.createTask(userTask);
        save();
    }

    @Override
    public void createSubtask(Subtask sub) {
        super.createSubtask(sub);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateTask(Task newTask) {
        super.updateTask(newTask);
        save();
    }

    @Override
    public void updateSubtask(Subtask newTask) {
        super.updateSubtask(newTask);
        save();
    }

    @Override
    public void updateEpic(Epic newEpic) {
        super.updateEpic(newEpic);
        save();
    }

    @Override
    public void removeTasks() {
        super.removeTasks();
        save();
    }

    @Override
    public void removeSubtasks() {
        super.removeSubtasks();
        save();
    }

    @Override
    public void removeEpics() {
        super.removeEpics();
        save();
    }

    @Override
    public void removeTasksById(int id) {
        super.removeTasksById(id);
        save();
    }

    @Override
    public void removeSubtasksById(int subId) {
        super.removeSubtasksById(subId);
        save();
    }

    @Override
    public void removeEpicsById(int epicId) {
        super.removeEpicsById(epicId);
        save();
    }

    private void save() {
        try (FileWriter writer = new FileWriter(String.valueOf(path), StandardCharsets.UTF_8)) {
            for (Task task : tasks.values()) {
                writer.write(taskToString(task));
            }
            for (Epic epic : epics.values()) {
                writer.write(taskToString(epic));
            }
            for (Subtask subtask : subtasks.values()) {
                writer.write(taskToString(subtask));
            }
        } catch (IOException except) {
            throw new ManagerSaveException(except);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager();
        try (FileReader reader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                String line = br.readLine();
                String[] splitLine = line.split(",");
                TaskType taskType = TaskType.valueOf(splitLine[1]);
                switch (taskType) {
                    case TASK:
                        manager.createTask(fromString(line));
                        break;
                    case SUBTASK:
                        manager.createSubtask((Subtask)fromString(line));
                        break;
                    case EPIC:
                        manager.createEpic((Epic)fromString(line));
                        break;
                    default:
                        throw new IllegalArgumentException("Недопустимое значение " + taskType);
                }
            }
        } catch (IOException except) {
            throw new ManagerSaveException(except);
        }
        return manager;
    }

    public static String taskToString(Task task) {
        String res;
        res = String.format("%d," + task.getTaskType() + ",%s," + task.getStatus() + ",%s", task.getTaskId(), task.getName(), task.getContext());
        if (task.getTaskType() == TaskType.SUBTASK) {
            StringBuilder sb = new StringBuilder(res);
            sb.append(",");
            sb.append(((Subtask) task).getEpicId());
            res = sb.toString();
        }
        return res + "\n";
    }

    public static Task fromString(String value) {
        String[] temp = value.split(",");
        int currentTaskId = Integer.parseInt(temp[0]);
        TaskType taskType = TaskType.valueOf(temp[1]);
        String taskName = temp[2];
        String taskStatus = temp[3];
        String taskContext = temp[4];

        switch (taskType) {
            case TASK:
                Task task = new Task(taskName, taskContext);
                task.setStatus(taskStatus);
                task.setTaskId(currentTaskId);
                return task;

            case SUBTASK:
                int subEpicId = Integer.parseInt(temp[5]);
                Subtask subtask = new Subtask(taskName, taskContext, subEpicId);
                subtask.setStatus(taskStatus);
                subtask.setTaskId(currentTaskId);
                return subtask;

            case EPIC:
                Epic epic = new Epic(taskName, taskContext);
                epic.setStatus(taskStatus);
                epic.setTaskId(currentTaskId);
                return epic;

            default:
                throw new IllegalArgumentException("Недопустимое значене: " + taskType);
        }
    }
}
