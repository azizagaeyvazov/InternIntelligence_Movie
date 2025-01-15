package com.intern_intelligence.movie.exception;

import static com.intern_intelligence.movie.exception.ErrorMessage.INVALID_TOKEN;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException() {
        super(INVALID_TOKEN.getMessage());
    }
}
