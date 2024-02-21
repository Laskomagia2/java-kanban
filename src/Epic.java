import java.util.HashMap;
public class Epic extends Task{
    protected HashMap<Integer, Subtask> subtasks;
    protected Epic(String name, String context) {
        super(name, context);
        subtasks = new HashMap<>();
    }

    public void addSubtask(Subtask subtask){
        subtasks.put(subtask.getTaskId(), subtask);
    }

    public HashMap<Integer, Subtask> getSubTasks(){
        return subtasks;
    }

    @Override
    public String toString() {

        String result = "Task{" +
                "name='" + name + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskStatus=" + status + '\'' +
                ", subtasks=" + subtasks + '\'';

        if(context != null) {
            result = result + ", extraInfo.length=" + context.length();
        } else {
            result = result + ", extraInfo=null";
        }

        return result + '}';
    }
}
