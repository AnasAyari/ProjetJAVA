package car;

public class DuplicateCarException extends RuntimeException {
    public DuplicateCarException(String message) { super(message); }
    public DuplicateCarException(String message, Throwable cause) { super(message, cause); }
}
