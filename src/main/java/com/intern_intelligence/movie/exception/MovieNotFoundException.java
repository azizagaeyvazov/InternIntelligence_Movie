package com.intern_intelligence.movie.exception;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException() {
        super(ErrorMessage.MOVIE_NOT_FOUND.getMessage());
    }
}
