package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Role;
import com.grupoingenios.sgpc.sgpc_api_final.entity.work.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ClientWorkRequestDTO {

    @NotNull(message = "El cliente es obligatorio")
    private Long clientId;

    @NotNull(message = "La obra es obligatoria")
    private Long workId;

    @NotNull(message = "El rol del cliente es obligatorio")
    private Role role;

    @FutureOrPresent
    private LocalDateTime assignedAt;

    @NotNull(message = "El status del cliente es obligatorio")
    private Status status;


}
