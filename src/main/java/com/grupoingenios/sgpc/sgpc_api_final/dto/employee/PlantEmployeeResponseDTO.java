package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
public class PlantEmployeeResponseDTO extends EmployeeResponseDTO {
    private Float workingHours;

    private BigDecimal salary;

    private String departmentName;

}
