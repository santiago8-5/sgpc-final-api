package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * DTO que representa la solicitud para crear o actualizar un empleado de planta.
 * Extiende de {@link EmployeeRequestDTO} e incluye atributos específicos para empleados de planta.
 */
@Data
public class PlantEmployeeRequestDTO extends EmployeeRequestDTO {

    /**
     * Número de horas de trabajo del empleado de planta.
     * Este campo es obligatorio.
     */
    @NotNull(message = "Las horas de trabajo deben de ser obligatorias")
    private Float workingHours;

    /**
     * Salario del empleado de planta.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El salario es obligatorio")
    private BigDecimal salary;

    /**
     * Identificador del departamento al que pertenece el empleado.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El departamento es obligatoria.")
    private Long departmentId;

}
