package com.intern_intelligence.movie.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MovieResponse {

    private String title;

    private String director;

    private Integer releaseYear;

    private List<GenreResponse> genre;

    private Double IMDbRating;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
