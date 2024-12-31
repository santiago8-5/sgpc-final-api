package com.grupoingenios.sgpc.sgpc_api_final.dto.inventory;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SupplierRequestDTO {

    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre del proveedor no debe de exceder los 100 caracteres")
    private String name;

    @NotNull(message = "La direccion es obligatoria")
    @Size(max=100, message="La direccón no puede exceder los 100 caracteres")
    private String address;

    @Pattern(regexp = "^[0-9]+$", message = "El telefono debe contener solo números")
    @NotBlank(message="El numero de celular es obligatorio")
    @Size(max=10, min = 10, message="El numero debe de tener 10 numeros")
    private String phone;

    @Email(message = "Debe de proporcionar un correo válido")
    @NotBlank(message="El email es obligatorio")
    @Size(max=100, message="El email no puede exceder los 100 caracteres")
    private String email;

    @Size(max=13, min = 13, message="El RFC debe de tener 13 caracteres")
    @NotBlank(message="El RFC es obligatorio")
    private String rfc;

}
