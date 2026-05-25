package receipt;

public class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(String message) { super(message); }
    public ReceiptNotFoundException(String message, Throwable cause) { super(message, cause); }
}
