package com.intern_intelligence.movie.service;

import com.intern_intelligence.movie.dto.GenreRequest;
import com.intern_intelligence.movie.dto.GenreResponse;
import com.intern_intelligence.movie.exception.GenreNotFoundException;
import com.intern_intelligence.movie.mapper.GenreMapper;
import com.intern_intelligence.movie.model.Genre;
import com.intern_intelligence.movie.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    private final GenreMapper mapper;

    @Override
    public void add(GenreRequest genreRequest) {
        repository.save(mapper.dtoToEntity(genreRequest));
    }

    @Override
    public List<GenreResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Genre getById(Long id) {
        return repository.findById(id).orElseThrow(GenreNotFoundException::new);

    }

    @Override
    public void update(Long id, GenreRequest genreRequest) {
        var genre = repository.findById(id).orElseThrow(GenreNotFoundException::new);
        repository.save(mapper.updateGenre(genreRequest, genre));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
