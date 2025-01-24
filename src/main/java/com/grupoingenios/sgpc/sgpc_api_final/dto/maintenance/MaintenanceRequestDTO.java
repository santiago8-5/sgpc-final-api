package com.grupoingenios.sgpc.sgpc_api_final.dto.maintenance;

import com.grupoingenios.sgpc.sgpc_api_final.entity.maintenance.RelatedEntityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * DTO que representa la solicitud para crear o actualizar un registro de mantenimiento.
 * Incluye validaciones para garantizar la integridad de los datos proporcionados.
 */
@Setter
@Getter
public class MaintenanceRequestDTO {

    /**
     * Identificador de la entidad relacionada (vehículo o maquinaria).
     * Obligatorio.
     */
    @NotNull(message = "Necesitas seleccionar un vehiculo o una maquinaria obligatoriamente")
    private Long relatedEntityId;

    /**
     * Tipo de entidad relacionada (vehículo o maquinaria).
     * Obligatorio.
     */
    @NotNull(message = "Selecciona de tipo maquina o vehiculo")
    private RelatedEntityType relatedEntityType;

    /**
     * Identificador del empleado responsable del mantenimiento.
     * Obligatorio.
     */
    @NotNull(message = "Selecciona un empleado para el mantenimiento")
    private Long employeeId;

    /**
     * Tipo de mantenimiento realizado.
     * Obligatorio.
     */
    @NotBlank(message = "El tipo de mantenimiento es obligatorio")
    private String maintenanceType;

    /**
     * Descripción adicional del mantenimiento realizado.
     * Este campo es opcional y no debe exceder los 255 caracteres.
     */
    @Size(max = 255, message = "La descripción no debe de exceder los 255 caracteres")
    private String description;

    /**
     * Costo del mantenimiento.
     * Obligatorio y debe ser un valor positivo.
     */
    @Positive(message = "El costo debe ser mayor a 0")
    private Float cost;

    /**
     * Fecha de realización del mantenimiento.
     */
    private LocalDate realizationDate;

    /**
     * Fecha estimada para el próximo mantenimiento.
     */
    private LocalDate nextDate;

}
