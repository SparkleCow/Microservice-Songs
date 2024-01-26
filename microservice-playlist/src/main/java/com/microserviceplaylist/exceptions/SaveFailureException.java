package com.microserviceplaylist.exceptions;

public class SaveFailureException extends RuntimeException{

    private final Object object;
    public SaveFailureException(String message, Object object) {
        super(message);
        this.object = object;
    }
    @Override
    public String getMessage() {
        String objectTypeName = (object != null) ? object.getClass().getSimpleName() : "null";
        return super.getMessage() + " Object: " + objectTypeName;
    }
}
