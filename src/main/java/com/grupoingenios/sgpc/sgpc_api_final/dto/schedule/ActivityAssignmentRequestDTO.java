package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.StatusActivityAssignment;
import lombok.Data;

/**
 * DTO que representa la solicitud para asignar actividades a empleados en un cronograma.
 * Incluye detalles sobre el rol asignado, si el empleado es responsable, estado de la asignación y el empleado asociado.
 */
@Data
public class ActivityAssignmentRequestDTO {

    /**
     * Rol del empleado en la actividad asignada.
     */
    private String role;

    /**
     * Indica si el empleado es responsable de la actividad.
     */
    private boolean responsible;

    /**
     * Estado de la asignación de la actividad (por ejemplo, PENDIENTE, COMPLETADA).
     */
    private StatusActivityAssignment status;

    /**
     * Identificador único del empleado al que se asigna la actividad.
     */
    private Long employeeId;

   // private Long scheduledActivityId;

}
