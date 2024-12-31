package com.grupoingenios.sgpc.sgpc_api_final.mapper.vehicle;

import com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle.VehicleRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.vehicle.VehicleResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.vehicle.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle toEntity(VehicleRequestDTO vehicleRequestDTO);

    VehicleResponseDTO toResponseDto(Vehicle vehicle);

    @Mapping(target = "id_vehicle", ignore = true)
    void updateVehicleFromDto(VehicleRequestDTO dto, @MappingTarget Vehicle entity);

}

