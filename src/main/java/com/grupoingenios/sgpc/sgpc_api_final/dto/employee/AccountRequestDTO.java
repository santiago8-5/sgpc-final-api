package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AccountRequestDTO {

    @NotNull(message = "El banco es obligatorio.")
    private Long bankId;

    @NotBlank(message = "El número de cuenta es obligatorio.")
    @Size(max = 25, message = "El número de cuenta no debe tener más de 25 caracteres.")
    private String accountNumber;

    private String nameBank;


}
