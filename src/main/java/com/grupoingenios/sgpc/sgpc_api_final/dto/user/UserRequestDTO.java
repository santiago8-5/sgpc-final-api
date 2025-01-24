package com.grupoingenios.sgpc.sgpc_api_final.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO que representa la solicitud para crear o actualizar un usuario.
 * Incluye validaciones para garantizar la consistencia y seguridad de los datos proporcionados.
 */
@Data
public class UserRequestDTO {

    /**
     * Nombre de usuario.
     * Obligatorio y con un máximo de 50 caracteres.
     */
    @NotBlank(message = "El username es obligatorio")
    @Size(max = 50, message = "El username no puede exceder los 50 caracteres")
    private String username;

    /**
     * Contraseña del usuario.
     * Obligatoria y con un máximo de 255 caracteres.
     */
    @NotBlank(message = "El password es obligatorio")
    @Size(max = 255, message = "El password no puede excede los 255 caracteres")
    private String password;

    /**
     * Identificador del rol asociado al usuario.
     */
    private Long rolId;

}
