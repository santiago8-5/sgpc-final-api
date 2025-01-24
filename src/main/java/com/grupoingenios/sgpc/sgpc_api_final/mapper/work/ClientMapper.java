package com.grupoingenios.sgpc.sgpc_api_final.mapper.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Client` y sus respectivos DTOs (`ClientRequestDTO` y `ClientResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {

    /**
     * Convierte un objeto `ClientRequestDTO` a una entidad `Client`.
     *
     * @param clientRequestDTO El objeto DTO `ClientRequestDTO` que se desea mapear.
     * @return La entidad `Client` correspondiente con los datos del DTO.
     */
    Client toEntity(ClientRequestDTO clientRequestDTO);

    /**
     * Convierte una entidad `Client` a un objeto `ClientResponseDTO`.
     *
     * @param client La entidad `Client` que se desea mapear.
     * @return Un objeto `ClientResponseDTO` con los datos de la entidad `Client`.
     */
    ClientResponseDTO toResponseDTO(Client client);

    /**
     * Actualiza una entidad `Client` a partir de un objeto `ClientRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `ClientRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Client` que se va a actualizar.
     */
    @Mapping(target = "idClient", ignore = true)
    void updatedClientFromDTO(ClientRequestDTO dto, @MappingTarget Client entity);

}
