package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO utilizado para crear o actualizar un tipo de obra.
 * Incluye validaciones para asegurar que los datos proporcionados sean válidos y completos.
 */
@Data
public class WorkTypeRequestDTO {

    /**
     * Nombre del tipo de obra.
     * Obligatorio y no debe exceder los 80 caracteres.
     */
    @NotNull(message = "El nombre es ogligatorio")
    @Size(max = 80, message = "El nombre no puede exceder los 80 caracteres")
    private String name;

    /**
     * Descripción del tipo de obra.
     * Opcional y no debe exceder los 255 caracteres.
     */
    @Size(max = 80, message = "La description no puede exceder los 255 caracteres")
    private String description;

}
