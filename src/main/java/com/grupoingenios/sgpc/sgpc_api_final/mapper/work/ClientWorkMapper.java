package com.grupoingenios.sgpc.sgpc_api_final.mapper.work;

import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.work.ClientWorkResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.ClientWork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `ClientWork` y sus respectivos DTOs (`ClientWorkRequestDTO` y `ClientWorkResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface ClientWorkMapper {

    /**
     * Convierte un objeto `ClientWorkRequestDTO` a una entidad `ClientWork`.
     *
     * @param clientWorkRequestDTO El objeto DTO `ClientWorkRequestDTO` que se desea mapear.
     * @return La entidad `ClientWork` correspondiente con los datos del DTO.
     */
    ClientWork toEntity(ClientWorkRequestDTO clientWorkRequestDTO);

    /**
     * Convierte una entidad `ClientWork` a un objeto `ClientWorkResponseDTO`.
     *
     * @param clientWork La entidad `ClientWork` que se desea mapear.
     * @return Un objeto `ClientWorkResponseDTO` con los datos de la entidad `ClientWork`.
     */
    @Mapping(target = "nameClient", source = "client.name")
    @Mapping(target = "nameWork", source = "work.name")
    @Mapping(target = "clientId", source = "client.idClient")
    ClientWorkResponseDTO toResponseDTO(ClientWork clientWork);

    /**
     * Actualiza una entidad `ClientWork` a partir de un objeto `ClientWorkRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `ClientWorkRequestDTO` con los datos a actualizar.
     * @param entity La entidad `ClientWork` que se va a actualizar.
     */
    @Mapping(target = "id", ignore = true)
    void updatedClientWorkFromDTO(ClientWorkRequestDTO dto, @MappingTarget ClientWork entity);
}
