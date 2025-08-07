package edu.bbte.idde.meim2276.backend.handlers;

import edu.bbte.idde.meim2276.backend.exceptions.BadArgumentException;
import edu.bbte.idde.meim2276.backend.exceptions.BadConnectionException;
import edu.bbte.idde.meim2276.backend.exceptions.DatabaseException;
import edu.bbte.idde.meim2276.backend.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleOrderNotFoundException(OrderNotFoundException ex) {
        Map<String, String> errors = new ConcurrentHashMap<>();
        errors.put("error", ex.getMessage());
        errors.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        return errors;
    }


    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleDatabaseException(DatabaseException ex) {
        Map<String, String> errors = new ConcurrentHashMap<>();
        errors.put("error", ex.getMessage());
        errors.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return errors;
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNumberFormatException(NumberFormatException ex) {
        Map<String, String> errors = new ConcurrentHashMap<>();
        errors.put("error", ex.getMessage());
        errors.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return errors;
    }

    @ExceptionHandler(BadConnectionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleBadConnectionException(BadConnectionException ex) {
        Map<String, String> errors = new ConcurrentHashMap<>();
        errors.put("error", ex.getMessage());
        errors.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return errors;
    }

    @ExceptionHandler(BadArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadArgumentException(BadArgumentException ex) {
        Map<String, String> errors = new ConcurrentHashMap<>();
        errors.put("error", ex.getMessage());
        errors.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return errors;
    }

}