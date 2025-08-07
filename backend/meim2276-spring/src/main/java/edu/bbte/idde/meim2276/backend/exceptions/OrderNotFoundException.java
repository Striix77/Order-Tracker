package edu.bbte.idde.meim2276.backend.exceptions;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String message, Exception e) {
        super(message, e);
    }

}
