package com.intern_intelligence.movie.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRequest {

    private String title;

    private String director;

    private Integer releaseYear;

    private String genre;

    private Integer IMDbRating;
}
