package com.intern_intelligence.movie.mapper;

import com.intern_intelligence.movie.dto.MovieRequest;
import com.intern_intelligence.movie.dto.MovieResponse;
import com.intern_intelligence.movie.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "movieRequest.genreId", ignore = true)
    Movie dtoToEntity(MovieRequest movieRequest);

    @Mapping(target = "genre", ignore = true)
    MovieResponse entityToDto(Movie movie);

    void updateMovie(MovieRequest movieRequest, @MappingTarget Movie movie);
}
