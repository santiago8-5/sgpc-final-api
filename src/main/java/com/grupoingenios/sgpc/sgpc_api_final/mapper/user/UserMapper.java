package com.grupoingenios.sgpc.sgpc_api_final.mapper.user;

import com.grupoingenios.sgpc.sgpc_api_final.dto.user.UserRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.user.UserResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO userRequestDTO);

    @Mapping(target = "rolName", source = "rol.name")
    UserResponseDTO toResponseDto(User user);

    @Mapping(target = "id_user", ignore = true)
    void updateUserFromDTO(UserRequestDTO dto, @MappingTarget User entity);

}
