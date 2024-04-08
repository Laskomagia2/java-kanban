package tasks;

public class Subtask extends Task {
    public Integer epicId;

    public Subtask(String name, String context, Integer epicId){
        super(name, context);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }
}
