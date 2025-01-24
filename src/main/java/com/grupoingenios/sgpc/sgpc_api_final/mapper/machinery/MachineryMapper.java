package com.grupoingenios.sgpc.sgpc_api_final.mapper.machinery;

import com.grupoingenios.sgpc.sgpc_api_final.dto.machinery.MachineryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.machinery.MachineryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.machinery.Machinery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Machinery` y sus respectivos DTOs (`MachineryRequestDTO` y `MachineryResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface MachineryMapper {

    /**
     * Mapea un objeto `MachineryRequestDTO` a una entidad `Machinery`.
     *
     * @param machineryRequestDTO El objeto DTO `MachineryRequestDTO` que se desea mapear.
     * @return La entidad `Machinery` con los datos del DTO.
     */
    Machinery toEntity(MachineryRequestDTO machineryRequestDTO);

    /**
     * Mapea una entidad `Machinery` a un objeto DTO `MachineryResponseDTO`.
     *
     * @param machinery La entidad `Machinery` que se desea mapear.
     * @return Un objeto `MachineryResponseDTO` con los datos de la entidad `Machinery`.
     */
    MachineryResponseDTO toResponseDto(Machinery machinery);

    /**
     * Actualiza una entidad `Machinery` a partir de un objeto `MachineryRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `MachineryRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Machinery` que se va a actualizar.
     */
    @Mapping(target = "id_machinery", ignore = true)
    void updatedMachineryFromDTO(MachineryRequestDTO dto, @MappingTarget Machinery entity);

}
