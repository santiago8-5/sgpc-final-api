package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información de un banco.
 * Incluye detalles como el identificador, el nombre y las marcas de tiempo.
 */
@Setter
@Getter
public class BankResponseDTO {

    private Long idBank;

    private String name;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
