package com.seaso.seaso.common.exception;

import com.seaso.seaso.modules.sys.utils.JsonResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

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

    @ExceptionHandler(ApiIllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<?> apiIllegalArgument(ApiIllegalArgumentException e) {
        return new ResponseEntity<>(
                new JsonResponseBody<>("API illegal argument: " + e.getLocalizedMessage(), 400, null),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseBody
    public ResponseEntity<?> unauthorized(HttpClientErrorException.Unauthorized e) {
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
    public ResponseEntity<?> serviceExcept(ServiceException e) {
        serviceLogger.info(e.getLocalizedMessage());
        return new ResponseEntity<>(
                new JsonResponseBody<>(e.getLocalizedMessage(), 50001, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ResponseEntity<?> sqlException(Exception e) {
        logger.info(e.getLocalizedMessage());
        return new ResponseEntity<>(
                new JsonResponseBody<>(e.getLocalizedMessage(), 50002, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> defaultErrorHandler(Exception e) {
        logger.info(e.getLocalizedMessage());
        return new ResponseEntity<>(
                new JsonResponseBody<>(e.getLocalizedMessage(), 50000, null),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
