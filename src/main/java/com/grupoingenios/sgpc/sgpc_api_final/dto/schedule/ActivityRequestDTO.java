package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud para crear o actualizar una actividad dentro de un cronograma.
 * Incluye validaciones para garantizar la integridad de los datos proporcionados.
 */
@Setter
@Getter
public class ActivityRequestDTO {

    /**
     * Nombre de la actividad.
     * Obligatorio y no debe exceder los 100 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres")
    private String name;

    /**
     * Descripción de la actividad.
     * Campo opcional con un límite máximo de 255 caracteres.
     */
    @Size(max = 255, message = "La descripción no debe de exceder los 255 caracteres")
    private String description;

    /**
     * Identificador de la etapa a la que pertenece la actividad.
     * Obligatorio.
     */
    @NotNull(message = "Debes elegir una etapa obligatoriamente")
    private Long idStage;

}
