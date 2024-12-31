package com.grupoingenios.sgpc.sgpc_api_final.mapper.employee;

import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.BankRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.employee.BankResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BankMapper {

    BankResponseDTO toResponseDto(Bank bank);

    Bank toEntity(BankRequestDTO bankDTO);

    // METODO PARA ACTUALIZAR
    @Mapping(target = "idBank", ignore = true)
    void updatedBankFromDTO(BankRequestDTO dto, @MappingTarget Bank entity);

}
