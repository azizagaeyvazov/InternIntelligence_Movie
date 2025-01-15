package com.intern_intelligence.movie.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreRequest {


    private String genre;
}
