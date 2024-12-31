package com.grupoingenios.sgpc.sgpc_api_final.mapper.machinery;

import com.grupoingenios.sgpc.sgpc_api_final.dto.machinery.MachineryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.machinery.MachineryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.machinery.Machinery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MachineryMapper {

    Machinery toEntity(MachineryRequestDTO machineryRequestDTO);

    MachineryResponseDTO toResponseDto(Machinery machinery);

    @Mapping(target = "id_machinery", ignore = true)
    void updatedMachineryFromDTO(MachineryRequestDTO dto, @MappingTarget Machinery entity);

}
