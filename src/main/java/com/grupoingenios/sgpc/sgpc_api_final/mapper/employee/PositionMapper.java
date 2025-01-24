package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.PositionRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.PositionResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Position` y sus respectivos DTOs (`PositionRequestDTO` y `PositionResponseDTO`).
 * Utiliza MapStruct para la conversión de datos entre las entidades y las representaciones DTO.
 */
@Mapper(componentModel="spring")

public interface PositionMapper {

    /**
     * Mapea un objeto `PositionRequestDTO` a una entidad `Position`.
     *
     * @param positionDTO El objeto DTO `PositionRequestDTO` que se desea mapear.
     * @return La entidad `Position` con los datos del DTO.
     */
    Position toEntity(PositionRequestDTO positionDTO);

    /**
     * Mapea una entidad `Position` a un objeto DTO `PositionResponseDTO`.
     *
     * @param position La entidad `Position` que se desea mapear.
     * @return Un objeto `PositionResponseDTO` con los datos de la entidad `Position`.
     */
    PositionResponseDTO toResponseDTO(Position position);

    /**
     * Actualiza una entidad `Position` a partir de un objeto `PositionRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `PositionRequestDTO` con los datos a actualizar.
     * @param position La entidad `Position` que se va a actualizar.
     */
    @Mapping(target = "idPosition", ignore = true)
    void updatedPositionFromDTO(PositionRequestDTO dto, @MappingTarget Position position);

}