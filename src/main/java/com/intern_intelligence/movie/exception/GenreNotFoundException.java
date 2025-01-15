package com.intern_intelligence.movie.exception;

public class GenreNotFoundException extends RuntimeException{

    public GenreNotFoundException() {
        super(ErrorMessage.GENRE_NOT_FOUND.getMessage());
    }
}
