package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
public class PlantEmployeeRequestDTO extends EmployeeRequestDTO {
    @NotNull(message = "Las horas de trabajo deben de ser obligatorias")
    private Float workingHours;
    @NotNull(message = "El salario es obligatorio")
    private BigDecimal salary;
    @NotNull(message = "El departamento es obligatoria.")
    private Long departmentId;

}
