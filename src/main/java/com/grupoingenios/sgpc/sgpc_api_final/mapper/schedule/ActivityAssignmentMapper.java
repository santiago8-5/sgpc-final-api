package com.grupoingenios.sgpc.sgpc_api_final.mapper.schedule;


import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.schedule.ActivityAssignmentResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.ActivityAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


/**
 * Mapper para convertir entre la entidad `ActivityAssignment` y sus respectivos DTOs (`ActivityAssignmentRequestDTO` y `ActivityAssignmentResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface ActivityAssignmentMapper {

    /**
     * Mapea un objeto `ActivityAssignmentRequestDTO` a una entidad `ActivityAssignment`.
     *
     * @param activityAssignmentRequestDTO El objeto DTO `ActivityAssignmentRequestDTO` que se desea mapear.
     * @return La entidad `ActivityAssignment` con los datos del DTO.
     */
    ActivityAssignment toEntity(ActivityAssignmentRequestDTO activityAssignmentRequestDTO);


    /**
     * Mapea una entidad `ActivityAssignment` a un objeto DTO `ActivityAssignmentResponseDTO`.
     *
     * @param activityAssignment La entidad `ActivityAssignment` que se desea mapear.
     * @return Un objeto `ActivityAssignmentResponseDTO` con los datos de la entidad `ActivityAssignment`.
     */
    @Mapping(source = "employee.name", target = "nameEmployee")
    @Mapping(source = "employee.idEmployee", target = "idEmployee")
    ActivityAssignmentResponseDTO toResponseDto(ActivityAssignment activityAssignment);

    /**
     * Actualiza una entidad `ActivityAssignment` a partir de un objeto `ActivityAssignmentRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `ActivityAssignmentRequestDTO` con los datos a actualizar.
     * @param entity La entidad `ActivityAssignment` que se va a actualizar.
     */
    void updateActivityAssignmentFromDTO(ActivityAssignmentRequestDTO dto, @MappingTarget ActivityAssignment entity);

}
