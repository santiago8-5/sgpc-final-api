package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.StatusScheduledActivity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * DTO que representa la respuesta con información detallada sobre una actividad programada.
 * Incluye detalles como fechas estimadas y reales, prioridad, estado, y la actividad asociada.
 */
@Setter
@Getter
@AllArgsConstructor
public class ScheduledActivityResponseDTO {

    private Long scheduledActivityId;

    private LocalDate estimatedStartDate;

    private LocalDate estimatedEndDate;

    private LocalDate actualStartDate;

    private LocalDate actualEndDate;

    private int priority;

    private StatusScheduledActivity status;

    private String nameActivity;

    private Long activityId;

}
