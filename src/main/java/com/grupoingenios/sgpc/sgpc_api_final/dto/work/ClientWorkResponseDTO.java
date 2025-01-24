package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Role;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * DTO que representa la respuesta detallada sobre la relación cliente-obra.
 * Incluye información sobre el cliente, la obra, el rol asignado, las fechas de asignación y modificaciones.
 */
@Setter
@Getter
@AllArgsConstructor
public class ClientWorkResponseDTO {

    private Long id;

    private String nameClient;

    private Long clientId;

    private String nameWork;

    private Role role;

    private LocalDateTime assignedAt;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

}
