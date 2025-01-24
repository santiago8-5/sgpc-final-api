package com.grupoingenios.sgpc.sgpc_api_final.dto.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.InputType;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.UnitType;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.WineryName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO que representa la solicitud para crear o actualizar un registro de inventario.
 * Incluye detalles como el nombre, cantidad, tipo de unidad, tipo de insumo, bodega y proveedor.
 */
@Data
public class InventoryRequestDTO {

    /**
     * Nombre del artículo en el inventario.
     * Obligatorio y no debe exceder los 100 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe tener más de 100 caracteres.")
    private String name;

    /**
     * Cantidad disponible del artículo en el inventario.
     * Obligatoria.
     */
    @NotNull(message = "La cantidad es obligatoria")
    private int amount;

    /**
     * Descripción adicional del artículo en el inventario.
     * Este campo es opcional y no debe exceder los 255 caracteres.
     */
    @Size(max = 255, message = "La descripción no debe tener más de 255 caracteres.")
    private String description;

    /**
     * Precio del artículo en el inventario.
     * Este campo es opcional.
     */
    private Float price;

    /**
     * Tipo de unidad asociada al artículo (por ejemplo, kilogramos, litros).
     * Obligatorio.
     */
    @NotNull(message = "El tipo de unidad es obligatoria")
    private UnitType unit;

    /**
     * Tipo de insumo del artículo (por ejemplo, materia prima, herramienta).
     * Obligatorio.
     */
    @NotNull(message = "El tipo de insumo es obligatorio")
    private InputType inputType;

    /**
     * Bodega donde se almacena el artículo.
     * Obligatoria.
     */
    @NotNull(message = "La bodega es obligatoria")
    private WineryName wineryName;

    /**
     * Identificador del proveedor asociado al artículo.
     * Este campo es opcional.
     */
    private Long supplierId;

}