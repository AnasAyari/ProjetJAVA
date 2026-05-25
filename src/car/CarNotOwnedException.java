package car;

public class CarNotOwnedException extends RuntimeException {
    public CarNotOwnedException(String message) { super(message); }
    public CarNotOwnedException(String message, Throwable cause) { super(message, cause); }
}
