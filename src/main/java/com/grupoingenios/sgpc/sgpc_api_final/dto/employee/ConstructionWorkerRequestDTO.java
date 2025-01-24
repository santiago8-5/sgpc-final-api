package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;


/**
 * DTO que representa la solicitud para crear o actualizar un empleado de obra.
 * Extiende de {@link EmployeeRequestDTO} e incluye atributos adicionales específicos para trabajadores de obra.
 */
@Data
public class ConstructionWorkerRequestDTO extends EmployeeRequestDTO {

    /**
     * Fecha de inicio del contrato o asignación.
     * Debe ser la fecha actual o una fecha futura.
     */
    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro")
    private LocalDate startDate;

    /**
     * Fecha de finalización del contrato o asignación.
     * Debe ser una fecha en el futuro.
     */
    @Future(message = "La fecha de fin debe estar en el pasado")
    private LocalDate endDate;

}
