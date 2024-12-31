package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
public class ConstructionWorkerRequestDTO extends EmployeeRequestDTO {

    @FutureOrPresent(message = "La fecha de inicio debe ser hoy o en el futuro")
    private LocalDate startDate;

    @Future(message = "La fecha de fin debe estar en el pasado")
    private LocalDate endDate;

}
