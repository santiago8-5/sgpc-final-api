package com.grupoingenios.sgpc.sgpc_api_final.dto.inventory;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO que representa la solicitud para crear o actualizar un proveedor.
 * Incluye validaciones para garantizar la integridad de los datos proporcionados.
 */
@Data
public class SupplierRequestDTO {

    /**
     * Nombre del proveedor.
     * Obligatorio y no debe exceder los 100 caracteres.
     */
    @NotNull(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre del proveedor no debe de exceder los 100 caracteres")
    private String name;

    /**
     * Dirección del proveedor.
     * Obligatoria y no debe exceder los 100 caracteres.
     */
    @NotNull(message = "La direccion es obligatoria")
    @Size(max=100, message="La direccón no puede exceder los 100 caracteres")
    private String address;

    /**
     * Teléfono del proveedor.
     * Obligatorio, debe contener 10 dígitos numéricos.
     */
    @Pattern(regexp = "^[0-9]+$", message = "El telefono debe contener solo números")
    @NotBlank(message="El numero de celular es obligatorio")
    @Size(max=10, min = 10, message="El numero debe de tener 10 numeros")
    private String phone;

    /**
     * Correo electrónico del proveedor.
     * Obligatorio, debe ser válido y no exceder los 100 caracteres.
     */
    @Email(message = "Debe de proporcionar un correo válido")
    @NotBlank(message="El email es obligatorio")
    @Size(max=100, message="El email no puede exceder los 100 caracteres")
    private String email;

    /**
     * RFC del proveedor.
     * Obligatorio, debe contener exactamente 13 caracteres.
     */
    @Size(max=13, min = 13, message="El RFC debe de tener 13 caracteres")
    @NotBlank(message="El RFC es obligatorio")
    private String rfc;

}
