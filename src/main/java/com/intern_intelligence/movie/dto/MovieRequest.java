package com.intern_intelligence.movie.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieRequest {

    @NotEmpty(message = "title is required")
    private String title;

    @NotEmpty(message = "director is required")
    private String director;

    @NotEmpty(message = "releaseYear is required")
    private Integer releaseYear;

    @NotEmpty(message = "genre id(s) is/are required")
    private Long genreId;

    @NotEmpty(message = "IMDbRating is required")
    private Double IMDbRating;
}
