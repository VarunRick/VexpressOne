package com.vexpress.vexpressbackend.exception;

public class EmployeeNotFoundException extends RuntimeException {
    private final long id;
    public EmployeeNotFoundException(String message, long id) {
        super(message);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
