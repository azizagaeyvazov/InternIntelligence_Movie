package com.intern_intelligence.movie.service;

import com.intern_intelligence.movie.dto.GenreRequest;
import com.intern_intelligence.movie.dto.GenreResponse;
import com.intern_intelligence.movie.model.Genre;

import java.util.List;

public interface GenreService {

    void add(GenreRequest genreRequest);

    List<GenreResponse> getAll();

    Genre getById(Long id);

    void update(Long id, GenreRequest genreRequest);

    void deleteById(Long id);
}
