package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.StatusActivityAssignment;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información detallada sobre una asignación de actividad.
 * Incluye detalles como el rol asignado, estado, empleado asignado y marcas de tiempo.
 */
@Data
public class ActivityAssignmentResponseDTO {

    private Long activityAssignmentId;

    private String role;

    private boolean responsible;

    private StatusActivityAssignment status;

    private String nameEmployee;

    private Long idEmployee;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
