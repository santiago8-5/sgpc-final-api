package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * DTO que representa la respuesta con información de un trabajador de obra.
 * Extiende de {@link EmployeeResponseDTO} e incluye atributos adicionales específicos para trabajadores de obra.
 */
@Data
public class ConstructionWorkerResponseDTO extends EmployeeResponseDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
