package com.grupoingenios.sgpc.sgpc_api_final.dto.machinery;

import com.grupoingenios.sgpc.sgpc_api_final.entity.machinery.StatusTypeMachinery;
import com.grupoingenios.sgpc.sgpc_api_final.entity.machinery.ToolType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * DTO que representa la solicitud para crear o actualizar un registro de maquinaria.
 * Incluye validaciones para garantizar la integridad de los datos.
 */
@Setter
@Getter
public class MachineryRequestDTO {

    /**
     * Nombre de la maquinaria.
     * Obligatorio y no debe exceder los 100 caracteres.
     */
    @NotBlank(message = "EL nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres")
    private String name;

    /**
     * Marca de la maquinaria.
     * Obligatoria y no debe exceder los 50 caracteres.
     */
    @NotBlank(message = "La marca es obligatoria")
    @Size(max = 50, message = "La marca no debe de exceder los 50 caracteres")
    private String brand;

    /**
     * Año del modelo de la maquinaria.
     * Obligatorio.
     */
    @NotNull(message = "El modelo es obligatorio")
    private int model;

    /**
     * Número de serie de la maquinaria.
     * Obligatorio y no debe exceder los 17 caracteres.
     */
    @NotBlank(message = "El serial es obligatorio")
    @Size(max = 17, message = "El serial no debe de exceder los 17 caracteres")
    private String serial;

    /**
     * Fecha de adquisición de la maquinaria.
     * Obligatoria.
     */
    @NotNull(message = "La fecha de adquisición es obligatoria")
    private LocalDate acquisitionDate;

    /**
     * Estado de la maquinaria (por ejemplo, OPERATIVA, INACTIVA).
     * Obligatorio.
     */
    @NotNull(message = "El estado es obligatorio")
    private StatusTypeMachinery status;

    /**
     * Tipo de herramienta o maquinaria (por ejemplo, herramienta manual, equipo pesado).
     * Obligatoria.
     */
    @NotNull(message = "El tipo de herramienta es obligatoria")
    private ToolType toolType;
}