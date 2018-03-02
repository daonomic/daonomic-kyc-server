package io.daonomic.kyc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.NoSuchFileException;

@ControllerAdvice
public class ApiExceptionHandler {
    public static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(NoSuchFileException.class)
    public ResponseEntity<Void> error(NoSuchFileException e) {
        logger.error("file not found", e);
        return new ResponseEntity<>((Void) null, HttpStatus.NOT_FOUND);
    }
}
