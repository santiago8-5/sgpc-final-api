package com.grupoingenios.sgpc.sgpc_api_final.mapper.maintenance;


import com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance.MaintenanceRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance.MaintenanceResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.Maintenance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    Maintenance toEntity(MaintenanceRequestDTO maintenanceRequestDTO);

    @Mapping(source = "employee.name", target = "nameEmployee")
    MaintenanceResponseDTO toResponseDto(Maintenance maintenance);

    @Mapping(target = "idMaintenance", ignore = true)
    void updatedRolFromDTO(MaintenanceRequestDTO dto, @MappingTarget Maintenance entity);

}
