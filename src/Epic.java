import java.util.ArrayList;
public class Epic extends AbstractTask{
    protected ArrayList<Task> subtasks;
    public Epic(String name, String context) {
        super(name, context);
        subtasks = new ArrayList<>();
    }

    @Override
    public void statusIsDone(){
        for (Task task : subtasks){
            if (task.status != Status.DONE){
                return;
            }
        }
        setStatus("DONE");
        Manager.doneTasks.put(this.taskId, this);
    }

    public void addSubtask(Task subtask){
        subtasks.add(subtask);
    }

    public void setSubtaskDone(Task task){
        task.statusIsDone();
        for(Task t : subtasks){
            if(t.equals(task)){
                t.statusIsDone();
                Manager.doneTasks.put(t.taskId, t);
            }
        }
    }

    @Override
    public String toString() {

        String result = "Task{" +
                "name='" + name + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskStatus=" + status + '\'' +
                ", subtasks=" + subtasks + '\'';

        if(context != null) { // проверяем, что поле не содержит null
            result = result + ", extraInfo.length=" + context.length(); // выводим не значение, а длину
        } else {
            result = result + ", extraInfo=null"; // выводим информацию, что поле равно null
        }

        return result + '}';
    }
}
