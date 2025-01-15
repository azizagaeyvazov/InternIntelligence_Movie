package com.intern_intelligence.movie.controller;

import com.intern_intelligence.movie.dto.MovieRequest;
import com.intern_intelligence.movie.model.Movie;
import com.intern_intelligence.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping
    public List<Movie> getAllMovies() {
        return service.getAll();
    }

    @PostMapping
    public void addMovie(@RequestBody MovieRequest movieRequest) {
        service.add(movieRequest);
    }

    @PutMapping
    public void updateMovie(@RequestParam Long id, @RequestBody MovieRequest movieRequest) {
        service.update(id, movieRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{movieId}/genres")
    public ResponseEntity<Void> addGenreToMovie(@PathVariable Long movieId, @RequestParam Long genreId) {
        service.addGenre(movieId, genreId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}/genres")
    public ResponseEntity<Void> removeGenreFromMovie(@PathVariable Long movieId, @RequestParam Long genreId) {
        service.deleteGenre(movieId, genreId);
        return ResponseEntity.ok().build();
    }
}
