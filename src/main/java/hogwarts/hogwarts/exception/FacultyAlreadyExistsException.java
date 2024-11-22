package hogwarts.hogwarts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class FacultyAlreadyExistsException extends RuntimeException{
}
