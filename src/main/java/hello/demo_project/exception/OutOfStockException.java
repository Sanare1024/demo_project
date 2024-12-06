package hello.demo_project.exception;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
    }

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
