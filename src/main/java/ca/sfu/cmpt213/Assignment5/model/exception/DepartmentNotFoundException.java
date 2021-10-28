package ca.sfu.cmpt213.Assignment5.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  Support class to signal certain types of exceptions
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends Exception {
    public DepartmentNotFoundException(String message) {
        super(message);
    }
}
