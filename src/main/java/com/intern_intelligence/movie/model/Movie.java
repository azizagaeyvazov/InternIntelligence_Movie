package com.intern_intelligence.movie.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    private Double IMDbRating;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "movie_genre", // Name of the join table
            joinColumns = @JoinColumn(name = "movie_id"), // Foreign key in join table for Student
            inverseJoinColumns = @JoinColumn(name = "genre_id") // Foreign key in join table for Course
    )
    @JsonManagedReference
    private List<Genre> genre = new ArrayList<>();
}
