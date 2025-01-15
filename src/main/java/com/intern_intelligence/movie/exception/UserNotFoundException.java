package com.intern_intelligence.movie.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super(ErrorMessage.USER_NOT_FOUND.getMessage());
    }
}
