package edu.java.controllers;

import edu.java.DTO.ExceptionResponse;
import edu.java.exceptions.DoubleChatIdException;
import edu.java.exceptions.NoChatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionLinkController {
    @ExceptionHandler(DoubleChatIdException.class)
    public ResponseEntity<ExceptionResponse> handleDoubleChatIdException(DoubleChatIdException ex) {
        return new ResponseEntity<>(
            new ExceptionResponse(ex.getClass().getName(), ex.getMessage()),
            HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(NoChatException.class)
    public ResponseEntity<ExceptionResponse> handleNoChatException(NoChatException ex) {
        return new ResponseEntity<>(
            new ExceptionResponse(
                ex.getClass().getName(),
                ex.getMessage()
            ),
            HttpStatus.UNAUTHORIZED
        );
    }

}
