package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO que representa la respuesta con información detallada sobre una obra.
 * Incluye atributos clave como nombre, fechas estimadas y reales, presupuesto, ubicación, tipo de obra y más.
 */
@Setter
@Getter
public class WorkResponseDTO {

    private Long idWork;

    private String name;

    private String description;

    private String workCode;

    private LocalDate estimatedStartDate;

    private LocalDate estimatedEndDate;

    private LocalDate actualStartDate;

    private LocalDate actualEndDate;

    private BigDecimal allocatedBudget;

    private BigDecimal actualCost;

    private String address;

    private String workTypeName;

    private Long workTypeId;

    private List<String> supplierNames;

    private BigDecimal  latitude;

    private BigDecimal  longitude;

    private LocalDateTime created_at;

    private  LocalDateTime lastModifiedDate;

    private boolean hasSchedule;


}