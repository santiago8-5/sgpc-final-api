package com.grupoingenios.sgpc.sgpc_api_final.dto.work;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la solicitud para crear o actualizar un cliente.
 * Incluye validaciones para garantizar que los datos proporcionados sean consistentes y completos.
 */
@Setter
@Getter
public class ClientRequestDTO {

    /**
     * Nombre del cliente.
     * Obligatorio y no debe exceder los 100 caracteres.
     */
    @NotBlank(message="El nombre es obligatorio")
    @Size(max=100, message="El nombre no puede exceder los 100 caracteres")
    private String name;

    /**
     * Dirección del cliente.
     * Obligatoria y no debe exceder los 100 caracteres.
     */
    @NotBlank(message="La dirección es obligatoria")
    @Size(max=100, message="La dirección no puede exceder los 100 caracteres")
    private String address;

    /**
     * Municipio donde reside el cliente.
     * Obligatorio y no debe exceder los 70 caracteres.
     */
    @NotBlank(message="El Municipio es obligatorio")
    @Size(max=70, message="El municipio no puede exceder los 70 caracteres")
    private String municipality;

    /**
     * Estado donde reside el cliente.
     * Obligatorio y no debe exceder los 50 caracteres.
     */
    @NotBlank(message="El Estado es obligatorio")
    @Size(max=50, message="El Estado no puede exceder los 50 caracteres")
    private String state;

    /**
     * Teléfono del cliente.
     * Obligatorio y debe tener exactamente 10 dígitos numéricos.
     */
    @NotBlank(message="El teléfono es obligatorio")
    @Size(max=10, min=10, message="El teléfono no puede exceder los 10 digitos")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono debe contener solo números")
    private String phone;


    /**
     * Correo electrónico del cliente.
     * Obligatorio, debe ser válido y no exceder los 100 caracteres.
     */
    @NotBlank(message="El email es obligatorio")
    @Size(max=100, message="El email no puede exceder los 100 caracteres")
    @Email(message = "Debe proporcionar un correo válido")
    private String email;


    /**
     * RFC del cliente.
     * Obligatorio y debe seguir el formato específico de 4 letras, 6 números y 3 caracteres alfanuméricos.
     */
    @NotBlank(message = "El RFC es obligatorio")
    @Size(max=13,min=13, message="El RFC no puede exceder los 13 caracteres")
    @Pattern(regexp = "[A-Z]{4}\\d{6}[A-Z0-9]{3}", message = "El RFC debe tener 4 letras, 6 números y 3 caracteres alfanuméricos")
    private String rfc;


}
