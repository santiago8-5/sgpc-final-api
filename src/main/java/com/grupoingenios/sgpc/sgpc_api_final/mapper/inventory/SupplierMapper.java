package com.grupoingenios.sgpc.sgpc_api_final.mapper.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.SupplierRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.SupplierResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    Supplier toEntity(SupplierRequestDTO supplierDTO);

    SupplierResponseDTO toResponseDto(Supplier supplier);

    @Mapping(target = "id_supplier", ignore = true)
    void updatedSupplierFromDTO(SupplierRequestDTO dto, @MappingTarget Supplier entity);

}
