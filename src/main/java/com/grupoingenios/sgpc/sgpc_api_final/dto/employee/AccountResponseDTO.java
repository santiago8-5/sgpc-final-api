package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta de una cuenta bancaria.
 * Contiene información sobre el banco, el número de cuenta y las marcas de tiempo.
 */
@Setter
@Getter
public class AccountResponseDTO {

    /**
     * ID del banco asociado a la cuenta bancaria.
     */
    private Long bankId;

    /**
     * Número de cuenta bancaria.
     */
    private String accountNumber;

    /**
     * Nombre del banco asociado.
     */
    private String nameBank;

    /**
     * Fecha y hora en que la cuenta fue creada.
     */
    private LocalDateTime created_at;

    /**
     * Fecha y hora de la última modificación de la cuenta.
     */
    private LocalDateTime lastModifiedDate;
}
