package com.grupoingenios.sgpc.sgpc_api_final.mapper.user;

import com.grupoingenios.sgpc.sgpc_api_final.dto.user.UserRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.user.UserResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
/**
 * Mapper para convertir entre la entidad `User` y sus respectivos DTOs (`UserRequestDTO` y `UserResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Mapea un objeto `UserRequestDTO` a una entidad `User`.
     *
     * @param userRequestDTO El objeto DTO `UserRequestDTO` que se desea mapear.
     * @return La entidad `User` con los datos del DTO.
     */
    User toEntity(UserRequestDTO userRequestDTO);

    /**
     * Mapea una entidad `User` a un objeto DTO `UserResponseDTO`.
     *
     * @param user La entidad `User` que se desea mapear.
     * @return Un objeto `UserResponseDTO` con los datos de la entidad `User`.
     */
    @Mapping(target = "rolName", source = "rol.name")
    UserResponseDTO toResponseDto(User user);

    /**
     * Actualiza una entidad `User` a partir de un objeto `UserRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `UserRequestDTO` con los datos a actualizar.
     * @param entity La entidad `User` que se va a actualizar.
     */
    @Mapping(target = "id_user", ignore = true)
    void updateUserFromDTO(UserRequestDTO dto, @MappingTarget User entity);

}
