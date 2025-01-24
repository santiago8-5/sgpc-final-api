package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información de un departamento.
 * Incluye detalles como el identificador, nombre, descripción, email y marcas de tiempo.
 */
@Getter
@Setter
public class DepartmentResponseDTO {

    private Long idDepartment;

    private String name;

    private String description;

    private String email;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
