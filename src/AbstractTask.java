import com.sun.source.tree.BreakTree;

import java.util.Objects;

public abstract class AbstractTask {

    public enum Status{
        NEW,
        IN_PROGRESS,
        DONE
    }

    protected String name;
    protected String context;
    protected Status status;
    protected int taskId;

    public AbstractTask(String name, String context){
        this.name = name;
        this.context = context;
        this.status = Status.NEW;
        this.taskId = hashCode();
    }

    @Override
    public int hashCode(){
        int hash = 15;
        if (name != null){
            hash = name.hashCode();
        }
        if (context != null){
            hash += context.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask task = (AbstractTask) o;
        return Objects.equals(taskId, task.taskId);

    }

    public void setStatus(String status){
        this.status = Status.valueOf(status);
    }

    public void statusIsInProgress(){
        setStatus("IN_PROGRESS");
    }

    public void statusIsDone(){
        setStatus("DONE");
    }

    public void setContext(String context){
        this.context = context;
    }

    public String getName(){
        return this.name;
    }

    public String getContext(){
        return this.context;
    }

    public Status getStatus(){
        return status;
    }

    public int getTaskId() {
        return taskId;
    }

    @Override
    public String toString() {

        String result = "Task{" +
                "name='" + name + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskStatus=" + status + '\'';

        if(context != null) { // проверяем, что поле не содержит null
            result = result + ", extraInfo.length=" + context.length(); // выводим не значение, а длину
        } else {
            result = result + ", extraInfo=null"; // выводим информацию, что поле равно null
        }

        return result + '}';
    }

}
