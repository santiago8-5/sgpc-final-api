package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.StatusScheduledActivity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * DTO que representa la solicitud para programar o actualizar una actividad dentro de un cronograma.
 * Incluye detalles como fechas estimadas y reales, prioridad, estado y actividad asociada.
 */
@Setter
@Getter
public class ScheduledActivityRequestDTO {

    /**
     * Fecha estimada de inicio de la actividad.
     * Obligatoria.
     */
    @NotNull(message = "La fecha de inicio estimada es obligatoria")
    private LocalDate estimatedStartDate;

    /**
     * Fecha estimada de finalización de la actividad.
     * Obligatoria.
     */
    @NotNull(message = "La fecha fin estimada es obligatoria")
    private LocalDate estimatedEndDate;

    /**
     * Fecha real de inicio de la actividad.
     * Este campo es opcional.
     */
    private LocalDate actualStartDate;

    /**
     * Fecha real de finalización de la actividad.
     * Este campo es opcional.
     */
    private LocalDate actualEndDate;

    /**
     * Prioridad asignada a la actividad programada.
     * Obligatoria.
     */
    @NotNull(message = "La prioridad es obligatoria")
    private int priority;

    /**
     * Estado actual de la actividad programada (por ejemplo, PENDIENTE, EN PROCESO, COMPLETADA).
     * Obligatoria.
     */
    @NotNull(message = "El status es obligatorio es obligatoria")
    private StatusScheduledActivity status;

    /**
     * Identificador único de la actividad asociada.
     * Obligatoria.
     */
    @NotNull(message = "La actividad es obligatoria")
    private Long idActivity;

}
