package com.grupoingenios.sgpc.sgpc_api_final.mapper.maintenance;


import com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance.MaintenanceRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance.MaintenanceResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.Maintenance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Maintenance` y sus respectivos DTOs (`MaintenanceRequestDTO` y `MaintenanceResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    /**
     * Mapea un objeto `MaintenanceRequestDTO` a una entidad `Maintenance`.
     *
     * @param maintenanceRequestDTO El objeto DTO `MaintenanceRequestDTO` que se desea mapear.
     * @return La entidad `Maintenance` con los datos del DTO.
     */
    Maintenance toEntity(MaintenanceRequestDTO maintenanceRequestDTO);

    /**
     * Mapea una entidad `Maintenance` a un objeto DTO `MaintenanceResponseDTO`.
     *
     * @param maintenance La entidad `Maintenance` que se desea mapear.
     * @return Un objeto `MaintenanceResponseDTO` con los datos de la entidad `Maintenance`.
     */
    @Mapping(source = "employee.name", target = "nameEmployee")
    @Mapping(target = "employeeId", source = "employee.idEmployee")
    MaintenanceResponseDTO toResponseDto(Maintenance maintenance);

    /**
     * Actualiza una entidad `Maintenance` a partir de un objeto `MaintenanceRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `MaintenanceRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Maintenance` que se va a actualizar.
     */
    @Mapping(target = "idMaintenance", ignore = true)
    void updatedRolFromDTO(MaintenanceRequestDTO dto, @MappingTarget Maintenance entity);

}
