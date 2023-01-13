package com.assessment.userapi.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@SuppressWarnings("unchecked")
public class ExceptionHandling {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity userNotFoundException(UserNotFoundException userNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(userNotFoundException);
        return new ResponseEntity(errorResponse, userNotFoundException.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity validationErrors(MethodArgumentNotValidException methodArgumentNotValidException){
        List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleException(HttpMessageNotReadableException httpMessageNotReadableException) {
        String message = null;
        if (httpMessageNotReadableException.getCause() instanceof JsonMappingException){
            JsonMappingException jme = (JsonMappingException) httpMessageNotReadableException.getCause();

            message = "Invalid field " +jme.getPath().get(0).getFieldName();
        }else{
            message = httpMessageNotReadableException.getMessage();
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, message);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
