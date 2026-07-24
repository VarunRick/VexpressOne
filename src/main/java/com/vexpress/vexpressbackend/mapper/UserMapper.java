package com.vexpress.vexpressbackend.mapper;

import com.vexpress.vexpressbackend.dto.UserRequestDTO;
import com.vexpress.vexpressbackend.dto.UserResponseDTO;
import com.vexpress.vexpressbackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponseDTO(User user);

    void updateUserFromDTO(UserRequestDTO dto, @MappingTarget User user);

}
