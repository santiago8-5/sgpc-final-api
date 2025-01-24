package com.grupoingenios.sgpc.sgpc_api_final.dto.user;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información detallada de un usuario.
 * Incluye atributos como identificador, nombre de usuario, contraseña, rol asociado y marcas de tiempo.
 */
@Data
public class UserResponseDTO {

    private Long id_user;

    private String username;

    private String password;

    private String rolName;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
