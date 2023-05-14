package pl.studentmed.facultative.exceptions;

public class InvalidSignsException extends RuntimeException{

    public final String fieldName;

    public InvalidSignsException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

}
