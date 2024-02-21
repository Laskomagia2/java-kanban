public class Subtask extends Task {
    protected Integer epicId;

    public Subtask(String name, String context, Epic epic){
        super(name, context);
        epicId = epic.getTaskId();
        epic.subtasks.put(this.getTaskId(), this);
    }

    public Integer getEpicId() {
        return epicId;
    }
}
