package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.StatusType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información de un puesto de trabajo.
 * Contiene detalles como el identificador, nombre, descripción, estado y marcas de tiempo.
 */
@Setter
@Getter
public class PositionResponseDTO {

    private Long idPosition;

    private String name;

    private String description;

    private StatusType statusType;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;
}
