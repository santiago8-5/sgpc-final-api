package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información detallada de una actividad.
 * Incluye atributos como identificador, nombre, descripción, etapa asociada y marcas de tiempo.
 */
@Setter
@Getter
public class ActivityResponseDTO {

    private Long idActivity;

    private String name;

    private String description;

    private String stageName;

    private Long idStage;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
