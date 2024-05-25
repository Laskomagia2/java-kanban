package taskManager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

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

    private void save() throws ManagerSaveException{
        Path path = Paths.get("SavedTasks.csv");
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

    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException{
        FileBackedTaskManager manager = new FileBackedTaskManager();
        try (FileReader reader = new FileReader(file)) {
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                String line = br.readLine();
                if (line.contains("TASK")) {
                    manager.createTask(fromString(line));
                } else if (line.contains("SUBTASK")) {
                    manager.createSubtask((Subtask)fromString(line));
                } else {
                    manager.createEpic((Epic)fromString(line));
                }
            }
        } catch (IOException except) {
            throw new ManagerSaveException(except);
        }
        return manager;
    }

    public static String taskToString(Task task) {
        String res;
        res = String.format("%d,"+task.getTaskType()+",%s,"+task.getStatus()+",%s", task.getTaskId(), task.getName(), task.getContext());
        if (task.getTaskType() == TaskType.SUBTASK) {
            res += "," + ((Subtask) task).getEpicId();
        }
        return res + "\n";
    }

    public static Task fromString(String value) {
        String[] temp = value.split(",");
        if (TaskType.valueOf(temp[1]) == TaskType.TASK) {
            Task task = new Task(temp[2], temp[4]);
            task.setStatus(temp[3]);
            task.setTaskId(Integer.parseInt(temp[0]));
            return task;
        } else if (TaskType.valueOf(temp[1]) == TaskType.SUBTASK) {
            Subtask subtask = new Subtask(temp[2], temp[4], Integer.valueOf(temp[5]));
            subtask.setStatus(temp[3]);
            subtask.setTaskId(Integer.parseInt(temp[0]));
            return  subtask;
        } else {
            Epic epic = new Epic(temp[2], temp[4]);
            epic.setStatus(temp[3]);
            epic.setTaskId(Integer.parseInt(temp[0]));
            return epic;
        }
    }
}
