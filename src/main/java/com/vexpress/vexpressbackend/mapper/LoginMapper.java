package com.vexpress.vexpressbackend.mapper;

import com.vexpress.vexpressbackend.dto.LoginResponseDTO;
import com.vexpress.vexpressbackend.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    LoginResponseDTO toLoginResponseDTO(User user);
}
