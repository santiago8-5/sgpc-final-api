package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * DTO que representa la respuesta con información de un empleado de planta.
 * Extiende de {@link EmployeeResponseDTO} e incluye atributos específicos para empleados de planta.
 */
@Data
public class PlantEmployeeResponseDTO extends EmployeeResponseDTO {
    /**
     * Número de horas de trabajo del empleado de planta.
     */
    private Float workingHours;

    /**
     * Salario del empleado de planta.
     */
    private BigDecimal salary;

    /**
     * Nombre del departamento al que pertenece el empleado de planta.
     */
    private String departmentName;


    private Long departmentId;

}
