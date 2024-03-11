package tasks;

import java.util.ArrayList;
public class Epic extends Task{
    protected ArrayList<Integer> subtasks;
    protected Epic(String name, String context) {
        super(name, context);
        subtasks = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask){
        subtasks.add(subtask.getTaskId());
    }

    public ArrayList<Integer> getSubTasks(){
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
