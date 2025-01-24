package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import com.grupoingenios.sgpc.sgpc_api_final.entity.schedule.StatusSchedule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud para crear o actualizar un cronograma.
 * Incluye validaciones para garantizar que los datos sean consistentes y completos.
 */
@Setter
@Getter
public class ScheduleRequestDTO {

    /**
     * Nombre del cronograma.
     * Obligatorio y no debe exceder los 100 caracteres.
     */
    @NotBlank(message = "El nombre es obligagorio")
    @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres")
    private String name;

    /**
     * Descripción del cronograma.
     * Obligatoria y no debe exceder los 255 caracteres.
     */
    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 255, message = "La descripción no debe de exceder los 255 caracteres")
    private String description;

    /**
     * Estado actual del cronograma (por ejemplo, ACTIVO, INACTIVO).
     * Obligatorio.
     */
    @NotNull(message = "El status es obligatorio")
    private StatusSchedule status;

}
