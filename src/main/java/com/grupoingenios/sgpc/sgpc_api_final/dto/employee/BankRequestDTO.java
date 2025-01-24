package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud para crear o actualizar un banco.
 * Contiene las validaciones necesarias para asegurar que los datos sean correctos.
 */
@Getter
@Setter
public class BankRequestDTO {
    /**
     * Nombre del banco.
     * Este campo es obligatorio y no debe exceder los 80 caracteres.
     */
    @NotBlank(message = "El nombre del banco es obligatorio")
    @Size(max = 80, message = "El nombre del banco no debe de exceder los 80 caracteres")
    private String name;
}
