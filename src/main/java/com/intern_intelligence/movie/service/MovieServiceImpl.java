package com.intern_intelligence.movie.service;

import com.intern_intelligence.movie.dto.MovieRequest;
import com.intern_intelligence.movie.mapper.MovieMapper;
import com.intern_intelligence.movie.model.Movie;
import com.intern_intelligence.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    private final MovieMapper mapper;

    @Override
    public void add(MovieRequest movieRequest) {
        var movie = mapper.dtoToEntity(movieRequest);
        repository.save(movie);
    }

    @Override
    public List<Movie> getAll() {
        return repository.findAll();
    }

    @Override
    public Movie update(Long id, MovieRequest movieRequest) {
        var movie = repository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        mapper.updateMovie(movieRequest, movie);
        return repository.save(movie);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
