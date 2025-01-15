package com.intern_intelligence.movie.mapper;

import com.intern_intelligence.movie.dto.GenreRequest;
import com.intern_intelligence.movie.dto.GenreResponse;
import com.intern_intelligence.movie.model.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target = "name", source = "genre")
    Genre dtoToEntity(GenreRequest genreRequest);

    @Mapping(target = "genre", source = "name")
    GenreResponse entityToDto(Genre genre);

    @Mapping(target = "name", source = "genre")
    Genre updateGenre(GenreRequest genreRequest, @MappingTarget Genre genre);
}
