package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientRequestDTO {


    @NotBlank(message="El nombre es obligatorio")
    @Size(max=100, message="El nombre no puede exceder los 100 caracteres")
    private String name;

    @NotBlank(message="La dirección es obligatoria")
    @Size(max=100, message="La dirección no puede exceder los 100 caracteres")
    private String address;

    @NotBlank(message="El Municipio es obligatorio")
    @Size(max=70, message="El municipio no puede exceder los 70 caracteres")
    private String municipality;

    @NotBlank(message="El Estado es obligatorio")
    @Size(max=50, message="El Estado no puede exceder los 50 caracteres")
    private String state;

    @NotBlank(message="El teléfono es obligatorio")
    @Size(max=10, min=10, message="El teléfono no puede exceder los 10 digitos")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono debe contener solo números")
    private String phone;

    @NotBlank(message="El email es obligatorio")
    @Size(max=100, message="El email no puede exceder los 100 caracteres")
    @Email(message = "Debe proporcionar un correo válido")
    private String email;

    @NotBlank(message = "El RFC es obligatorio")
    @Size(max=13,min=13, message="El RFC no puede exceder los 13 caracteres")
    @Pattern(regexp = "[A-Z]{4}\\d{6}[A-Z0-9]{3}", message = "El RFC debe tener 4 letras, 6 números y 3 caracteres alfanuméricos")
    private String rfc;


}
