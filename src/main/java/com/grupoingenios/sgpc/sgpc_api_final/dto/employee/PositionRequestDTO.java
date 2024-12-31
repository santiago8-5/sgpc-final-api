package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.StatusType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PositionRequestDTO {

    @Column(name="name", nullable=true, length=80)
    private String name;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @NotNull(message = "El puesto es obligatorio")
    private StatusType statusType;

}
