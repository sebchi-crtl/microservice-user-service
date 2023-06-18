package com.multree.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ControllerAdvisor {
//extends ResponseEntityExceptionHandler
    @ExceptionHandler(value = {UserEmailNotValidException.class})
    public ResponseEntity<Object> handleEmailNotValidException(
            UserEmailNotValidException ex) {

        HttpStatus notAcceptable = HttpStatus.NOT_ACCEPTABLE;

        ApiException apiException = new ApiException(
                ex.getMessage(),
                notAcceptable,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, notAcceptable);
//        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {UserAuthenticationConflictException.class})
    public ResponseEntity<Object> handleEmailNotValidException(
            UserAuthenticationConflictException ex) {

        HttpStatus badRequest = HttpStatus.CONFLICT;

        ApiException apiException = new ApiException(
                ex.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
//        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
    }


//    @ExceptionHandler(value
//            = { IllegalArgumentException.class,
//            IllegalStateException.class })
//    protected ResponseEntity<Object> handleConflict(
//            RuntimeException ex, WebRequest request) {
//        String bodyOfResponse = "This should be application specific";
//        var responseEntity = handleExceptionInternal(ex, bodyOfResponse,
//                new HttpHeaders(), HttpStatus.CONFLICT, request);
//        return responseEntity;
//    }
}
