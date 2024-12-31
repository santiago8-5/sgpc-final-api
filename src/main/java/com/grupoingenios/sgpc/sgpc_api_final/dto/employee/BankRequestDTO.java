package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankRequestDTO {
    @NotBlank(message = "El nombre del banco es obligatorio")
    @Size(max = 80, message = "El nombre del banco no debe de exceder los 80 caracteres")
    private String name;
}
