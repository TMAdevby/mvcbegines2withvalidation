package com.example.mvcbegines2withvalidation.exception;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String id) {
        super("Task number " + id + " is not found");
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotFoundException(Throwable cause) {
        super(cause);
    }


}
