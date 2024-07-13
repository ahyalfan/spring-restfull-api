package my.ahyalfan.restfull.restfull.exception;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
