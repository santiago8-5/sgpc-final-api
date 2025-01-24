package com.grupoingenios.sgpc.sgpc_api_final.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO que representa la solicitud para crear o actualizar un rol de usuario.
 * Incluye validaciones para garantizar la consistencia de los datos proporcionados.
 */
@Data
public class RolRequestDTO {

    /**
     * Nombre del rol.
     * Obligatorio y con un límite de 50 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String name;
}
