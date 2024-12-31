package com.grupoingenios.sgpc.sgpc_api_final.mapper.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.InventoryRequestDTO;
import com.grupoingenios.sgpc.sgpc_api_final.dto.inventory.InventoryResponseDTO;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Inventory;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {Supplier.class, Collectors.class})
public interface InventoryMapper {

    // METODOS PARA TRANSFORMACIONES
    Inventory toEntity(InventoryRequestDTO inventoryRequestDTO);

    // METODO PARA RESPUESTA
    @Mapping(target = "supplierNames", expression = "java(inventory.getSuppliers().stream().map(Supplier::getName).collect(Collectors.toList()))")
    InventoryResponseDTO toResponseDTO(Inventory inventory);

    // METODO PARA ACTUALIZAR
    @Mapping(target = "id_inventory", ignore = true)
    void updatedInventoryFromDTO(InventoryRequestDTO dto, @MappingTarget Inventory entity);

}
