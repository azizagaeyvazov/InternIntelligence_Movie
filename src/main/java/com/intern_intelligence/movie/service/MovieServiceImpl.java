package com.intern_intelligence.movie.service;

import com.intern_intelligence.movie.dto.MovieRequest;
import com.intern_intelligence.movie.exception.MovieNotFoundException;
import com.intern_intelligence.movie.mapper.MovieMapper;
import com.intern_intelligence.movie.model.Movie;
import com.intern_intelligence.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    private final MovieMapper mapper;

    private final GenreService genreService;

    @Override
    public void add(MovieRequest movieRequest) {
        var genre = genreService.getById(movieRequest.getGenreId());
        var movie = mapper.dtoToEntity(movieRequest);
        movie.getGenre().add(genre);
        repository.save(movie);
    }

    @Override
    public List<Movie> getAll() {
        return repository.findAll();
    }

    @Override
    public Movie update(Long id, MovieRequest movieRequest) {
        var movie = repository.findById(id).orElseThrow(MovieNotFoundException::new);
        mapper.updateMovie(movieRequest, movie);
        return repository.save(movie);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteGenre(Long movieId, Long genreId) {
        var movie = repository.findById(movieId).orElseThrow(MovieNotFoundException::new);
        var genres = movie.getGenre();
        genres.removeIf(genre -> Objects.equals(genre.getId(), genreId));
        repository.save(movie);
    }

    @Override
    public void addGenre(Long movieId, Long genreId) {
        var movie = repository.findById(movieId).orElseThrow(MovieNotFoundException::new);
        var genre = genreService.getById(genreId);
        movie.getGenre().add(genre);
        repository.save(movie);
    }
}
