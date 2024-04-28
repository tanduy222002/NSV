package nsv.com.nsvserver.Exception;

public class SlotOverContaining extends RuntimeException{
    public SlotOverContaining() {
        super("The bin over the capacity of slot");
    }

    public SlotOverContaining(String message) {
        super(message);
    }
}
