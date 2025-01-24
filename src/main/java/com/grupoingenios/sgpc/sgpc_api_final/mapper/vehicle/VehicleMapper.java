package com.grupoingenios.sgpc.sgpc_api_final.mapper.vehicle;

import com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle.VehicleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle.VehicleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.vehicle.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Vehicle` y sus respectivos DTOs (`VehicleRequestDTO` y `VehicleResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface VehicleMapper {

    /**
     * Mapea un objeto `VehicleRequestDTO` a una entidad `Vehicle`.
     *
     * @param vehicleRequestDTO El objeto DTO `VehicleRequestDTO` que se desea mapear.
     * @return La entidad `Vehicle` con los datos del DTO.
     */
    Vehicle toEntity(VehicleRequestDTO vehicleRequestDTO);

    /**
     * Mapea una entidad `Vehicle` a un objeto DTO `VehicleResponseDTO`.
     *
     * @param vehicle La entidad `Vehicle` que se desea mapear.
     * @return Un objeto `VehicleResponseDTO` con los datos de la entidad `Vehicle`.
     */
    VehicleResponseDTO toResponseDto(Vehicle vehicle);

    /**
     * Actualiza una entidad `Vehicle` a partir de un objeto `VehicleRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `VehicleRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Vehicle` que se va a actualizar.
     */
    @Mapping(target = "id_vehicle", ignore = true)
    void updateVehicleFromDto(VehicleRequestDTO dto, @MappingTarget Vehicle entity);

}

