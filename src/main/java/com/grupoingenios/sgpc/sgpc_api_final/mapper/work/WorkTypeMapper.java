package com.grupoingenios.sgpc.sgpc_api_final.mapper.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkTypeRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.WorkTypeResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.WorkType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `WorkType` y sus respectivos DTOs (`WorkTypeRequestDTO` y `WorkTypeResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface WorkTypeMapper {

    /**
     * Convierte un objeto `WorkTypeRequestDTO` a una entidad `WorkType`.
     *
     * @param workTypeRequestDTO El objeto DTO `WorkTypeRequestDTO` que se desea mapear.
     * @return La entidad `WorkType` correspondiente con los datos del DTO.
     */
    WorkType toEntity(WorkTypeRequestDTO workTypeRequestDTO);


    /**
     * Convierte una entidad `WorkType` a un objeto `WorkTypeResponseDTO`.
     *
     * @param workType La entidad `WorkType` que se desea mapear.
     * @return Un objeto `WorkTypeResponseDTO` con los datos de la entidad `WorkType`.
     */
    WorkTypeResponseDTO toResponseDTO(WorkType workType);

    /**
     * Actualiza una entidad `WorkType` a partir de un objeto `WorkTypeRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `WorkTypeRequestDTO` con los datos a actualizar.
     * @param entity La entidad `WorkType` que se va a actualizar.
     */
    @Mapping(target = "idWorkType", ignore = true)
    void updatedWorkTypeFromDTO(WorkTypeRequestDTO dto, @MappingTarget WorkType entity);

}
