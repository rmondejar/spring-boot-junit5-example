package example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class DuplicatedEntityException extends RuntimeException {

    public DuplicatedEntityException() {
        super();
    }

    public DuplicatedEntityException(String message) {
        super(message);
    }
}