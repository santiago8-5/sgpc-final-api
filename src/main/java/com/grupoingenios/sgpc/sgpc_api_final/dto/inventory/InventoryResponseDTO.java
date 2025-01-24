package com.grupoingenios.sgpc.sgpc_api_final.dto.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.InputType;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.UnitType;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.WineryName;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO que representa la respuesta con información detallada de un registro de inventario.
 * Incluye detalles como identificador, nombre, cantidad, tipo de unidad, insumo, bodega y proveedores asociados.
 */
@Data
public class InventoryResponseDTO {

    private Long id_inventory;

    private String name;

    private int amount;

    private String description;

    private Float price;

    private UnitType unit;

    private InputType inputType;

    private WineryName wineryName;

    private List<String> supplierNames;

    private Long supplierId ;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}