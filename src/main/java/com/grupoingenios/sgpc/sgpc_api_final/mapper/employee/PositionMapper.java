package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.PositionRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.PositionResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")

public interface PositionMapper {

    Position toEntity(PositionRequestDTO positionDTO);

    PositionResponseDTO toResponseDTO(Position position);

    @Mapping(target = "idPosition", ignore = true)
    void updatedPositionFromDTO(PositionRequestDTO dto, @MappingTarget Position position);

}