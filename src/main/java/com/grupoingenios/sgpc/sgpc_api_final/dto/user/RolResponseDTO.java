package com.grupoingenios.sgpc.sgpc_api_final.dto.user;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información detallada de un rol de usuario.
 * Incluye atributos como identificador, nombre y marcas de tiempo.
 */
@Data
public class RolResponseDTO {

    private Long id_rol;

    private String name;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
