package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Data;
import lombok.EqualsAndHashCode;


import java.time.LocalDate;

@Data
public class ConstructionWorkerResponseDTO extends EmployeeResponseDTO {
    private LocalDate startDate;

    private LocalDate endDate;
}
