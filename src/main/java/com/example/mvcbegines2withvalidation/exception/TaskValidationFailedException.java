package com.example.mvcbegines2withvalidation.exception;

public class TaskValidationFailedException extends RuntimeException {

    public TaskValidationFailedException(String message) {
        super(message);
    }
}
