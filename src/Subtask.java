public class Subtask extends Task {
    protected Integer epicId;

    protected Epic epic;

    public Subtask(String name, String context, Epic epic){
        super(name, context);
        this.epic = epic;
        epicId = epic.getTaskId();
        epic.subtasks.put(this.getTaskId(), this);
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public void statusIsDone(){
        this.setStatus("DONE");
        /*for (Subtask task : epic.subtasks.values()){
            if (task.getStatus() != Status.DONE){
                break;
            }
            epic.statusIsDone();
        } */
    }
}
