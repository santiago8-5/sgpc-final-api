package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Role;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO que representa la solicitud para crear o actualizar una relación cliente-obra.
 * Incluye validaciones para asegurar que los datos proporcionados sean completos y válidos.
 */
@Setter
@Getter
public class ClientWorkRequestDTO {

    /**
     * Identificador del cliente asociado a la obra.
     * Obligatorio.
     */
    @NotNull(message = "El cliente es obligatorio")
    private Long clientId;

    /**
     * Identificador de la obra a la que se asigna el cliente.
     * Obligatorio.
     */
    @NotNull(message = "La obra es obligatoria")
    private Long workId;

    /**
     * Rol del cliente en la obra (por ejemplo, contratista, proveedor).
     * Obligatorio.
     */
    @NotNull(message = "El rol del cliente es obligatorio")
    private Role role;

    /**
     * Fecha y hora en que se asigna la relación cliente-obra.
     * Debe ser una fecha presente o futura.
     */
    @FutureOrPresent
    private LocalDateTime assignedAt;

    /**
     * Estado de la relación cliente-obra (por ejemplo, activo, inactivo).
     * Obligatorio.
     */
    @NotNull(message = "El status del cliente es obligatorio")
    private Status status;


}
