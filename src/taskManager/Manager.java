package taskManager;

import tasks.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private static int taskId = 0;

    final protected HashMap<Integer, Task> tasks = new HashMap<>();
    final protected HashMap<Integer ,Subtask> subtasks = new HashMap<>();
    final protected HashMap<Integer, Epic> epics = new HashMap<>();

    public void createTask(Task userTask){
        taskId ++;
        userTask.setTaskId(taskId);
        tasks.put(userTask.getTaskId(), userTask);
    }

    public void createSubtask(Subtask userTask){
        taskId ++;
        userTask.setTaskId(taskId);
        epics.get(userTask.getEpicId()).addSubtask(userTask);
        subtasks.put(userTask.getTaskId(), userTask);
        checkEpicStatus(epics.get(userTask.getEpicId()).getTaskId());
    }

    public void createEpic(Epic epic){
        taskId ++;
        epic.setTaskId(taskId);
        epics.put(epic.getTaskId(), epic);
    }

    public ArrayList<Task> getListOfTasks(){
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Task> getListOfSubtasks(){
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Task> getListOfEpics(){
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getEpicSubtasks(Epic epic){
        ArrayList<Subtask> currentSubtasks = new ArrayList<>();
        for (Integer id : epic.getSubTasks()){
            currentSubtasks.add(subtasks.get(id));
        }
        return currentSubtasks;
    }

    public Task getTaskById(Integer id){
        if(tasks.get(id) != null){
            return tasks.get(id);
        }
        return null;
    }

    public Subtask getSubtaskById(Integer id){
        if(subtasks.get(id) != null){
            return subtasks.get(id);
        }
        return null;
    }

    public Epic getEpicById(Integer id){
        if (epics.get(id) != null){
            return epics.get(id);
        }
        return null;
    }

    public void updateTask(Task newTask){
        if (newTask != null) {
            tasks.put(newTask.getTaskId(), newTask);
        }
    }

    public void updateSubtask(Subtask newTask){
        if (newTask != null) {
            subtasks.put(newTask.getTaskId(), newTask);
            checkEpicStatus(epics.get(newTask.getEpicId()).getTaskId());
        }
    }

    public void updateEpic(Epic newEpic){
        if (newEpic != null) {
            epics.put(newEpic.getTaskId(), newEpic);
        }
    }

    public void removeTasks(){
        tasks.clear();
    }

    public void removeSubtasks(){
        subtasks.clear();
        for (Epic epic : epics.values()){
            epic.getSubTasks().clear();
            checkEpicStatus(epic.getTaskId());
        }

    }

    public void removeEpics(){
        epics.clear();
        subtasks.clear();
    }

    public void removeTasksById(int id){
        tasks.remove(id);
    }

    public void removeSubtasksById(int subId){
        int epicId = subtasks.get(subId).getEpicId();
        subtasks.remove(subId);
        epics.get(epicId).getSubTasks().remove(subId);
        checkEpicStatus(epicId);
    }

    public void removeEpicsById(int epicId){
        for (Integer subId : epics.get(epicId).getSubTasks()){
            subtasks.remove(subId);
        }
        epics.remove(epicId);
    }

    public void checkEpicStatus(int epicId){
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
