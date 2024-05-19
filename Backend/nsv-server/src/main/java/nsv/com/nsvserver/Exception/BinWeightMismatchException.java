package nsv.com.nsvserver.Exception;

public class BinWeightMismatchException extends RuntimeException{
    public BinWeightMismatchException() {
        super("The weight of bin in slots mismatch with the total weight of bin");
    }

    public BinWeightMismatchException(String message) {
        super(message);
    }
}
