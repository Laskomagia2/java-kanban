package programExceptions;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException(Exception except) {
        super(except);
    }
}
