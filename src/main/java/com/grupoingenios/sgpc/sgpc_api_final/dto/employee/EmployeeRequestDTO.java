package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.EmployeeType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO que representa la solicitud para crear o actualizar un empleado.
 * Es la clase base que se extiende para diferentes tipos de empleados.
 */
@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "employeeType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlantEmployeeRequestDTO.class, name = "PLANTA"),
        @JsonSubTypes.Type(value = ConstructionWorkerRequestDTO.class, name = "OBRA")
})
public class EmployeeRequestDTO {

    /**
     * Nombre completo del empleado.
     * Obligatorio y con un máximo de 100 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 100, message = "El nombre no debe tener más de 100 caracteres.")
    private String name;


    /**
     * RFC del empleado.
     * Obligatorio y con un formato específico que incluye 4 letras, 6 números y 3 caracteres alfanuméricos.
     */
    @NotBlank(message = "El RFC es obligatorio.")
    @Size(max = 13, message = "El RFC no debe tener más de 13 caracteres.")
    @Pattern(regexp = "[A-Z]{4}\\d{6}[A-Z0-9]{3}", message = "El RFC debe tener 4 letras, 6 números y 3 caracteres alfanuméricos")
    private String rfc;

    /**
     * Correo electrónico del empleado.
     * Obligatorio, válido y con un máximo de 100 caracteres.
     */
    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debe ser un correo electrónico válido.")
    @Size(max = 100, message = "El correo electrónico no debe tener más de 100 caracteres.")
    private String email;


    /**
     * Fecha de contratación del empleado.
     * Obligatoria y no puede ser una fecha futura.
     */
    @NotNull(message = "La fecha de contratación es obligatoria.")
    @PastOrPresent(message = "La fecha de contratación no puede ser futura.")
    private LocalDate hiringDate;


    /**
     * Tipo de empleado (PLANTA u OBRA).
     * Obligatorio.
     */
    @NotNull(message = "El tipo de empleado es obligatorio.")
    private EmployeeType employeeType;


    /**
     * Identificador del puesto que ocupa el empleado.
     * Obligatorio.
     */
    @NotNull(message = "La posición es obligatoria.")
    private Long positionId;


    /**
     * Identificador de la categoría del empleado.
     * Obligatorio.
     */
    @NotNull(message = "La categoría es obligatoria.")
    private Long categoryId;


    /**
     * Cuentas bancarias asociadas al empleado.
     * Este campo es opcional.
     */
    private Set<AccountRequestDTO> accounts;


    /**
     * Números de teléfono asociados al empleado.
     * Este campo es opcional.
     */
    private Set<PhoneRequestDTO> phones;
}