package com.grupoingenios.sgpc.sgpc_api_final.mapper.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.InventoryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.InventoryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Inventory;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre la entidad `Inventory` y sus respectivos DTOs (`InventoryRequestDTO` y `InventoryResponseDTO`).
 * Utiliza MapStruct para realizar la conversión de datos entre las entidades y sus representaciones DTO.
 */
@Mapper(componentModel = "spring", imports = {Supplier.class, Collectors.class})
public interface InventoryMapper {

    /**
     * Mapea un objeto `InventoryRequestDTO` a una entidad `Inventory`.
     *
     * @param inventoryRequestDTO El objeto DTO `InventoryRequestDTO` que se desea mapear.
     * @return La entidad `Inventory` con los datos del DTO.
     */
    Inventory toEntity(InventoryRequestDTO inventoryRequestDTO);

    /**
     * Mapea una entidad `Inventory` a un objeto DTO `InventoryResponseDTO`.
     *
     * @param inventory La entidad `Inventory` que se desea mapear.
     * @return Un objeto `InventoryResponseDTO` con los datos de la entidad `Inventory`.
     */
    @Mapping(target = "supplierNames", expression = "java(inventory.getSuppliers().stream().map(Supplier::getName).collect(Collectors.toList()))")
    @Mapping(target = "supplierId", expression = "java(inventory.getSuppliers().isEmpty() ? null : inventory.getSuppliers().iterator().next().getId_supplier())")
    InventoryResponseDTO toResponseDTO(Inventory inventory);

    /**
     * Actualiza una entidad `Inventory` a partir de un objeto `InventoryRequestDTO`.
     * Este método se usa para actualizar los atributos de una entidad existente.
     *
     * @param dto El objeto DTO `InventoryRequestDTO` con los datos a actualizar.
     * @param entity La entidad `Inventory` que se va a actualizar.
     */
    @Mapping(target = "id_inventory", ignore = true)
    void updatedInventoryFromDTO(InventoryRequestDTO dto, @MappingTarget Inventory entity);

}
