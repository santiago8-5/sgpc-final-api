package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


/**
 * DTO que representa la solicitud para agregar o actualizar un teléfono asociado a un empleado.
 * Incluye validaciones para garantizar la calidad de los datos proporcionados.
 */
@Setter
@Getter
public class PhoneRequestDTO {

    /**
     * Número de teléfono del empleado.
     * Obligatorio y debe cumplir con el formato de 10 dígitos numéricos.
     */
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe tener 10 dígitos numéricos")
    private String phone;

    /**
     * Identificador del empleado al que pertenece el teléfono.
     * Obligatorio.
     */
    @NotNull(message = "El empleado debe de tener un telefono")
    private Long employeeId;

}
