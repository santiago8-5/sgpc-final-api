package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WorkTypeRequestDTO {

    @NotNull(message = "El nombre es ogligatorio")
    @Size(max = 80, message = "El nombre no puede exceder los 80 caracteres")
    private String name;

    @Size(max = 80, message = "La description no puede exceder los 255 caracteres")
    private String description;

}
