package pl.studentmed.facultative.exceptions;

public class InvalidLengthException extends RuntimeException {

    public final String fieldName;

    public InvalidLengthException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

}
