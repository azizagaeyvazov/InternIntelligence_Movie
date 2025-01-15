package com.intern_intelligence.movie.mapper;

import com.intern_intelligence.movie.dto.UserRegisterRequest;
import com.intern_intelligence.movie.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User dtoToEntity(UserRegisterRequest userRequest);

    @Mapping(target = "password", ignore = true)
    void updateUserEntity(UserRegisterRequest request, @MappingTarget User user);

}
