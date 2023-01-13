package com.assessment.userapi.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }

    public UserNotFoundException(){
        this("User not found");
    }

}
