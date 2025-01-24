package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud para crear o actualizar un departamento.
 * Contiene validaciones para garantizar que los datos ingresados sean correctos.
 */
@Getter
@Setter
public class DepartmentRequestDTO {
    /**
     * Nombre del departamento.
     * Este campo es obligatorio y no debe exceder los 80 caracteres.
     */
    @NotBlank(message = "El nombre del departamento es obligatorio")
    @Size(max=80, message="El nombre del departamento no debe de exceder los 80 caracteres")
    private String name;

    /**
     * Descripción del departamento.
     * Este campo es opcional, pero no debe exceder los 80 caracteres.
     */
    @Size(max=80, message="La descripción del departamento no debe de exceder los 80 caracteres")
    private String description;

    /**
     * Correo electrónico del departamento.
     * Este campo debe ser un correo electrónico válido y no debe exceder los 100 caracteres.
     */
    @Size(max=100, message="El email no debe de exceder los 100 caracteres")
    @Email(message = "Debe ser un correo electrónico válido.")
    private String email;
}
