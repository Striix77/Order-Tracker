package edu.bbte.idde.meim2276.exceptions;

public class BadArgumentException extends Exception {
    public BadArgumentException(String message, Exception e) {
        super(message, e);
    }

}
