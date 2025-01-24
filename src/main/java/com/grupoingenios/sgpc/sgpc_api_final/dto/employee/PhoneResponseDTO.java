package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la respuesta con información de un teléfono asociado a un empleado.
 */
@Setter
@Getter
public class PhoneResponseDTO {

    /**
     * Número de teléfono del empleado.
     */
    private String phone;

    /**
     * Identificador del empleado al que pertenece el teléfono.
     */
    private Long employeeId;

}
