package com.grupoingenios.sgpc.sgpc_api_final.dto.employee;

// REQUEST LO QUE DEVULVE EL CLIENTE AL SERVIDOR, CREAR O ACTUALIZAR UN EMPLEADO

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.grupoingenios.sgpc.sgpc_api_final.entity.employee.EmployeeType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

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

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 100, message = "El nombre no debe tener más de 100 caracteres.")
    private String name;

    @NotBlank(message = "El RFC es obligatorio.")
    @Size(max = 13, message = "El RFC no debe tener más de 13 caracteres.")
    @Pattern(regexp = "[A-Z]{4}\\d{6}[A-Z0-9]{3}", message = "El RFC debe tener 4 letras, 6 números y 3 caracteres alfanuméricos")
    private String rfc;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debe ser un correo electrónico válido.")
    @Size(max = 100, message = "El correo electrónico no debe tener más de 100 caracteres.")
    private String email;

    @NotNull(message = "La fecha de contratación es obligatoria.")
    @PastOrPresent(message = "La fecha de contratación no puede ser futura.")
    private LocalDate hiringDate;

    @NotNull(message = "El tipo de empleado es obligatorio.")
    private EmployeeType employeeType;

    @NotNull(message = "La posición es obligatoria.")
    private Long positionId;

    @NotNull(message = "La categoría es obligatoria.")
    private Long categoryId;

    private Set<AccountRequestDTO> accounts;

    private Set<PhoneRequestDTO> phones;
}