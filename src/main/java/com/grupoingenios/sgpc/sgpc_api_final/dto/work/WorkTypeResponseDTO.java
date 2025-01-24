package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO utilizado para devolver la respuesta con la información detallada de un tipo de obra.
 * Incluye atributos clave como el nombre, la descripción y las fechas de creación y última modificación.
 */
@Data
public class WorkTypeResponseDTO {

    private Long idWorkType;

    private String name;

    private String description;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
