package nsv.com.nsvserver.Exception;

public class OtpExpiredException extends RuntimeException{
    public OtpExpiredException() {
        super("Otp was expired");
    }

    public OtpExpiredException(String message) {
        super(message);
    }
}
