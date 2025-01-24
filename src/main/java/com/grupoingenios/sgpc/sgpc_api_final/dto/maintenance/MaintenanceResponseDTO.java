package com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance;

import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.RelatedEntityType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información detallada de un registro de mantenimiento.
 * Incluye atributos como el identificador, la entidad relacionada, tipo de mantenimiento, empleado responsable, costos, y fechas relevantes.
 */
@Setter
@Getter
public class MaintenanceResponseDTO {

    private Long idMaintenance;

    private Long relatedEntityId;

    private RelatedEntityType relatedEntityType;

    private String nameEmployee;

    private Long employeeId;

    private String maintenanceType;

    private String description;

    private Float cost;

    private LocalDate realizationDate;

    private LocalDate nextDate;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
