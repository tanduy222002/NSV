package nsv.com.nsvserver.Exception;

public class RefreshTokenExpiredException extends RuntimeException{
    public RefreshTokenExpiredException() {
        super("Refresh token expired");
    }

    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}
