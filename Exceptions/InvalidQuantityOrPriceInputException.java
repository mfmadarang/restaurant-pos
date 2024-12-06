package Exceptions;

public class InvalidQuantityOrPriceInputException extends NumberFormatException {
    public InvalidQuantityOrPriceInputException(String message) {
        super(message);
    }
}
