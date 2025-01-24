package com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Activity` y sus respectivos DTOs (`ActivityRequestDTO` y `ActivityResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface ActivityMapper {

    /**
     * Mapea un objeto `ActivityRequestDTO` a una entidad `Activity`.
     *
     * @param activityRequestDTO El objeto DTO `ActivityRequestDTO` que se desea mapear.
     * @return La entidad `Activity` con los datos del DTO.
     */
    Activity toEntity(ActivityRequestDTO activityRequestDTO);

    /**
     * Mapea una entidad `Activity` a un objeto DTO `ActivityResponseDTO`.
     *
     * @param activity La entidad `Activity` que se desea mapear.
     * @return Un objeto `ActivityResponseDTO` con los datos de la entidad `Activity`.
     */
    @Mapping(target = "stageName", source = "stage.name")
    @Mapping(target = "idStage", source = "stage.idStage")
    ActivityResponseDTO toResponseDto(Activity activity);

    /**
     * Actualiza una entidad `Activity` a partir de un objeto `ActivityRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `ActivityRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Activity` que se va a actualizar.
     */
    @Mapping(target = "idActivity", ignore = true)
    void updateActivityFromDTO(ActivityRequestDTO dto, @MappingTarget Activity entity);

}
