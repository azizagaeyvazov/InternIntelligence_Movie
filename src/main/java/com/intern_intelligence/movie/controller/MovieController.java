package com.intern_intelligence.movie.controller;

import com.intern_intelligence.movie.dto.MovieRequest;
import com.intern_intelligence.movie.model.Movie;
import com.intern_intelligence.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping
    public List<Movie> getAllMovies() {
        return service.getAll();
    }

    @PostMapping
    public void addMovie (@RequestBody MovieRequest movieRequest) {
        service.add(movieRequest);
    }

    @PutMapping
    public void updateMovie (@RequestParam Long id, @RequestBody MovieRequest movieRequest) {
        service.update(id, movieRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie (@PathVariable Long id) {
        service.delete(id);
    }
}
