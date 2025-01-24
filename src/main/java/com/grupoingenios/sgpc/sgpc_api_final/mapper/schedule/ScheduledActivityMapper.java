package com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduledActivityResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ScheduledActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `ScheduledActivity` y sus respectivos DTOs (`ScheduledActivityRequestDTO` y `ScheduledActivityResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface ScheduledActivityMapper {

    /**
     * Mapea un objeto `ScheduledActivityRequestDTO` a una entidad `ScheduledActivity`.
     *
     * @param scheduledActivityRequestDTO El objeto DTO `ScheduledActivityRequestDTO` que se desea mapear.
     * @return La entidad `ScheduledActivity` con los datos del DTO.
     */
    ScheduledActivity toEntity(ScheduledActivityRequestDTO scheduledActivityRequestDTO);

    /**
     * Mapea una entidad `ScheduledActivity` a un objeto DTO `ScheduledActivityResponseDTO`.
     *
     * @param scheduledActivity La entidad `ScheduledActivity` que se desea mapear.
     * @return Un objeto `ScheduledActivityResponseDTO` con los datos de la entidad `ScheduledActivity`.
     */
    @Mapping(target = "nameActivity", source = "activity.name")
    @Mapping(target = "activityId", source = "activity.idActivity")
    ScheduledActivityResponseDTO toResponseDto(ScheduledActivity scheduledActivity);

    /**
     * Actualiza una entidad `ScheduledActivity` a partir de un objeto `ScheduledActivityRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `ScheduledActivityRequestDTO` con los datos a actualizar.
     * @param entity La entidad `ScheduledActivity` que se va a actualizar.
     */
    @Mapping(target = "activity", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    void updateScheduledActivityFromDTO(ScheduledActivityRequestDTO dto, @MappingTarget ScheduledActivity entity);

}
