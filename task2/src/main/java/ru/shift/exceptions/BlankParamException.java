package ru.shift.exceptions;

import ru.shift.constants.Messages;

public class BlankParamException extends IllegalArgumentException {
    public BlankParamException() {
        super(Messages.STRING_IS_BLANK);
    }
}
