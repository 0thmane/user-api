package com.assessment.userapi.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

    private int status;
    private List<String> errors = new ArrayList<>();

    public ErrorResponse(HttpStatus status, String message){
        this.status = status.value();
        this.errors.add(message);
    }
    public ErrorResponse(ApiException apiException){
        this.status = apiException.getStatus().value();
        this.errors.add(apiException.getMessage());
    }

    public ErrorResponse(HttpStatus status, List<String> errors){
        this.status = status.value();
        this.errors.addAll(errors);
    }

    public int getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }
}
