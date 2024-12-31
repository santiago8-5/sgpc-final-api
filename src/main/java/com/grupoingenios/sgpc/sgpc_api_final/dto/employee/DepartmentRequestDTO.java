package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequestDTO {
    @NotBlank(message = "El nombre del departamento es obligatorio")
    @Size(max=80, message="El nombre del departamento no debe de exceder los 80 caracteres")
    private String name;

    @Size(max=80, message="La descripción del departamento no debe de exceder los 80 caracteres")
    private String description;

    @Size(max=100, message="El email no debe de exceder los 100 caracteres")
    @Email(message = "Debe ser un correo electrónico válido.")
    private String email;
}
