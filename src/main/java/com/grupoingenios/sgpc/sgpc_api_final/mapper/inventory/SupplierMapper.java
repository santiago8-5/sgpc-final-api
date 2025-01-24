package com.grupoingenios.sgpc.sgpc_api_final.mapper.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.SupplierRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.SupplierResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para convertir entre la entidad `Supplier` y sus respectivos DTOs (`SupplierRequestDTO` y `SupplierResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring")
public interface SupplierMapper {

    /**
     * Mapea un objeto `SupplierRequestDTO` a una entidad `Supplier`.
     *
     * @param supplierDTO El objeto DTO `SupplierRequestDTO` que se desea mapear.
     * @return La entidad `Supplier` con los datos del DTO.
     */
    Supplier toEntity(SupplierRequestDTO supplierDTO);

    /**
     * Mapea una entidad `Supplier` a un objeto DTO `SupplierResponseDTO`.
     *
     * @param supplier La entidad `Supplier` que se desea mapear.
     * @return Un objeto `SupplierResponseDTO` con los datos de la entidad `Supplier`.
     */
    SupplierResponseDTO toResponseDto(Supplier supplier);

    /**
     * Actualiza una entidad `Supplier` a partir de un objeto `SupplierRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `SupplierRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Supplier` que se va a actualizar.
     */
    @Mapping(target = "id_supplier", ignore = true)
    void updatedSupplierFromDTO(SupplierRequestDTO dto, @MappingTarget Supplier entity);

}
