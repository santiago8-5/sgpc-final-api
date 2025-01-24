package com.grupoingenios.sgpc.sgpc_api_final.mapper.user;

import com.grupoingenios.sgpc.sgpc_api_final.dto.user.RolRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.user.RolResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.user.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


/**
 * Mapper para convertir entre la entidad `Rol` y sus respectivos DTOs (`RolRequestDTO` y `RolResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface RolMapper {

    /**
     * Mapea un objeto `RolRequestDTO` a una entidad `Rol`.
     *
     * @param rolRequestDTO El objeto DTO `RolRequestDTO` que se desea mapear.
     * @return La entidad `Rol` con los datos del DTO.
     */
    Rol toEntity(RolRequestDTO rolRequestDTO);

    /**
     * Mapea una entidad `Rol` a un objeto DTO `RolResponseDTO`.
     *
     * @param rol La entidad `Rol` que se desea mapear.
     * @return Un objeto `RolResponseDTO` con los datos de la entidad `Rol`.
     */
    RolResponseDTO toResponseDto(Rol rol);

    /**
     * Actualiza una entidad `Rol` a partir de un objeto `RolRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `RolRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Rol` que se va a actualizar.
     */
    @Mapping(target = "id_rol", ignore = true)
    void updatedRolFromDTO(RolRequestDTO dto, @MappingTarget Rol entity);

}
