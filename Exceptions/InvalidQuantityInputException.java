package Exceptions;

public class InvalidQuantityInputException extends NumberFormatException {
    public InvalidQuantityInputException(String message) {
        super(message);
    }
}
