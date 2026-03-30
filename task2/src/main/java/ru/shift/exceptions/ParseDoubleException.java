package ru.shift.exceptions;

public class ParseDoubleException extends IllegalArgumentException {
    public ParseDoubleException(String reason) {
        super(reason);
    }
}
