package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud para crear o actualizar una cuenta bancaria asociada a un empleado.
 */
@Setter
@Getter
public class AccountRequestDTO {

    /**
     * ID del banco asociado a la cuenta bancaria.
     * Este campo es obligatorio.
     */
    @NotNull(message = "El banco es obligatorio.")
    private Long bankId;

    /**
     * Número de cuenta bancaria.
     * Este campo es obligatorio y tiene un límite máximo de 25 caracteres.
     */
    @NotBlank(message = "El número de cuenta es obligatorio.")
    @Size(max = 25, message = "El número de cuenta no debe tener más de 25 caracteres.")
    private String accountNumber;

    /**
     * Nombre del banco asociado.
     * Este campo es opcional.
     */
    private String nameBank;

}
