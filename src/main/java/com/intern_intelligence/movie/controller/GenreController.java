package com.intern_intelligence.movie.controller;

import com.intern_intelligence.movie.dto.GenreRequest;
import com.intern_intelligence.movie.dto.GenreResponse;
import com.intern_intelligence.movie.model.Genre;
import com.intern_intelligence.movie.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService service;

    @PostMapping
    public ResponseEntity<Void> addGenre(@RequestBody GenreRequest request) {
        service.add(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping
    public ResponseEntity<Void> updateGenre(@RequestParam Long id, @RequestBody GenreRequest request) {
        service.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenreById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
