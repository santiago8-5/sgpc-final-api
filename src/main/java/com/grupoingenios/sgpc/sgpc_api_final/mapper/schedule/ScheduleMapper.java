package com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ScheduleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Schedule` y sus respectivos DTOs (`ScheduleRequestDTO` y `ScheduleResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    /**
     * Mapea un objeto `ScheduleRequestDTO` a una entidad `Schedule`.
     *
     * @param scheduleRequestDTO El objeto DTO `ScheduleRequestDTO` que se desea mapear.
     * @return La entidad `Schedule` con los datos del DTO.
     */
    Schedule toEntity(ScheduleRequestDTO scheduleRequestDTO);

    /**
     * Mapea una entidad `Schedule` a un objeto DTO `ScheduleResponseDTO`.
     *
     * @param schedule La entidad `Schedule` que se desea mapear.
     * @return Un objeto `ScheduleResponseDTO` con los datos de la entidad `Schedule`.
     */
    ScheduleResponseDTO toResponseDto(Schedule schedule);

    /**
     * Actualiza una entidad `Schedule` a partir de un objeto `ScheduleRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `ScheduleRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Schedule` que se va a actualizar.
     */
    void updateScheduleFromDTO(ScheduleRequestDTO dto, @MappingTarget Schedule entity);
}