package car;

public class InvalidCarConfigurationException extends Exception {
    public InvalidCarConfigurationException(String message) {
        super(message);
    }

    public InvalidCarConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}