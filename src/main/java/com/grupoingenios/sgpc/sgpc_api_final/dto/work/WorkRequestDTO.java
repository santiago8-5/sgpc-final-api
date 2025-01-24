package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO que representa la solicitud para crear o actualizar una obra.
 * Incluye validaciones para asegurar que los datos proporcionados sean completos y válidos.
 */
@Setter
@Getter
public class WorkRequestDTO {

    /**
     * Nombre de la obra.
     * Obligatorio y no debe exceder los 100 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres")
    private String name;

    /**
     * Descripción de la obra.
     * Obligatoria y no debe exceder los 255 caracteres.
     */
    @NotBlank(message = "la descripción es obligatorio")
    @Size(max = 255, message = "La descripción no debe de exceder los 255 caracteres")
    private String description;

    /**
     * Fecha de inicio estimada de la obra.
     * Obligatoria y debe ser una fecha presente o futura.
     */
    @NotNull(message = "La fecha de inicio estimada es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio estimada no puede estar en el pasado")
    private LocalDate estimatedStartDate;

    /**
     * Fecha de finalización estimada de la obra.
     * Obligatoria y debe ser una fecha presente o futura.
     */
    @NotNull(message = "La fecha de finalización estimada es obligatoria")
    @FutureOrPresent(message = "La fecha de finalización estimada no puede estar en el pasado")
    private LocalDate estimatedEndDate;

    /**
     * Fecha de inicio real de la obra.
     * Opcional y debe ser futura.
     */
    @Future(message = "La fecha de inicio real debe ser futura")
    private LocalDate actualStartDate;

    /**
     * Fecha de finalización real de la obra.
     * Opcional y debe ser futura.
     */
    @Future(message = "La fecha fin real debe ser futura")
    private LocalDate actualEndDate;

    /**
     * Presupuesto asignado para la obra.
     * Obligatorio y debe ser mayor a 0.
     */
    @NotNull(message = "El presupuesto asignado no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El presupuesto asignado debe ser mayor a 0")
    private BigDecimal allocatedBudget;

    /**
     * Costo real de la obra.
     * Opcional y debe ser mayor a 0.
     */
    @DecimalMin(value = "0.0", inclusive = false, message = "El costo real debe ser mayor a 0")
    private BigDecimal actualCost;

    /**
     * Dirección de la obra.
     * Obligatoria y no debe exceder los 150 caracteres.
     */
    @NotBlank(message = "La dirección no debe de ser nula")
    @Size(max = 150, message = "La dirección no de de exceder los 150 caracteres")
    private String address;

    /**
     * Identificador del tipo de obra.
     * Obligatorio.
     */
    @NotNull(message = "El tipo de obra es obligatorio")
    private Long workTypeId;

    /**
     * Conjunto de identificadores de proveedores asociados a la obra.
     * Opcional.
     */
    private Set<Long> suppliersId;

    /**
     * Longitud geográfica de la ubicación de la obra.
     * Opcional.
     */
    private BigDecimal longitude;

    /**
     * Latitud geográfica de la ubicación de la obra.
     * Opcional.
     */
    private BigDecimal latitude;


}
