package com.cowradio.microservicesecurity.exceptions;

public class DuplicateElementException extends RuntimeException {
    Object object;
    public DuplicateElementException(String message, Object object) {
        super(message);
        this.object = object;
    }

    @Override
    public String getMessage() {
        String objectTypeName = (object != null) ? object.getClass().getSimpleName() : "null";
        return super.getMessage() + "Object: " + objectTypeName;
    }
}
