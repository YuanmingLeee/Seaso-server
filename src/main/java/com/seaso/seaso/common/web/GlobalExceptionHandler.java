package com.seaso.seaso.common.web;

import com.seaso.seaso.common.exception.ResourceConflictException;
import com.seaso.seaso.common.exception.ResourceNotFoundException;
import com.seaso.seaso.common.exception.ServiceException;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final Logger serviceLogger = LoggerFactory.getLogger(ServiceException.class);

    @ExceptionHandler(ResourceConflictException.class)
    @ResponseBody
    public ResponseEntity<?> resourceConflict() {
        return new ResponseEntity<>(
                new JsonResponseBody<>("Resources conflict", 409, null),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> resourceNotFound() {
        return new ResponseEntity<>(
                new JsonResponseBody<>("Resources not found", 404, null),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<?> apiIllegalArgument(@NotNull ConstraintViolationException e) {
        String message = e.getLocalizedMessage().replaceAll("[A-Za-z]*\\.(?=[A-Za-z]*:)", "");
        return new ResponseEntity<>(
                new JsonResponseBody<>("API illegal argument: " + message, 400, null),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseBody
    public ResponseEntity<?> unauthorized(@NotNull HttpClientErrorException.Unauthorized e) {
        return new ResponseEntity<>(
                new JsonResponseBody<>("Unauthorized: " + e.getLocalizedMessage(), 401, null),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Global {@link ServiceException} handler which returns the error message with a code of {@code 50001} (Service
     * Exception).
     *
     * @param e Service exception.
     * @return a JSON response with message and error code.
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<?> serviceExcept(@NotNull ServiceException e) {
        serviceLogger.info(e.getLocalizedMessage());
        return new ResponseEntity<>(
                new JsonResponseBody<>(e.getLocalizedMessage(), 50001, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ResponseEntity<?> sqlException(@NotNull Exception e) {
        logger.info(e.getLocalizedMessage());
        return new ResponseEntity<>(
                new JsonResponseBody<>(e.getLocalizedMessage(), 50002, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> defaultErrorHandler(@NotNull Exception e) {
        logger.info(e.getLocalizedMessage());
        return new ResponseEntity<>(
                new JsonResponseBody<>(e.getLocalizedMessage(), 50000, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
