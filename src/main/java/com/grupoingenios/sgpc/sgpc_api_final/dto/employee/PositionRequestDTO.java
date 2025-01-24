package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.StatusType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud para crear o actualizar un puesto de trabajo.
 * Contiene validaciones y atributos relevantes para la gestión de puestos.
 */
@Setter
@Getter
public class PositionRequestDTO {

    /**
     * Nombre del puesto.
     * Este campo es opcional y tiene un límite de 80 caracteres.
     */
    @Column(name="name", nullable=true, length=80)
    private String name;

    /**
     * Descripción del puesto.
     * Este campo es opcional y tiene un límite de 255 caracteres.
     */
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    /**
     * Estado del puesto (activo o inactivo).
     * Este campo es obligatorio.
     */
    @NotNull(message = "El puesto es obligatorio")
    private StatusType statusType;

}
