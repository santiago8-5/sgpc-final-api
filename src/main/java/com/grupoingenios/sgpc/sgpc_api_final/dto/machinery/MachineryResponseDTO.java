package com.grupoingenios.sgpc.sgpc_api_final.dto.machinery;

import com.grupoingenios.sgpc.sgpc_api_final.entity.machinery.StatusTypeMachinery;
import com.grupoingenios.sgpc.sgpc_api_final.entity.machinery.ToolType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta con información detallada de una maquinaria.
 * Incluye atributos como identificador, nombre, marca, modelo, serie, estado, y marcas de tiempo.
 */
@Setter
@Getter
public class MachineryResponseDTO {
    private Long id_machinery;

    private String name;

    private String brand;

    private int model;

    private String serial;

    private LocalDate acquisitionDate;

    private StatusTypeMachinery status;

    private ToolType toolType;

    private LocalDateTime created_at;

    private LocalDateTime lastModifiedDate;
}