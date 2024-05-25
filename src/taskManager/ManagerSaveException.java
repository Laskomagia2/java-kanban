package taskManager;

public class ManagerSaveException extends RuntimeException {

    ManagerSaveException (Throwable except) {
        super(except);
    }
}
