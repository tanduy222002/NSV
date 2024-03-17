package nsv.com.nsvserver.Exception;

public class OtpNotMatchIdentifierException extends RuntimeException{
    public OtpNotMatchIdentifierException(String message) {
        super(message);
    }

    public OtpNotMatchIdentifierException() {
        super("Otp was not matched with identifier provided");

    }
}
