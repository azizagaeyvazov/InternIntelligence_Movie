package com.intern_intelligence.movie.service;

import com.intern_intelligence.movie.dto.MovieRequest;
import com.intern_intelligence.movie.model.Movie;

import java.util.List;

public interface MovieService {

    void add(MovieRequest movieRequest);

    List<Movie> getAll();

    Movie update(Long id, MovieRequest movieRequest);

    void delete(Long id);
}
