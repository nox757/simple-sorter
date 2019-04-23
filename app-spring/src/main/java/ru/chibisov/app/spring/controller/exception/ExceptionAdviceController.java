package ru.chibisov.app.spring.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.chibisov.app.spring.dto.ResponseMessage;
import ru.chibisov.app.spring.exception.NotFoundException;

@RestControllerAdvice
public class ExceptionAdviceController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseMessage handleNotFoundException(NotFoundException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseMessage handleNotFoundException(Exception ex) {
        return new ResponseMessage(ex.getMessage());
    }
}
