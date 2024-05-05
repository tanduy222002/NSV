package nsv.com.nsvserver.Exception;

public class SlotAreaMismatchException extends RuntimeException{
    public SlotAreaMismatchException() {
        super("The bin over the capacity of slot");
    }

    public SlotAreaMismatchException(String message) {
        super(message);
    }
}
