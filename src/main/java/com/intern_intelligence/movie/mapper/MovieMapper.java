package com.intern_intelligence.movie.mapper;

import com.intern_intelligence.movie.dto.MovieRequest;
import com.intern_intelligence.movie.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie dtoToEntity(MovieRequest movieRequest);

    void updateMovie(MovieRequest movieRequest, @MappingTarget Movie movie);
}
