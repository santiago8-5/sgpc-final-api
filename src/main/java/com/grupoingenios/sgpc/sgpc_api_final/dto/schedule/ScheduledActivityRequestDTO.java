package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.StatusScheduledActivity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
public class ScheduledActivityRequestDTO {
    @NotNull(message = "La fecha de inicio estimada es obligatoria")
    private LocalDate estimatedStartDate;

    @NotNull(message = "La fecha fin estimada es obligatoria")
    private LocalDate estimatedEndDate;

    private LocalDate actualStartDate;

    private LocalDate actualEndDate;

    @NotNull(message = "La prioridad es obligatoria")
    private int priority;

    @NotNull(message = "El status es obligatorio es obligatoria")
    private StatusScheduledActivity status;


    @NotNull(message = "La actividad es obligatoria")
    private Long idActivity;


}
