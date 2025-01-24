package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud para crear o actualizar una categoría.
 * Contiene las validaciones necesarias para asegurar que los datos sean correctos.
 */
@Getter
@Setter
public class CategoryRequestDTO {

    /**
     * Nombre de la categoría.
     * Este campo es obligatorio y no debe exceder los 80 caracteres.
     */
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 80, message = "El nombre de la categoría no debe de exceder los 80 caracteres")
    private String name;


    /**
     * Descripción de la categoría.
     * Este campo es obligatorio y no debe exceder los 255 caracteres.
     */
    @NotBlank(message = "La descripción es obligatorio")
    @Size(max = 255, message = "La descripción no debe de exceder los 255 caracteres")
    private String description;

}
