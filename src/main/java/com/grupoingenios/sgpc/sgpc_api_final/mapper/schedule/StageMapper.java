package com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.StageRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.StageResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Stage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Stage` y sus respectivos DTOs (`StageRequestDTO` y `StageResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface StageMapper {

    /**
     * Mapea un objeto `StageRequestDTO` a una entidad `Stage`.
     *
     * @param activityRequestDTO El objeto DTO `StageRequestDTO` que se desea mapear.
     * @return La entidad `Stage` con los datos del DTO.
     */
    Stage toEntity(StageRequestDTO activityRequestDTO);

    /**
     * Mapea una entidad `Stage` a un objeto DTO `StageResponseDTO`.
     *
     * @param stage La entidad `Stage` que se desea mapear.
     * @return Un objeto `StageResponseDTO` con los datos de la entidad `Stage`.
     */
    StageResponseDTO toResponseDto(Stage stage);

    /**
     * Actualiza una entidad `Stage` a partir de un objeto `StageRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `StageRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Stage` que se va a actualizar.
     */
    @Mapping(target = "idStage", ignore = true)
    void updateStageFromDTO(StageRequestDTO dto, @MappingTarget Stage entity);

}
