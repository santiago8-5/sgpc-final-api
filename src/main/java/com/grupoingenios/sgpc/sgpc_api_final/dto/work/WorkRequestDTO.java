package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
public class WorkRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres")
    private String name;

    @NotBlank(message = "la descripción es obligatorio")
    @Size(max = 255, message = "La descripción no debe de exceder los 255 caracteres")
    private String description;

    @NotNull(message = "La fecha de inicio estimada es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio estimada no puede estar en el pasado")
    private LocalDate estimatedStartDate;

    @NotNull(message = "La fecha de finalización estimada es obligatoria")
    @FutureOrPresent(message = "La fecha de finalización estimada no puede estar en el pasado")
    private LocalDate estimatedEndDate;

    @Future(message = "La fecha de inicio real debe ser futura")
    private LocalDate actualStartDate;

    @Future(message = "La fecha fin real debe ser futura")
    private LocalDate actualEndDate;

    @NotNull(message = "El presupuesto asignado no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El presupuesto asignado debe ser mayor a 0")
    private BigDecimal allocatedBudget;

    @DecimalMin(value = "0.0", inclusive = false, message = "El costo real debe ser mayor a 0")
    private BigDecimal actualCost;

    @NotBlank(message = "La dirección no debe de ser nula")
    @Size(max = 150, message = "La dirección no de de exceder los 150 caracteres")
    private String address;

    @NotNull(message = "El tipo de obra es obligatorio")
    private Long workTypeId;

    private Set<Long> suppliersId;

    private BigDecimal longitude;

    private BigDecimal latitude;


}
