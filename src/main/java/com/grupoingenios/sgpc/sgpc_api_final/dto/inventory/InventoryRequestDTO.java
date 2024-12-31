package com.grupoingenios.sgpc.sgpc_api_final.dto.inventory;

import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.InputType;
import com.grupoingenios.sgpc.sgpc_api_final.entity.inventory.WineryName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InventoryRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe tener más de 100 caracteres.")
    private String name;

    @NotNull(message = "La cantidad es obligatoria")
    private int amount;

    @Size(max = 255, message = "La descripción no debe tener más de 255 caracteres.")
    private String description;

    private Float price;

    @NotNull(message = "El tipo de insumo es obligatorio")
    private InputType inputType;

    @NotNull(message = "La bodega es obligatoria")
    private WineryName wineryName;

    private Long supplierId;

}