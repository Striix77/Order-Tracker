package edu.bbte.idde.meim2276.backend.exceptions;

public class BadConnectionException extends Exception {
    public BadConnectionException(String message, Exception e) {
        super(message, e);
    }

}
