package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información de una categoría.
 * Contiene detalles como el identificador, el nombre, la descripción y las marcas de tiempo.
 */
@Setter
@Getter
public class CategoryResponseDTO {

    private Long idCategory;

    private String name;

    private String description;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;

}
