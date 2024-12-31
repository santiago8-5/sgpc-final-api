package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 80, message = "El nombre de la categoría no debe de exceder los 80 caracteres")
    private String name;

    @NotBlank(message = "La descripción es obligatorio")
    @Size(max = 255, message = "La descripción no debe de exceder los 255 caracteres")
    private String description;

}
