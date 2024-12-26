package com.intern_intelligence.movie.repository;

import com.intern_intelligence.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
