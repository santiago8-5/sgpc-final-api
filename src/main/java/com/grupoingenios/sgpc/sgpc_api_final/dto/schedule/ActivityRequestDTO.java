package com.grupoingenios.sgpc.sgpc_api_final.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActivityRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no debe de exceder los 255 caracteres")
    private String description;

    @NotNull(message = "Debes elegir una etapa obligatoriamente")
    private Long idStage;

}
