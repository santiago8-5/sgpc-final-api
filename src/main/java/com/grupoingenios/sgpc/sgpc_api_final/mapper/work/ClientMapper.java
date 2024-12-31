package com.grupoingenios.sgpc.sgpc_api_final.mapper.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {


    /**
     * Convierte un ClientDTO a una entidad Client.
     * @param clientRequestDTO el ClientDTO a convertir
     * @return la entidad Client correspondiente
     */
    Client toEntity(ClientRequestDTO clientRequestDTO);

    ClientResponseDTO toResponseDTO(Client client);

    @Mapping(target = "idClient", ignore = true)
    void updatedClientFromDTO(ClientRequestDTO dto, @MappingTarget Client entity);

}
