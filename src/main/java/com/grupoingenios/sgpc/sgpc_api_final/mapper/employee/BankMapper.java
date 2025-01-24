package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.BankRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.BankResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Bank` y los DTOs `BankRequestDTO` y `BankResponseDTO`.
 * Utiliza MapStruct para la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface BankMapper {


    /**
     * Mapea una entidad `Bank` a un objeto DTO `BankResponseDTO`.
     *
     * @param bank La entidad `Bank` que se desea mapear.
     * @return Un objeto `BankResponseDTO` con los datos de la entidad `Bank`.
     */
    BankResponseDTO toResponseDto(Bank bank);

    /**
     * Mapea un objeto DTO `BankRequestDTO` a una entidad `Bank`.
     *
     * @param bankDTO El objeto DTO `BankRequestDTO` que se desea mapear.
     * @return La entidad `Bank` con los datos del DTO.
     */
    Bank toEntity(BankRequestDTO bankDTO);

    /**
     * Actualiza una entidad `Bank` existente con los valores de un objeto DTO `BankRequestDTO`.
     * El campo `idBank` es ignorado durante la actualización.
     *
     * @param dto El objeto DTO `BankRequestDTO` con los nuevos datos.
     * @param entity La entidad `Bank` que se desea actualizar.
     */
    @Mapping(target = "idBank", ignore = true)
    void updatedBankFromDTO(BankRequestDTO dto, @MappingTarget Bank entity);

}
