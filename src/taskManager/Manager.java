package taskManager;

import tasks.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    static int taskId = 0;

    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer ,Subtask> subtasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();

    public void createTask(Task userTask){
        taskId ++;
        userTask.setTaskId(taskId);
        tasks.put(userTask.getTaskId(), userTask);
    }

    public void createSubtask(Subtask userTask){
        taskId ++;
        userTask.setTaskId(taskId);
        epics.get(userTask.getEpicId()).setStatus("IN_PROGRESS");
        epics.get(userTask.getEpicId()).addSubtask(userTask);
        subtasks.put(userTask.getTaskId(), userTask);
    }

    public void createEpic(Epic epic){
        taskId ++;
        epic.setTaskId(taskId);
        epics.put(epic.getTaskId(), epic);
    }

    public void setTaskStatus(Task task, String status){
        task.setStatus(status);
    }

    public void setSubtaskStatus(Subtask subtask, String status){
        if(status.equals("DONE")){
            for(Integer subId : epics.get(subtask.getEpicId()).getSubTasks()){
                if(subtasks.get(subId).getStatus() != Status.DONE){
                    break;
                }
                epics.get(subtask.getEpicId()).setStatus("DONE");
            }
        }
        subtask.setStatus(status);
    }

    public ArrayList<Task> getListOfTasks(){
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.addAll(tasks.values());
        allTasks.addAll(subtasks.values());
        allTasks.addAll(epics.values());
        return allTasks;
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
        if(subtasks.get(id) != null){
            return subtasks.get(id);
        }
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
            epics.get(newTask.getEpicId()).setStatus("IN_PROGRESS");
            subtasks.put(newTask.getTaskId(), newTask);
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
    }

    public void removeEpics(){
        epics.clear();
    }

    public void removeTasksById(int id){
        tasks.remove(id);
    }

    public void removeSubtasksById(int id){
        int epicId = subtasks.get(id).getEpicId();
        subtasks.remove(id);
        for(Integer subId : epics.get(epicId).getSubTasks()){
            if(subtasks.get(subId).getStatus() != Status.DONE){
                break;
            }
            epics.get(epicId).setStatus("DONE");
        }
    }

    public void removeEpicsById(int id){
        epics.remove(id);
    }
}
