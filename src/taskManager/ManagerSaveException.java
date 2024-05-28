package taskManager;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException (Exception except) {
        super(except);
    }
}
