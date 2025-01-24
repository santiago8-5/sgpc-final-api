package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información detallada de una etapa dentro de un cronograma.
 * Incluye atributos como identificador, nombre, descripción y marcas de tiempo.
 */
@Setter
@Getter
public class StageResponseDTO {

    private Long idStage;

    private String name;

    private String description;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
