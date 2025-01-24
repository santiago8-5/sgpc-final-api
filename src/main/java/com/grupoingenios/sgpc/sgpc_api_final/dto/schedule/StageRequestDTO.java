package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud para crear o actualizar una etapa dentro de un cronograma.
 * Incluye validaciones para garantizar que los datos proporcionados sean consistentes y cumplan con los límites establecidos.
 */
@Setter
@Getter
public class StageRequestDTO {

    /**
     * Nombre de la etapa.
     * Obligatorio y no debe exceder los 100 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres")
    private String name;

    /**
     * Descripción de la etapa.
     * Opcional y no debe exceder los 255 caracteres.
     */
    @Size(max = 255, message = "La descripción no debe de exceder los 255 caracteres")
    private String description;


}
