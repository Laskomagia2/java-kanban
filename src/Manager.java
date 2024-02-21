import java.util.HashMap;

public class Manager {
    protected HashMap<Integer, Task> allTasks = new HashMap<>();
    protected HashMap<Integer ,Task> newTasks = new HashMap<>();
    protected HashMap<Integer, Task> inProgressTasks = new HashMap<>();
    protected HashMap<Integer,Task> doneTasks = new HashMap<>();

    public void createTask(Task userTask){
        newTasks.put(userTask.taskId, userTask);
        allTasks.put(userTask.getTaskId(), userTask);
    }

    public void createSubtask(Task userTask){
        newTasks.put(userTask.taskId, userTask);
        allTasks.put(userTask.getTaskId(), userTask);
    }

    public void createEpic(Epic epic){
        newTasks.put(epic.taskId, epic);
        allTasks.put(epic.getTaskId(), epic);
    }

    public HashMap<Integer, Task> getListOfTasks(){
        return allTasks;
    }

    protected HashMap<Integer,Subtask> getEpicSubtasks(Epic epic){
        return epic.getSubTasks();
    }

    public Task getTaskById(Integer id){
        if (allTasks.containsKey(id)){
            return allTasks.get(id);
        }
        return null;
    }

    public HashMap<Integer, Task> getDoneTasks() {
        return doneTasks;
    }

    public HashMap<Integer, Task> getInProgressTasks() {
        return inProgressTasks;
    }

    public HashMap<Integer, Task> getNewTasks() {
        return newTasks;
    }

    public void changeTask(Task currentTask, Task newTask){
        currentTask = newTask;
    }

    public void removeAllTasks(){
        allTasks.clear();
        newTasks.clear();
        inProgressTasks.clear();
        doneTasks.clear();
    }

    public void removeAllTasksById(int id){
        allTasks.remove(id);
        newTasks.remove(id);
        inProgressTasks.remove(id);
        doneTasks.remove(id);
    }
}
