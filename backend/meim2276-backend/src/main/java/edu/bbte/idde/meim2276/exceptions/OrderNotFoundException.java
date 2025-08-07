package edu.bbte.idde.meim2276.exceptions;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String message, Exception e) {
        super(message, e);
    }

}
