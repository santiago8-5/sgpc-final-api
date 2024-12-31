package com.grupoingenios.sgpc.sgpc_api_final.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "El username es obligatorio")
    @Size(max = 50, message = "El username no puede exceder los 50 caracteres")
    private String username;

    @NotBlank(message = "El password es obligatorio")
    @Size(max = 255, message = "El password no puede excede los 255 caracteres")
    private String password;

    private Long rolId;

}
