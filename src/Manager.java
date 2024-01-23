import java.util.HashMap;
import java.util.ArrayList;

public class Manager {
    public static ArrayList<AbstractTask> allTasks = new ArrayList<>();
    public static HashMap<Integer ,AbstractTask> newTasks = new HashMap<>();
    public static HashMap<Integer, AbstractTask> inProgressTasks = new HashMap<>();
    public static HashMap<Integer,AbstractTask> doneTasks = new HashMap<>();

    public void createTask(Task userTask){
        newTasks.put(userTask.taskId, userTask);
    }

    public void createEpic(Epic epic){
        newTasks.put(epic.taskId, epic);
    }

    public void putTaskInEpic(Epic epic, Task task){
        epic.addSubtask(task);
    }

    public ArrayList<AbstractTask> getListOfTasks(){
        return allTasks;
    }

    public AbstractTask getTaskById(Integer id){
        for(AbstractTask task : allTasks){
            if(id.equals(task.getTaskId())){
                return task;
            }
        }
        return null;
    }

    public void taskInProgress(AbstractTask task){
        task.setStatus("IN_PROGRESS");
        inProgressTasks.put(task.getTaskId(), task);
    }

    public void doneTask(Task task){
        task.statusIsDone();
    }

    public void doneSubtask(Epic epic, Task subtask){
        epic.setSubtaskDone(subtask);
    }

    public void changeTask(AbstractTask currentTask, AbstractTask newTask){
        currentTask = newTask;
    }

    public void removeAllTasks(){
        allTasks.clear();
        newTasks.clear();
        inProgressTasks.clear();
        doneTasks.clear();
    }

    public void removeAllTasksById(int id){
        for (AbstractTask task : allTasks){
            if (task.getTaskId() == id){
                allTasks.remove(id);
            }
        }
        newTasks.remove(id);
        inProgressTasks.remove(id);
        doneTasks.remove(id);
    }
}
