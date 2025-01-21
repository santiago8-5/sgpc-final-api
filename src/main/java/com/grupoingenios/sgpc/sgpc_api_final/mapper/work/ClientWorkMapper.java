package com.grupoingenios.sgpc.sgpc_api_final.mapper.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.ClientWork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientWorkMapper {

    ClientWork toEntity(ClientWorkRequestDTO clientWorkRequestDTO);

    @Mapping(target = "nameClient", source = "client.name")
    @Mapping(target = "nameWork", source = "work.name")
    @Mapping(target = "clientId", source = "client.idClient")
    ClientWorkResponseDTO toResponseDTO(ClientWork clientWork);

    @Mapping(target = "id", ignore = true)
    void updatedClientWorkFromDTO(ClientWorkRequestDTO dto, @MappingTarget ClientWork entity);
}
